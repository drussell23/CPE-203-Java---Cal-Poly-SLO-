import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class MinerFull implements Moveable
{
    private final String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    public int resourceCount;
    private final int actionPeriod;
    private final int animationPeriod;

    public MinerFull( String id, Point position,
                      List<PImage> images, int resourceLimit, int resourceCount,
                      int actionPeriod, int animationPeriod)
    {
        this.id=id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public static MinerFull createMinerFull(String id, int resourceLimit,
                                            Point position, int actionPeriod, int animationPeriod,
                                            List<PImage> images)
    {
        return new MinerFull( id, position, images,
                resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    public Point getPosition() { return this.position;}
    public List <PImage> getImages() { return this.images;}
    public int getImageIndex() { return this.imageIndex;}
    public void setPosition(Point p) { this.position = p;}
    public void setImages(List<PImage> i) { this.images =i; }

    public void execute( WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.position, Blacksmith.class);

        if (fullTarget.isPresent() &&
                moveToFull( world, fullTarget.get(), scheduler))
        {
            this.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    public void transformFull( WorldModel world,
                               EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFull miner = MinerNotFull.createMinerNotFull(this.id, this.resourceLimit,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);

        world.removeEntity( this);
        scheduler.unscheduleAllEvents( this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    public Point nextPosition( WorldModel world, Point destPos)
    {
        int horizontal = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horizontal,
                this.position.y);

        if (horizontal == 0 || world.isOccupied( newPos))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x,
                    this.position.y + vert);

            if (vert == 0 || world.isOccupied( newPos))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public  boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.position, target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition( world, target.getPosition());

            if (!this.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant( nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);

                world.moveEntity( this, nextPos);
            }
            return false;
        }
    }

    public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public void nextImage()
    {
        this.imageIndex = (this.getImageIndex()+ 1) % this.getImages().size();
    }

    public void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getAnimationPeriod());

        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }
}