package main;

import Entity.LightUnit;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cursor {

    private int x, y; // x and y position on map
    private int col, row; // column and row position on the map

    private BufferedImage sprite; // Sprite for the cursor's visual representation
    private int spriteCounter = 0; // Counter for sprite animation timing
    private int spriteNum = 1; // Determines which sprite image to display

    // Variables that control cursor movement
    private String direction = "none"; // Direction of cursor movement
    private boolean moving = false; // Indicates if the cursor is currently moving
    private int moveDelayCounter = 0; // Counts frames to manage movement delay
    private int moveDelayThreshold = 5; // Threshold to control the speed of cursor movement

    private boolean shiftPressed = false; // Track if shift has been handled in this update

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

    // Sets the starting position of the cursor on the map grid
    public void setStartingPosition(int startCol, int startRow) {
        this.col = startCol;
        this.row = startRow;
        x = getX(col);
        y = getY(row);
    }

    // Moves the cursor up by one tile if not at the top edge of the map
    public void moveUp () {
        if (row > 0) { // Check if not at the top edge
            row -= 1;
            y = getY(row);
        }
    }

    // Moves the cursor down by one tile if not at the bottom edge of the map
    public void moveDown () {
        if (row < 51) { // Check if not at the bottom edge
            row += 1;
            y = getY(row);
        }
    }

    // Moves the cursor left by one tile if not at the left edge of the map
    public void moveLeft () {
        if (col > 0) { // Check if not at the left edge
            col -= 1;
            x = getX(col);
        }
    }

    // Moves the cursor right by one tile if not at the right edge of the map
    public void moveRight () {
        if (col < 51) { // Check if not at the right edge
            col += 1;
            x = getX(col);
        }
    }

    // Loads the cursor sprite image
    public void loadImage() {
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream("/Cursor/Cursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Updates the cursor's position based on its current column and row
    public void updatePosition() {
        x = getX(col);
        y = getY(row);
    }

    // Updates the cursor's state and position based on input and game state
    public void update() {
        // Check if it's the player's turn
        if (gp.TurnM.getPlayerPhase()) {
            // Check if no unit is currently selected
            if (gp.selectedUnit == null) {

                moveAroundPlayers(); // Traverse through light units

                // Reset shiftPressed if Shift key is released
                if (!keyH.isShiftPressed()) {
                    shiftPressed = false;
                }

                // Increment the delay counter
                moveDelayCounter++;

                // Only move the cursor when the delay counter reaches the threshold
                if (moveDelayCounter >= moveDelayThreshold) {
                    // Determine if any cursor movement key is pressed
                    if (keyH.isUpPressed() && !moving) {
                        direction = "up";
                        moving = true;
                    } else if (keyH.isDownPressed() && !moving) {
                        direction = "down";
                        moving = true;
                    } else if (keyH.isLeftPressed() && !moving) {
                        direction = "left";
                        moving = true;
                    } else if (keyH.isRightPressed() && !moving) {
                        direction = "right";
                        moving = true;
                    } else if (!keyH.isUpPressed() && !keyH.isDownPressed() &&
                            !keyH.isLeftPressed() && !keyH.isRightPressed()) {
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
                        updatePosition();

                        // Reset the moving flag if the key is released or tile movement is complete
                        if ((!keyH.isUpPressed() && direction.equals("up")) ||
                                (!keyH.isDownPressed() && direction.equals("down")) ||
                                (!keyH.isLeftPressed() && direction.equals("left")) ||
                                (!keyH.isRightPressed() && direction.equals("right"))) {
                            moving = false;
                            direction = "none";
                        }

                        // Reset the delay counter after moving
                        moveDelayCounter = 0;
                    }
                }
            }

            // Update cursor position if a selected unit is moving
            if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected()) {
                if (gp.selectedUnit.getIsMoving()) {
                    col = gp.selectedUnit.getCol();
                    row = gp.selectedUnit.getRow();
                    updatePosition();
                }
            }

            // Update sprite animation
            spriteCounter++;
            // Toggle between two sprite images for animation
            if (spriteCounter > 20) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0; // Reset sprite counter
            }
        } else {
            // If it's not the player's turn, position cursor at the first light unit
            if (!gp.simLightUnits.isEmpty()) {
                col = gp.simLightUnits.get(0).getCol();
                row = gp.simLightUnits.get(0).getRow();
                updatePosition();
            }
        }
    }

    // Handles cursor movement when Shift key is pressed
    public void moveAroundPlayers() {
        if (gp.selectedUnit == null && keyH.isShiftPressed() && !shiftPressed) {
            // Filter the light units to include only those that have wait == false
            List<LightUnit> activeUnits = new ArrayList<>();
            for (LightUnit unit : gp.simLightUnits) {
                if (!unit.getWait()) {
                    activeUnits.add(unit);
                }
            }

            if (activeUnits.isEmpty()) return; // Exit if there are no active light units

            // Find the current active light unit under the cursor
            LightUnit currentUnit = null;
            for (LightUnit unit : activeUnits) {
                if (unit.getCol() == col && unit.getRow() == row) {
                    currentUnit = unit;
                    break;
                }
            }

            if (currentUnit != null) {
                // Move to the next active light unit in the list, wrapping around if necessary
                int index = activeUnits.indexOf(currentUnit);
                int nextIndex = (index + 1) % activeUnits.size(); // Wrap around
                LightUnit nextUnit = activeUnits.get(nextIndex);
                col = nextUnit.getCol();
                row = nextUnit.getRow();
            } else {
                // If not on an active light unit, move to the first active light unit
                LightUnit firstUnit = activeUnits.get(0);
                col = firstUnit.getCol();
                row = firstUnit.getRow();
            }

            updatePosition();     // Update cursor position
            shiftPressed = true;  // Mark Shift key as handled
        }
    }


    // Draws the cursor on the screen
    public void draw (Graphics2D g2) {
        BufferedImage image = null;

            // Cursor blinks if no unit is selected, solid cursor if a player unit is selected
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

    //Getters

    public int getX(int col) {
        return col * gp.getTileSize();
    } // Calculate x position in pixels based on column

    public int getY(int row) { return row * gp.getTileSize(); } // Calculate y position in pixels based on row

    public int getCol() { return col; } // Get the cursor's current column

    public int getRow() { return row; } // Get the cursor's current row
}
