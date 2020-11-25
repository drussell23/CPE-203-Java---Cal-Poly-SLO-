import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

final class ImageStore {
    public Map<String, List<PImage>> images;
    public List<PImage> defaultImages;

    public static final int COLOR_MASK = 0xffffff;
    public static final int PROPERTY_KEY = 0;
    public static final String BGND_KEY = "background";
    public static final String MINER_KEY = "miner";
    public static final String OBSTACLE_KEY = "obstacle";
    public static final String ORE_KEY = "ore";
    public static final String VEIN_KEY = "vein";
    public static final String SMITH_KEY = "blacksmith";

    private static final String FSMITH_KEY = "fireblacksmith";
    private static final String FBGND_KEY = "firebackground";
    private static final String FMAN_KEY = "fireman";

    public ImageStore(PImage defaultImage) {
        this.images = new HashMap<>();
        defaultImages = new LinkedList<>();
        defaultImages.add(defaultImage);
    }

    public List<PImage> getImageList(String key){
        return images.getOrDefault(key, defaultImages);
    }

    public static List<PImage> getImageList(ImageStore imageStore, String key) {
        return imageStore.images.getOrDefault(key, imageStore.defaultImages);
    }

    public static List<PImage> getImages(Map<String, List<PImage>> images, String key) {
        return images.computeIfAbsent(key, k -> new LinkedList<>());
    }

    public static void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }

    public void load(Scanner in, WorldModel world, ImageStore imageStore) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!processLine(in.nextLine(), world, imageStore)) {
                    System.err.printf("invalid entry on line %d%n",
                            lineNumber);
                }
            } catch (NumberFormatException e) {
                System.err.printf("invalid entry on line %d%n",
                        lineNumber);
            } catch (IllegalArgumentException e) {
                System.err.printf("issue on line %d: %s%n",
                        lineNumber, e.getMessage());
            }
            lineNumber++;
        }
    }

    public static boolean processLine(String line, WorldModel world,
                                      ImageStore imageStore) {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return WorldModel.parseBackground(properties, world, imageStore);
                case MINER_KEY:
                    return WorldModel.parseMiner(properties, world, imageStore);
                case OBSTACLE_KEY:
                    return WorldModel.parseObstacle(properties, world, imageStore);
                case ORE_KEY:
                    return WorldModel.parseOre(properties, world, imageStore);
                case SMITH_KEY:
                    return WorldModel.parseSmith(properties, world, imageStore);
                case VEIN_KEY:
                    return WorldModel.parseVein(properties, world, imageStore);


            }
        }
        return false;
    }
    public Map<String, List<PImage>> getImageMap(){
        return images;
    }
}