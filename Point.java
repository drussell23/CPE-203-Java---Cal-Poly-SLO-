final class Point
{
    public final int x;
    public final int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public int getX() { return (x); }
    public int getY() { return (y);}

    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other)
    {
        return (other instanceof Point) &&
                (((Point) other).x == this.x) &&
                (((Point) other).y == this.y);
    }

    public int hashCode()
    {
        int result;
        result = 17 * 31 + x;
        result = result * 31 + y;
        return result;
    }

    public static boolean adjacent(Point p1, Point p2) {
        return (Math.abs(p1.y - p2.y) == 1 && p1.x == p2.x) ||
                (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }

    public static int distanceSquared(Point point_1, Point point_2)
    {
        int delta_X = point_1.x - point_2.x, delta_Y = point_1.y - point_2.y;

        return (delta_X * delta_X + delta_Y * delta_Y);
    }
}