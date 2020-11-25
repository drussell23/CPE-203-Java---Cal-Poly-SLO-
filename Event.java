final class Event
{
    private final Action action;
    private final long time;
    private final Abstract_Entity entity;

    public Event(Action action, long time, Abstract_Entity entity)
    {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }

    public Action getAction(){return action;}

    public long getTime(){return time;}

    public Abstract_Entity getEntity(){return entity;}

}
