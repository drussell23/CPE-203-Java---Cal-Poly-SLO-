public class Activity implements Action {


    private final Abstract_Executable entity;
    private final WorldModel world;
    private final ImageStore imageStore;
    public int repeatCount;

    public Activity(Abstract_Executable entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        entity.execute(world, imageStore, scheduler);
    }

}