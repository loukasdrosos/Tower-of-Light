package Item;

import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item {

    protected BufferedImage image;
    protected String name;
    protected String description;
    protected int col, row;
    protected int x, y;
    protected int tileSize = 15;

    UtilityTool uTool = new UtilityTool();

    // Method to update the item's pixel position based on its current tile position
    public void updatePosition () {
        x = getX(col);
        y = getY(row);
    }

    // Placeholder method to set up the main weapon's stats
    public void setupStats() {   }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, tileSize, tileSize, null);
    }

    // Getter methods

    public BufferedImage getImage() {
        return image;
    }

    public int getX(int col) {
        return col * tileSize;
    } // Calculate x position in pixels based on column

    public String getDescription() { return description; } // Get the item's description

    public int getY(int row) { return row * tileSize; } // Calculate y position in pixels based on row

    public int getCol() { return col; } // Get the item's current column

    public int getRow() { return row; } // Get the item's current row

    public void setCol(int col) { this.col = col; } // Set the item's spawn column

    public void setRow(int row) { this.row = row; } // Set the item's spawn row

    public String getName() { return name; }
}
