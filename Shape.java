import java.awt.*;

public interface Shape
{
    //Methods
    Color getColor();
    void setColor(Color color);
    double getArea();
    void translate(Point point);
    double getPerimeter();
}