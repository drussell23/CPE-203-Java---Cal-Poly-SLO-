import java.lang.Math;

public class Point
{
    private double x;
    private double y;

    Point(double x_1, double y_1)
    {
        x = x_1;
        y = y_1;
    }
    public double getX()
    {
        return (x);
    }
    public double getY()
    {
        return (y);
    }
    public double getRadius()
    {
        return (Math.sqrt(y * y + x * x));
    }
    public double getAngle()
    {
        return (Math.atan2(y, x));
    }
    public Point rotate90()
    {
        double x_prime = this.getRadius() * Math.cos(this.getAngle() + Math.PI/2);
        double y_prime = this.getRadius() * Math.sin(this.getAngle() + Math.PI/2);

        return new Point(x_prime, y_prime);
    }
}
