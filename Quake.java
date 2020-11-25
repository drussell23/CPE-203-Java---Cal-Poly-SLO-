import processing.core.PImage;

import java.util.List;

public class Quake extends Abstract_AnimationEntity {


    //public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
   // public static final String ORE_KEY = "ore";


    public Quake(String id, Point position, List<PImage> images,
                 int actionPeriod, int animationPeriod)
    {
       super(id, position, images, actionPeriod, animationPeriod);
    }

    public static Quake createQuake(Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public void execute( WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents( this);
        world.removeEntityAt( this.getPosition());
    }

    public void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), getActionPeriod());
        scheduler.scheduleEvent(this, new Animation(this, QUAKE_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
    }
}