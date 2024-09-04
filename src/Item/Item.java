package Item;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item {

    protected BufferedImage image;
    protected String name;
    protected int col, row;
    protected int x, y;
    protected int tileSize = 16;
    protected boolean collision = false;

    // Method to update the item's pixel position based on its current tile position
    public void updatePosition () {
        x = getX(col);
        y = getY(row);
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, x, y, tileSize, tileSize, null);
    }

    // Getter methods

    public int getX(int col) {
        return col * tileSize;
    } // Calculate x position in pixels based on column

    public int getY(int row) { return row * tileSize; } // Calculate y position in pixels based on row

    public int getCol() { return col; } // Get the item's current column

    public int getRow() { return row; } // Get the item's current row

    public void setCol(int col) { this.col = col; } // Set the item's spawn column

    public void setRow(int row) { this.row = row; } // Set the item's spawn row
}
