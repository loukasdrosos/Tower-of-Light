package main;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cursor {

    private int x, y; // x and y position on map
    private int col, row, preCol, preRow;
    private String direction = "none";
    private BufferedImage sprite;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private boolean moving = false;
    private int pixelCounter = 0;

    // Variables that control cursor movement speed
    private int moveDelayCounter = 0; // Increments each frame, and when it reaches moveDelayThreshold, the cursor is allowed to move.
    private int moveDelayThreshold = 5; // Adjust this value to change the speed

    GamePanel gp;
    private KeyHandler keyH;

    public Cursor (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage, Cursor not loading properly");
        }
    }

    public void setStartingPosition(int startCol, int startRow) {
        this.col = startCol;
        this.row = startRow;
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row;
    }

    //CURSOR MOVEMENT
    public void moveUp () {
        if (row > 0) { // Check if not at the top edge
            this.row -= 1;
            y = getY(row);
        }
    }

    public void moveDown () {
        if (row < 51) { // Check if not at the bottom edge
            this.row += 1;
            y = getY(row);
        }
    }

    public void moveLeft () {
        if (col > 0) { // Check if not at the left edge
            this.col -= 1;
            x = getX(col);
        }
    }

    public void moveRight () {
        if (col < 51) { // Check if not at the right edge
            this.col += 1;
            x = getX(col);
        }
    }

    public void loadImage() {
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream("/Cursor/Cursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (gp.selectedUnit == null) {
            // Increment the delay counter
            moveDelayCounter++;

            // Only move the cursor when the delay counter reaches the threshold
            if (moveDelayCounter >= moveDelayThreshold) {
                // Determine if any cursor movement key is pressed
                if (keyH.isCursorUpPressed() && !moving) {
                    direction = "up";
                    moving = true;
                } else if (keyH.isCursorDownPressed() && !moving) {
                    direction = "down";
                    moving = true;
                } else if (keyH.isCursorLeftPressed() && !moving) {
                    direction = "left";
                    moving = true;
                } else if (keyH.isCursorRightPressed() && !moving) {
                    direction = "right";
                    moving = true;
                } else if (!keyH.isCursorUpPressed() && !keyH.isCursorDownPressed() &&
                        !keyH.isCursorLeftPressed() && !keyH.isCursorRightPressed()) {
                    // If no key is pressed, stop movement
                    moving = false;
                    direction = "none";
                }

                // Move cursor by a full tile (16 pixels) in the direction
                if (moving) {
                    switch (direction) {
                        case "up":
                            moveUp();
                            break;
                        case "down":
                            moveDown();
                            break;
                        case "left":
                            moveLeft();
                            break;
                        case "right":
                            moveRight();
                            break;
                    }

                    // Ensure the cursor stays centered on the tile
                    x = col * gp.getTileSize();
                    y = row * gp.getTileSize();

                    // Reset the moving flag if the key is released or tile movement is complete
                    if ((!keyH.isCursorUpPressed() && direction.equals("up")) ||
                            (!keyH.isCursorDownPressed() && direction.equals("down")) ||
                            (!keyH.isCursorLeftPressed() && direction.equals("left")) ||
                            (!keyH.isCursorRightPressed() && direction.equals("right"))) {
                        moving = false;
                        direction = "none";
                    }

                    // Reset the delay counter after moving
                    moveDelayCounter = 0;
                }
            }
        }
            if (gp.selectedUnit != null) {

                if (keyH.isCursorUpPressed() == true || keyH.isCursorDownPressed() == true
                        || keyH.isCursorLeftPressed() == true || keyH.isCursorRightPressed() == true) {

                    if (keyH.isCursorUpPressed() == true) {
                        moveUp();
                    } else if (keyH.isCursorDownPressed() == true) {
                        moveDown();
                    } else if (keyH.isCursorLeftPressed() == true) {
                        moveLeft();
                    } else if (keyH.isCursorRightPressed() == true) {
                        moveRight();
                    }
                }
            }


        spriteCounter++;
        if (spriteCounter > 20 ) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

//        pixelCounter += spriteSpeed;
//        if (pixelCounter == 16) {
//            moving = false;
//            pixelCounter = 0;
//        }
    }

    public void draw (Graphics2D g2) {
        BufferedImage image = null;

            if (gp.selectedUnit == null) {
                if (spriteNum == 1) {
                    image = sprite;
                }
                if (spriteNum == 2) {
                    image = null;
                }
            } else  {
                   image = sprite;
            }
       g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
    }

    //GETTERS && SETTERS

    public int getX(int col) {
        return col * gp.getTileSize();
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY(int row) {
        return row * gp.getTileSize();
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPreCol() {
        return preCol;
    }

    public void setPreCol(int preCol) {
        this.preCol = preCol;
    }

    public int getPreRow() {
        return preRow;
    }

    public void setPreRow(int preRow) {
        this.preRow = preRow;
    }
}
