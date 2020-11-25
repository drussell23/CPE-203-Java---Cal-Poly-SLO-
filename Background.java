import java.util.List;
import processing.core.PImage;

class Background
{
    public String id;
    public List<PImage> images;
    public int imageIndex;

    public Background(String id, List<PImage> images)
    {
        this.id = id;
        this.images = images;
    }

    public static PImage getCurrentImage(Object entity) {
        if (entity instanceof Background) {
            return ((Background) entity).images
                    .get(((Background) entity).imageIndex);
        } else if (entity instanceof Abstract_Entity) {
            return ((Abstract_Entity) entity).getImages().get(((Abstract_Entity) entity).getImageIndex());
        } else {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            entity));
        }
    }
}