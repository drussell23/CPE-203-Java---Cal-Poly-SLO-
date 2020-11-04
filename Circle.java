public class Circle
{
    private Point Center;
    private double radius;

    Circle(Point circle_1, double radius_1)
    {
        Center = circle_1;
        radius = radius_1;
    }
    public double getRadius()
    {
        return radius;
    }
    public Point getCenter()
    {
        return Center;
    }
}