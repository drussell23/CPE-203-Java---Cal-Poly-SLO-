import java.util.List;

import processing.core.PImage;

public abstract class Abstract_Executable extends Abstract_Entity {

    private final int actionPeriod;

    public Abstract_Executable(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    abstract void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);


}
