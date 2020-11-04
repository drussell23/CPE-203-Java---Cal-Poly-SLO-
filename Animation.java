public class Animation implements Action
{
    public AnimationEntity entity;
    public int repeatCount;

    public Animation(AnimationEntity entity, int repeatCount)
    {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public static Animation createAnimationAction(AnimationEntity entity, int repeatCount)
    {
        return new Animation(entity, repeatCount);
    }

    @Override
    public void executeAction(EventScheduler scheduler)
    {
        this.entity.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent(this.entity,
                    createAnimationAction(this.entity,
                            Math.max(this.repeatCount - 1, 0)),
                    ((AnimationEntity)this.entity).getAnimationPeriod());
        }
    }
    public Entity getEntity() { return entity; }
  //  public int getRepeatCount() { return repeatCount; }
}