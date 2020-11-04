import processing.core.PImage;
import java.util.List;

public class Blacksmith implements Entity
{
    public final String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    public Blacksmith( String id, Point position,
                       List<PImage> images, int resourceLimit,
                       int resourceCount, int actionPeriod,
                       int animationPeriod)
    {
        this.id=id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public static Blacksmith createBlacksmith(String id, Point position, List<PImage> images)
    {
        return new Blacksmith( id, position, images, 0, 0, 0, 0);
    }

    public Point getPosition() { return this.position; }
    public List<PImage> getImages() { return this.images; }
    public int getImageIndex() { return this.imageIndex; }
    public void setPosition(Point p) { this.position = p; }
    public void setImages(List<PImage> i) { this.images =i; }
    public int getAnimationPeriod() { return this.animationPeriod; }

    public void nextImage()
    {
        this.imageIndex = (this.getImageIndex()+ 1) % this.getImages().size();
    }
}