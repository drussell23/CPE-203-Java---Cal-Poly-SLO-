import java.util.List;
import processing.core.PImage;

public abstract class Abstract_AnimationEntity extends Abstract_Executable {
    private final int animationPeriod;

    public Abstract_AnimationEntity(String id, Point position, List<PImage> images, int actionPeriod,
            int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    void nextImage() {
        this.setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    }

    int getAnimationPeriod() {
        return animationPeriod;
    }
}

    