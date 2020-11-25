import processing.core.PImage;

import java.util.List;

abstract public class Fire_MinerEntity extends Abstract_Moveable
{
    private AStarPathingStrategy strategy = new AStarPathingStrategy();
    protected int resourceLimit;


    public Fire_MinerEntity(String id, Point position, List<PImage> images,
                            int actionPeriod, int animationPeriod, int resourceLimit){
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    protected int getResourceLimit() { return resourceLimit; }
    protected abstract int getResourceCount();
}
