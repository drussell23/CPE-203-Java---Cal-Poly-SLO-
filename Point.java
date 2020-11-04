import java.lang.*;

class Point
{
    final private double x;
    final private double y;

    public  Point(double x1, double y1)
    {
       x = x1;
       y = y1;
    }
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
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