import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Oreblob extends Abstract_Moveable
{
    public static final String QUAKE_KEY = "quake";


    public Oreblob(String id, Point position, List<PImage>  images, int actionPeriod, int animationPeriod)
    {
       super(id, position, images, actionPeriod, animationPeriod);
    }

    public static Oreblob createOreBlob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        return new Oreblob(id, position, images, actionPeriod, animationPeriod);
    }

    public boolean moveToOreBlob(WorldModel world, Abstract_Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.getPosition(), target.getPosition()))
        {
            world.removeEntityAt( target.getPosition());
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

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Abstract_Entity> blobTarget = world.findNearest( this.getPosition(), Vein.class);
        long nextPeriod = getActionPeriod();

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveToOreBlob(world, blobTarget.get(), scheduler))
            {
                Quake quake = Quake.createQuake(tgtPos,

                        ImageStore.getImageList(imageStore, QUAKE_KEY));

                world.addEntity( quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions( scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent( this, new Activity(this,
                world, imageStore, 0), nextPeriod);
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

    void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0),
                this.getAnimationPeriod());

        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());
    }
}