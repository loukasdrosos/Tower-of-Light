package Item;

import main.UtilityTool;
import java.awt.image.BufferedImage;

public class Item {

    protected BufferedImage image;
    protected String name;
    protected String description;
    protected int tileSize = 15;

    UtilityTool uTool = new UtilityTool();

    // Placeholder method to set up the main weapon's stats
    public void setupStats() {   }

    // Getter methods

    public BufferedImage getImage() {
        return image;
    }

    public int getX(int col) {
        return col * tileSize;
    } // Calculate x position in pixels based on column

    public String getDescription() { return description; } // Get the item's description

    public String getName() { return name; }
}
