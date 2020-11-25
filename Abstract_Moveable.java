import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import processing.core.PImage;

public abstract class Abstract_Moveable extends Abstract_AnimationEntity {

    public Abstract_Moveable(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);

    }

    private PathingStrategy strategy = new AStarPathingStrategy();

    protected Point nextPosition(WorldModel world, Point desPos){
        List<Point> pts;

        pts = strategy.computePath(getPosition(), desPos, p -> world.withinBounds(p) && !world.isOccupied(p),
                Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if(pts.size() != 0)
            return pts.get(0);
        return getPosition();
    }


    private static Predicate<Point> canPassThrough(WorldModel world){
        return p -> (world.withinBounds(p) && !world.isOccupied(p));
    }

    public static BiPredicate<Point, Point> withinReach() { return (Point::adjacent); }
}