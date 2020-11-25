import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Vein extends Abstract_Executable {

    private static final Random random = new Random();

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;

    public static final String ORE_KEY = "ore";

    public Vein(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent()) {
            Abstract_Executable ore = new Ore(ORE_ID_PREFIX + this.id, openPt.get(),
                    ImageStore.getImageList(imageStore, ORE_KEY),
                    ORE_CORRUPT_MIN + random.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.getActionPeriod());
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world,
                imageStore, 0), this.getActionPeriod());
    }
}