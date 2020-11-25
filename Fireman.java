import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Fireman extends Abstract_Moveable {

    private static final String FSMITH_KEY = "fireblacksmith";

    private final PathingStrategy strategy = new AStarPathingStrategy();

    private int steps;

    public Fireman(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.steps = 0;
    }

    public boolean moveToFireman(WorldModel world, Abstract_Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.getPosition(), target.getPosition()))
        {
            world.removeEntityAt(target.getPosition());
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition( world, target.getPosition());

            if(!this.getPosition().equals(nextPos))
            {
                Optional<Abstract_Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Abstract_Entity> firemanTarget = world.findNearest(getPosition(), Blacksmith.class);
        long nextPeriod = getActionPeriod();

        if(firemanTarget.isPresent()){
            Point target_Pos = firemanTarget.get().getPosition();

            if(moveToFireman(world, firemanTarget.get(), scheduler)){
                Abstract_Entity fireSmith = new Fire_Blacksmith(FSMITH_KEY, target_Pos,
                        imageStore.getImageList(FSMITH_KEY));

                world.addEntity(fireSmith);
                nextPeriod += getActionPeriod();
            }
        }
        scheduler.scheduleEvent( this, new Activity(this,
                world, imageStore, 0), nextPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world,
                imageStore, 0), this.getActionPeriod());
        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());
    }

    public Point nextPosition(WorldModel world, Point desPos){
        List<Point> pts;

        pts = strategy.computePath(getPosition(), desPos, p -> world.withinBounds(p) && !world.isOccupied(p),
                Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if(pts.size() != 0)
            return pts.get(0);
        return getPosition();
    }

    private void incrementingSTEP(WorldModel world){
        if(steps > 25){
            world.removeEntityAt(this.getPosition());
        }
        else
        {
            steps += 1;
        }
    }
}
