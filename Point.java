public class Point
{
    public final int x;
    public final int y;

    public String toString()
    {
        return ("Point{" + "x=" + x + ", y=" + y + '}');
    }

    public boolean equals(Object o)
    {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        Point point = (Point) o;

        if(x != point.x)
            return false;
        return y == point.y;
    }

    public int hashCode()
    {
        int result = x;
        result = 31 * result + y;
        return result;
    }
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
