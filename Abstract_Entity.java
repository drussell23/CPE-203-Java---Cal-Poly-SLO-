import processing.core.PImage;
import java.util.List;

public abstract class Abstract_Entity {
    protected final String id;
    protected Point position;
    public List<PImage> images;
    private int imageIndex;

    public Abstract_Entity(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    List<PImage> getImages() {
        return images;
    }

    Point getPosition() {
        return position;
    }

    void setPosition(Point p) {
        this.position = p;
    }

    int getImageIndex() {
        return imageIndex;
    }

    void setImageIndex(int index) {
        this.imageIndex = index;
    }

}