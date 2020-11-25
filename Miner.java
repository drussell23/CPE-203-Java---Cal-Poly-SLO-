import processing.core.PImage;
import java.util.List;

abstract public class Miner extends Abstract_Moveable {
    protected int resourceLimit;
    protected int resourceCount;

    public Miner(String id, Point position, List<PImage> images,
                 int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }
    abstract public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    private PathingStrategy strategy = new AStarPathingStrategy();
    @Override
    public Point nextPosition(WorldModel world, Point desPos){
        List<Point> pts;

        pts = strategy.computePath(getPosition(), desPos, p -> world.withinBounds(p) && !world.isOccupied(p),
                Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);

        if(pts.size() != 0)
            return pts.get(0);
        return getPosition();
    }
}
