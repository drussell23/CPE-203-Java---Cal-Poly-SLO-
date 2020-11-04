import processing.core.PImage;
import java.util.List;

public interface Entity
{
    List <PImage> getImages();
    Point getPosition();
    void setPosition(Point p);

    int getImageIndex();







}