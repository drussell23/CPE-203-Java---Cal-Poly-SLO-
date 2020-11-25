import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        /*
            - Compares the current entity node through point, to the F Value.// Computing & Sorting the shortest path.
        */
        List<Point> path = new LinkedList<>();
        /*
            - The estimated movement cost to move from that given square on the grid to the final destination.
         */
        Comparator<AStar_Node> sorting_ASTAR = Comparator.comparing(AStar_Node::getF_star);
        PriorityQueue<AStar_Node> open = new PriorityQueue<>(sorting_ASTAR); // Selects the smallest f value in the list and sets as target.

        Map<Point, AStar_Node> closed = new HashMap<>();

        int g_distance_value = 0;
        int H_heurstic_distance_value = getHeuristic(start, end);
        int f_total_distance = g_distance_value + H_heurstic_distance_value;

        AStar_Node curr = new AStar_Node(start, g_distance_value, H_heurstic_distance_value, f_total_distance, null);

        open.add(curr);

        while (open.size() > 0)
        {
            curr = open.poll();

            if(withinReach.test(curr.getPos(), end))
            {
                writePath_ASTAR(path, curr);
                break;
            }
            closed.put(curr.getPos(), curr);

            List<Point> neighbors = potentialNeighbors
                    .apply(curr.getPos())
                    .filter(canPassThrough)
                    .filter(p -> !closed.containsKey(p))
                    .collect(Collectors.toList());

            for(Point neighbor : neighbors)
            {
                g_distance_value = curr.getG_star() + 1;
                H_heurstic_distance_value = getHeuristic(neighbor, end);
                f_total_distance = H_heurstic_distance_value + g_distance_value;

                AStar_Node nearest = new AStar_Node(neighbor, g_distance_value,
                        H_heurstic_distance_value, f_total_distance, curr);

                if(!open.contains(nearest))
                    open.add(nearest);
            }
        }
        return (path);
    }

    private void writePath_ASTAR(List<Point> path, AStar_Node node)
    {
        if(node.getEarlier() == null) return;

        path.add(0, node.getPos());

        if(node.getEarlier().getEarlier() != null) writePath_ASTAR(path, node.getEarlier());
    }

    private int getHeuristic(Point point_1, Point point_2)
    {
        return Math.abs((point_1.getY() - point_2.getY()) + (point_1.getX() -point_2.getX()));
    }

    private static class AStar_Node{

        private final int g_star;
        private final int h_star;
        private final int f_star;
        private final AStar_Node earlier;
        private final Point location;

        public AStar_Node(Point location, int g_star, int h_star, int f_star, AStar_Node earlier)
        {
            this.g_star = g_star;
            this.h_star = h_star;
            this.f_star = f_star;
            this.location = location;
            this.earlier = earlier;
        }

        public boolean equals(Object additional)
        {
            if(additional == null) return false;

            if(additional.getClass() != getClass()) return false;

            return (Objects.equals(this.location, ((AStar_Node) additional).location));
        }

        public int getG_star() { return g_star; }
        public int getF_star() { return f_star; }
        public int getH_star() { return h_star; }
        public Point getPos() { return location; }
        public AStar_Node getEarlier() { return earlier; }
    }
}