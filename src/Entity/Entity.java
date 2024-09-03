package Entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Entity {

    // Variables representing the unit's position on the map in pixels and tiles
    protected int x, y;  // x and y position on the map in pixels
    protected int col, row, preCol, preRow; // Current and previous tile positions (columns and rows)

    protected BufferedImage default1, default2, up1, up2, down1, down2, left1, left2, right1, right2; // BufferedImages for different animation frames and directions
    protected int spriteCounter = 0;  // Counter to control sprite animation timing
    protected int spriteNum = 1;      // Variable to track which sprite frame to display

    // Movement-related variables
    protected int movement = 3; // The number of tiles the unit can move
    protected String direction = "none"; // Direction the unit is currently moving in
    protected int moveDelayCounter = 0;  // Counter for delaying movement, allowing smooth movement between tiles

    protected boolean wait = false; // Variable that controls if unit can take action or not

    GamePanel gp;
    KeyHandler keyH;

    // Method to update the unit's pixel position based on its current tile position
    public void updatePosition () {
        x = getX(col);
        y = getY(row);
    }

    // Method to draw the unit on the screen
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Select the correct image based on the unit's direction and current sprite frame
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            default:
                if (spriteNum == 1) {
                    image = default1;
                }
                if (spriteNum == 2) {
                    image = default2;
                }
        }
        // Draw the selected image at the unit's position, scaled to the tile size
        g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
    }

    // Placeholder methods

    public void loadImage() {  }   // Placeholder method that loads images for the unit's animation frames

    public void startTurn() {  }  // Placeholder method to start the unit's turn

    public void endTurn() {  }   // Placeholder method to end the unit's turn

    public void update() {    }  // Placeholder method to update the unit's state

    public void move() {    }  // Placeholder method to move a unit

    // Getter methods

    public int getX(int col) {
        return col * gp.getTileSize();
    } // Calculate x position in pixels based on column

    public int getY(int row) { return row * gp.getTileSize(); } // Calculate y position in pixels based on row

    public int getCol() { return col; } // Get the unit's current column

    public int getRow() { return row; } // Get the unit's current row

    public int getPreCol() { return preCol; } // Get the unit's previous column

    public int getPreRow() { return preRow; } // Get the unit's previous row

    public boolean getWait () { return wait; } // Get the unit's wait status

    public int getMovement() { return movement; } // Get the unit's wait status

}




