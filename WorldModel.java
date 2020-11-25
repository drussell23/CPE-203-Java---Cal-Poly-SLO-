import processing.core.PImage;
import java.util.*;

@SuppressWarnings("ALL")
final class WorldModel
{
    private final int numRows;
    private final int numCols;
    private final Background[][] background;
    private final Abstract_Entity[][] occupancy;
    private final Set<Abstract_Entity> entities;

    public static final int ORE_REACH = 1;

    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;


    private static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    private static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 4;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;


    private static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;


    private static final String SMITH_KEY = "blacksmith";
    public static final int SMITH_NUM_PROPERTIES = 4;
    public static final int SMITH_ID = 1;
    public static final int SMITH_COL = 2;
    public static final int SMITH_ROW = 3;


    private static final String VEIN_KEY = "vein";
    public static final int VEIN_NUM_PROPERTIES = 5;
    public static final int VEIN_ID = 1;
    public static final int VEIN_COL = 2;
    public static final int VEIN_ROW = 3;
    public static final int VEIN_ACTION_PERIOD = 4;

    private static final String FSMITH_KEY = "fireblacksmith";
    private static final int FSMITH_NUM_PROPERTIES = 4;
    private static final int FSMITH_ID = 1;
    private static final int FSMITH_COL = 2;
    private static final int FSMITH_ROW = 3;

    private static final String FBGND_KEY = "firebackground";
    private static final int FBGND_NUM_PROPERTIES = 4;
    private static final int FBGND_ID = 1;
    private static final int FBGND_COL = 2;
    private static final int FBGND_ROW = 3;

    private static final String FMAN_KEY = "fireman";

