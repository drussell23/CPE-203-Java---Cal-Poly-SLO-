public class Animation implements Action {
    public Abstract_AnimationEntity entity;
    public int repeatCount;

    public Animation(Abstract_AnimationEntity entity, int repeatCount) {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity, new Animation(this.entity, Math.max(this.repeatCount - 1, 0)),
                    this.entity.getAnimationPeriod());
        }
    }
}