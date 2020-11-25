/**
 * Derek J. Russell
 * November 24th, 2020
 * ===================
 * CPE 203-01 : Assignment 7, CSC/CPE 203
 * ======================================
 * At least one type of existing mobile entity (e.g, miners or blobs) must be affected by the world event,
 * based on proximity to the event location. More specifically, this type of entity should change in
 * appearance and behavior.
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import processing.core.*;

public final class VirtualWorld extends PApplet {

    private static final String FIREMAN_KEY = "fireman";
    private static final String FIREMAN_ID = "fireman";
    private static final int FIREMAN_LIMIT = 100;
    private static final int FIREMAN_ACTION_PERIOD = 5;
    private static final int FIREMAN_ANIMATION_PERIOD = 6;

    private static final int TILE_SIZE = 32;
    private static final int TIMER_ACTION_PERIOD = 100;

    private static final int VIEW_WIDTH = 1080;
    private static final int VIEW_HEIGHT = 960;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;

    private static final String LOAD_FILE_NAME = "gaia.sav";

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    public static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;


    private static double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;
    private long next_time;



    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    public void setup() {
        this.imageStore  = new ImageStore(
                createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
                createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
                TILE_WIDTH, TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        loadImages(imageStore, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= next_time) {
            this.scheduler.updateOnTime(time);
            next_time = time + TIMER_ACTION_PERIOD;
        }
        this.view.drawViewport();
    }

    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
            this.view.shiftView(dx, dy);
        }
    }

    // This mousePressed() function generates a Fireman and Fire.
    public void mousePressed()
    {
        Point pressed      = mouseToPoint(mouseX, mouseY);
        Background Fire    = new Background("fire_1", imageStore.getImageList("fire_1"));

        Point top_Right    = new Point(pressed.x + 1, pressed.y + 1);
        Point top_Middle   = new Point(pressed.x, pressed.y + 1);
        Point top_Left     = new Point(pressed.x - 1, pressed.y + 1);

        Point middle       = new Point(pressed.x, pressed.y);
        Point middle_Right = new Point(pressed.x + 1, pressed.y);
        Point middle_Left  = new Point(pressed.x - 1, pressed.y);

        Point bottom_Right  = new Point(pressed.x + 1, pressed.y - 1);
        Point bottom_Left   = new Point(pressed.x - 1, pressed.y - 1);
        Point bottom_Middle = new Point(pressed.x, pressed.y - 1);

        System.out.println(this.world.getOccupancyCell(new Point(pressed.x, pressed.y)));

        if(this.world.getOccupancyCell(middle) == null){
            Fireman fireman = new Fireman(FIREMAN_ID, middle, imageStore.getImageList(FIREMAN_KEY),
                    FIREMAN_ACTION_PERIOD, FIREMAN_ANIMATION_PERIOD);
            this.world.addEntity(fireman);
            fireman.scheduleActions(scheduler, world, imageStore);
        }

        if(this.world.getOccupancyCell(top_Right) == null){
            this.world.setBackgroundCell(top_Right, Fire);
        }


        if(this.world.getOccupancyCell(top_Middle) == null){
            this.world.setBackgroundCell(top_Middle, Fire);
        }

        if(this.world.getOccupancyCell(top_Left) == null){
            this.world.setBackgroundCell(top_Left, Fire);
        }

        if(this.world.getOccupancyCell(middle_Right) == null){
            this.world.setBackgroundCell(middle_Right, Fire);
        }

        if(this.world.getOccupancyCell(middle_Left) == null){
            this.world.setBackgroundCell(middle_Left, Fire);
        }

        if(this.world.getOccupancyCell(bottom_Right) == null){
            this.world.setBackgroundCell(bottom_Right, Fire);
        }

        if(this.world.getOccupancyCell(bottom_Left) == null){
            this.world.setBackgroundCell(bottom_Left, Fire);
        }

        if(this.world.getOccupancyCell(bottom_Middle) == null){
            this.world.setBackgroundCell(bottom_Middle, Fire);
        }
        redraw();
    }

    private Point mouseToPoint(int x, int y)
    {
        return new Point(mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                ImageStore.getImageList(imageStore, DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        Arrays.fill(img.pixels, color);
        img.updatePixels();
        return img;
    }

    private static void loadImages(ImageStore imageStore, PApplet screen) {
        try {
            Scanner in = new Scanner(new File(VirtualWorld.IMAGE_LIST_FILE_NAME));
            VirtualWorld.loadImages(in, imageStore, screen);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadImages(Scanner in, ImageStore imageStore,
                                  PApplet screen) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                processImageLine(imageStore.images, in.nextLine(), screen);
            } catch (NumberFormatException e) {
                System.out.printf("Image format error on line %d%n",
                        lineNumber);
            }
            lineNumber++;
        }
    }

    public static void processImageLine(Map<String, List<PImage>> images,
                                        String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = ImageStore.getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    ImageStore.setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }
    public static void loadWorld(WorldModel world, String filename,
                                 ImageStore imageStore)
    {
        try
        {
            Scanner in = new Scanner(new File(filename));
            imageStore.load(in, world, imageStore);
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public static void scheduleActions(WorldModel world,
                                       EventScheduler scheduler, ImageStore imageStore)
    {
        for (Abstract_Entity entity : world.getEntities()) {
            if (entity instanceof Abstract_Executable) {
                ((Abstract_Executable) entity).scheduleActions(scheduler, world, imageStore);
            }
        }
    }

    public static void parseCommandLine(String [] args)
    {
        for (String arg : args)
        {
            switch (arg)
            {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + arg);
            }
        }
    }

    public static void main(String [] args)
    {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }
}