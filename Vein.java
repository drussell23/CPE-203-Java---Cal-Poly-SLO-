import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Vein implements Executable {

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;

    public static final String ORE_KEY = "ore";

    private Point position;
    private List<PImage> images;
    private int imageIndex;
    public final int resourceLimit;
    public final int resourceCount;
    private final int actionPeriod;
    private final int animationPeriod;
    private final String id;

    public Vein(String id, Point position,
                List<PImage> images, int resourceLimit, int resourceCount,
                int actionPeriod, int animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public static Vein createVein(String id, Point position, int actionPeriod,
                                  List<PImage> images) {
        return new Vein( id, position, images, 0, 0,
                actionPeriod, 0);
    }

    public Point getPosition() { return this.position;}
    public List<PImage> getImages() { return this.images;}
    public int getImageIndex() { return this.imageIndex;}
    public void setPosition(Point p) { this.position = p;}
    public void setImages(List<PImage> i) { this.images =i; }

    public void execute( WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            Ore ore = Ore.createOre(ORE_ID_PREFIX + this.id,
                    openPt.get(), ORE_CORRUPT_MIN +
                            Functions.rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    Functions.getImageList(imageStore, ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActions( scheduler, world, imageStore);
        }

        scheduler.scheduleEvent( this,
                Activity.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }


    public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    //edited
    // public void nextImage()
    // {
    //     this.imageIndex = (this.getImageIndex()+ 1) % this.getImages().size();
    // }


    public void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.actionPeriod);

    }
}