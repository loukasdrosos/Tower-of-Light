package Entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Entity {

    // Variables representing the unit's position on the map in pixels and tiles
    protected int x, y;  // x and y position on the map in pixels
    protected int col, row, preCol, preRow; // Current and previous tile positions (columns and rows)

    protected BufferedImage default1, default2, up1, up2, down1, down2, left1, left2, right1, right2; // BufferedImages for different animation frames and directions
    protected int spriteCounter = 0;  // Counter to control sprite animation timing
    protected int spriteNum = 1;      // Variable to track which sprite frame to display

    // Movement-related variables
    protected String direction = "none"; // Direction the unit is currently moving in
    protected int moveDelayCounter = 0;  // Counter for delaying movement, allowing smooth movement between tiles

    protected boolean wait = false; // Variable that controls if unit can take action or not

    GamePanel gp;
    KeyHandler keyH;

    // Method to get the range of movement for the unit (returns a list of possible moves)
    public List<int[]> getMovementRange() {
        List<int[]> moves = new ArrayList<>();
        // Current position
        moves.add(new int[]{0, 0});

        // Movement by 1 tile
        moves.add(new int[]{1, 0});   // 1 tile right
        moves.add(new int[]{-1, 0});  // 1 tile left
        moves.add(new int[]{0, 1});   // 1 tile down
        moves.add(new int[]{0, -1});  // 1 tile up

        return moves;
    }

    // Method to check if a move to a target tile is allowed
    public boolean allowedMove (int targetCol, int targetRow) {
        // Check if the target tile is within the map bounds
        if (gp.cChecker.isWithinMap(targetCol, targetRow)) {
            // Check if the move is valid based on unit's movement range
            if ((targetCol == preCol && targetRow == preRow) || Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1) {
                return true;
            }
        }
        return false;
    }

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

}




