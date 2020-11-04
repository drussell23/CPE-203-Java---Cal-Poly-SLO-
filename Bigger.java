import java.util.Arrays;
import java.util.*;

public class Bigger
{
    public static double whichIsBigger(Circle circle_1, Rectangle Rectangle_1, Polygon polygon_1)
    {
        List<Point> points_polygon = Arrays.asList(new Point(0.0, 0.0),
                                                   new Point(3.0, 2.0),
                                                   new Point(1.0, 4.0),
                                                   new Point(-1.0, 4.0));

        double p_Circle = Util.perimeter(circle_1);
        double p_Rectangle = Util.perimeter(Rectangle_1);
        double p_Polygon = Util.perimeter(polygon_1);

        if(p_Circle > p_Rectangle && p_Circle > p_Polygon)
        {
            return -1.0;
        }
        if(p_Rectangle > p_Circle && p_Rectangle > p_Polygon)
        {
            return 1.0;
        }
        else
            {
            return 0.0;
        }
    }
}