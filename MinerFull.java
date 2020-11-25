import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class MinerFull extends Miner {

    public MinerFull(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod,
            int resourceLimit, int resourceCount) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);

    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Abstract_Entity> fullTarget = world.findNearest(this.position, Blacksmith.class);

        if (fullTarget.isPresent() && moveToFull(world, fullTarget.get(), scheduler)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.getActionPeriod());
        }
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        MinerNotFull miner = new MinerNotFull(this.id, this.position, this.images, getActionPeriod(), getAnimationPeriod(), this.resourceLimit, this.resourceCount);

        world.removeEntityAt(this.getPosition());
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
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


    public boolean moveToFull(WorldModel world, Abstract_Entity target, EventScheduler scheduler) {
        if (Point.adjacent(this.position, target.getPosition())) {
            return true;
        }
        else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.position.equals(nextPos)) {
                Optional<Abstract_Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0),
                this.getAnimationPeriod());

        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());
    }
}