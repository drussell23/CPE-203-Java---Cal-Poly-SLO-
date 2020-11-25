// Name: Derek J. Russell
// Date: 11/11/2020

// Project 3 - (CPE 203)
import processing.core.PImage;
import java.util.List;
import java.util.Random;

public class Ore extends Abstract_Executable {

    private static final Random random = new Random();

    public static final String BLOB_KEY = "blob";
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;

    public Ore(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = this.getPosition();

        world.removeEntityAt(this.getPosition());
        scheduler.unscheduleAllEvents(this);

        Abstract_Moveable blob = new Oreblob(this.id + BLOB_ID_SUFFIX, pos, ImageStore.getImageList(imageStore, BLOB_KEY),
                getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + random.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);

    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.getActionPeriod());
    }
}