    public WorldModel(int numRows, int numCols, Background defaultBackground)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Abstract_Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++)
        {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }
    public int getNumRows(){return numRows;}
    public int getNumCols(){return numCols;}
    public Set<Abstract_Entity> getEntities(){return entities;}


    public static boolean parseBackground(String[] properties,
                                          WorldModel world, ImageStore imageStore) {
        if (properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(pt, new Background(id, imageStore.getImageList(imageStore, id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

    public boolean parseFireBackground(String[] properties, ImageStore imageStore){
        if(properties.length == FBGND_NUM_PROPERTIES){
            Point pts = new Point(Integer.parseInt(properties[FBGND_COL]),
                    Integer.parseInt(properties[FBGND_ROW]));
            String id = properties[FBGND_ID];
            this.setBackground(pts, new Fire_Background(id, imageStore.getImageList(id)));
        }
        return properties.length == BGND_NUM_PROPERTIES;
    }

    public static boolean parseMiner(String[] properties, WorldModel world,
                                     ImageStore imageStore) {
        if (properties.length == MINER_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            Abstract_Entity entity = new MinerNotFull(properties[MINER_ID],
                    pt,
                    imageStore.getImageList(imageStore, MINER_KEY),
                    Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[MINER_LIMIT]),
                    0
            );
            world.tryAddEntity(entity);
        }
        return properties.length == MINER_NUM_PROPERTIES;
    }

    public void addEntities(Abstract_Entity entity){
        this.entities.add(entity);
    }

    public void setOccupancyCell(Point position, Abstract_Entity entity){
        this.occupancy[position.getY()][position.getX()] = entity;
    }

    public static boolean parseObstacle(String[] properties, WorldModel world,
                                        ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Abstract_Entity entity = new Obstacle(properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(imageStore, OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    public static boolean parseOre(String[] properties, WorldModel world,
                                   ImageStore imageStore) {
        if (properties.length == ORE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                    Integer.parseInt(properties[ORE_ROW]));
            Abstract_Entity entity = new Ore(properties[ORE_ID],
                    pt, imageStore.getImageList(imageStore, ORE_KEY),
                    Integer.parseInt(properties[ORE_ACTION_PERIOD]));
            world.tryAddEntity(entity);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }

    public static boolean parseSmith(String[] properties, WorldModel world,
                                     ImageStore imageStore) {
        if (properties.length == SMITH_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Abstract_Entity entity = new Blacksmith(properties[SMITH_ID],
                    pt, imageStore.getImageList(imageStore, SMITH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    public static boolean parseVein(String[] properties, WorldModel world,
                                    ImageStore imageStore) {
        if (properties.length == VEIN_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                    Integer.parseInt(properties[VEIN_ROW]));
            Abstract_Entity entity = new Vein(properties[VEIN_ID],
                    pt, imageStore.getImageList(imageStore, VEIN_KEY),
                    Integer.parseInt(properties[VEIN_ACTION_PERIOD]));

            world.tryAddEntity(entity);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }

    public  void tryAddEntity( Abstract_Entity entity)
    {
        if (isOccupied( entity.getPosition()))
        {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    public boolean withinBounds(Point pos)
    {
        return pos.y >= 0 && pos.y < this.numRows &&
                pos.x >= 0 && pos.x < this.numCols;
    }

    public boolean isOccupied( Point pos)
    {
        return pos.y >= 0 && pos.y < this.numRows &&
                pos.x >= 0 && pos.x < this.numCols &&
                getOccupancyCell( pos) != null;
    }


    public  Optional<Abstract_Entity> nearestEntity(List<Abstract_Entity> entities, Point pos)
    {
        if (entities.isEmpty())
        {
            return Optional.empty();
        }
        else
        {
            Abstract_Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), pos);

            for (Abstract_Entity other : entities)
            {
                int otherDistance = distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public  int distanceSquared(Point p1, Point p2)
    {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

    public Optional<Abstract_Entity> findNearest(Point pos, Class type)
    {
        List<Abstract_Entity> ofType = new LinkedList<>();
        for (Abstract_Entity entity : entities)
        {
            if (entity.getClass() != type) {
                continue;
            }
            ofType.add(entity);
        }
        return nearestEntity(ofType, pos);
    }


    public  void addEntity(Abstract_Entity entity)
    {
        Point pos = entity.getPosition();
        if (pos.y >= 0 && pos.y < this.numRows &&
                pos.x >= 0 && pos.x < this.numCols)
        {
            Point pos1 = entity.getPosition();
            occupancy[pos1.y][pos1.x] = entity;
            entities.add(entity);
        }
    }

    public  void moveEntity(Abstract_Entity entity, Point pos)
    {
        Point oldPos = entity.getPosition();
        if (pos.y >= 0 && pos.y < this.numRows &&
                pos.x >= 0 && pos.x < this.numCols && !pos.equals(oldPos))
        {
            occupancy[oldPos.y][oldPos.x] = null;
            removeEntityAt( pos);
            occupancy[pos.y][pos.x] = entity;
            entity.setPosition(pos);
        }
    }

    public void removeEntityAt(  Point pos)
    {
        if (pos.y >= 0 && pos.y < this.numRows
                && pos.x >= 0
                && pos.x < this.numCols
                && getOccupancyCell( pos) != null)
        {
            Abstract_Entity entity = getOccupancyCell( pos);

            entity.setPosition(new Point(-1, -1));

            entities.remove(entity);
            occupancy[pos.y][pos.x] = null;
        }
    }

    public Optional<PImage> getBackgroundImage(Point position)
    {
        if (position.y >= 0 && position.y < this.numRows &&
                position.x >= 0 && position.x < this.numCols)
            return Optional.of(Background.getCurrentImage(getBackgroundCell(position)));
        else
        {
            return Optional.empty();
        }
    }

    public  void setBackground(  Point pos, Background background)
    {
        if (pos.y >= 0 && pos.y < this.numRows &&
                pos.x >= 0 && pos.x < this.numCols)
        {
            setBackgroundCell( pos, background);
        }
    }

    public Optional<Abstract_Entity> getOccupant(Point pos)
    {
        if (isOccupied( pos))
        {
            return Optional.of(getOccupancyCell( pos));
        }
        else
        {
            return Optional.empty();
        }
    }

    public Abstract_Entity getOccupancyCell(Point pos)
    {
        return occupancy[pos.y][pos.x];
    }

    public  Background getBackgroundCell(Point pos)
    {
        return background[pos.y][pos.x];
    }

    public void setBackgroundCell(Point pos, Background background)
    {
        this.background[pos.y][pos.x] = background;
    }

    public Optional<Point> findOpenAround( Point pos)
    {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
        {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
            {
                Point newPt = new Point(pos.x + dx, pos.y + dy);
                if (!(!(newPt.y >= 0) || !(newPt.y < this.numRows) ||
                        !(newPt.x >= 0) || !(newPt.x < this.numCols) ||
                        isOccupied(newPt)))
                    return Optional.of(newPt);
            }
        }
        return Optional.empty();
    }
}