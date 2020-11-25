import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends Miner {
    public MinerNotFull(String id, Point position,
                        List<PImage> images, int actionPeriod,
                        int animationPeriod, int resourceLimit, int resourceCount)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public static MinerNotFull createMinerNotFull(String id, int resourceLimit,
                                                  Point position, int actionPeriod,
                                                  int animationPeriod, List<PImage> images)
    {
        return new MinerNotFull(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.resourceLimit) {

            MinerFull miner = new MinerFull(this.id, this.position,
                    this.images, getActionPeriod(), getAnimationPeriod(),
                    this.resourceLimit, this.resourceCount);

            world.removeEntityAt( this.getPosition());
            scheduler.unscheduleAllEvents(this);
            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }

    public boolean moveToNotFull(WorldModel world, Abstract_Entity target, EventScheduler scheduler) {

        if (Point.adjacent(this.position, target.getPosition())) {
            this.resourceCount += 1;
            world.removeEntityAt( target.getPosition());
            scheduler.unscheduleAllEvents(target);

            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Abstract_Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Abstract_Entity> notFullTarget = world.findNearest(this.position, Ore.class);

        if (!notFullTarget.isPresent() ||
                !moveToNotFull(world, notFullTarget.get(), scheduler) ||
                !transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore, 0),
                    getActionPeriod());
        }
    }

    @Override
    void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0)
                , this.getAnimationPeriod());
        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());
    }

    private PathingStrategy strategy = new AStarPathingStrategy();
    @Override
    public Point nextPosition(WorldModel world, Point desPos){
        List<Point> pts;

        pts = strategy.computePath(getPosition(), desPos, p -> world.withinBounds(p) && !world.isOccupied(p),
                Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if(pts.size() != 0)
            return pts.get(0);
        return getPosition();
    }
}