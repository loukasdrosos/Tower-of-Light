package main;

import Entity.ChaosUnit;
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
    private boolean moving = false; // Indicates if the cursor is currently moving
    private int moveDelayCounter = 0; // Counts frames to manage movement delay
    private int moveDelayThreshold = 5; // Threshold to control the speed of cursor movement

    private boolean shiftPressed = false; // Track if shift has been handled in this update
    private int enemyIndex = 0;


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
            gp.playSE(0);
        }
    }

    // Moves the cursor down by one tile if not at the bottom edge of the map
    public void moveDown () {
        if (row < 51) { // Check if not at the bottom edge
            row += 1;
            y = getY(row);
            gp.playSE(0);
        }
    }

    // Moves the cursor left by one tile if not at the left edge of the map
    public void moveLeft () {
        if (col > 0) { // Check if not at the left edge
            col -= 1;
            x = getX(col);
            gp.playSE(0);
        }
    }

    // Moves the cursor right by one tile if not at the right edge of the map
    public void moveRight () {
        if (col < 51) { // Check if not at the right edge
            col += 1;
            x = getX(col);
            gp.playSE(0);
        }
    }

    // Updates the cursor's state and position based on input and game state
    public void update() {
        // Check if it's the player's turn
        if (gp.TurnM.getPlayerPhase()) {
            // Check if no unit is currently selected
            if (gp.selectedUnit == null) {

                moveAroundPlayers(); // Traverse through light units

                // Increment the delay counter
                moveDelayCounter++;

                // Only move the cursor when the delay counter reaches the threshold
                if (moveDelayCounter >= moveDelayThreshold) {
                    // Determine if any cursor movement key is pressed
                    if (keyH.isUpPressed() && !moving) {
                        moveUp();
                        moving = true;
                    }
                    else if (keyH.isDownPressed() && !moving) {
                        moveDown();
                        moving = true;
                    }
                    else if (keyH.isLeftPressed() && !moving) {
                        moveLeft();
                        moving = true;
                    }
                    else if (keyH.isRightPressed() && !moving) {
                        moveRight();
                        moving = true;
                    }

                    // Ensure the cursor stays centered on the tile
                    updatePosition();

                    // Reset the moving flag if the key is released or tile movement is complete
                    if (!keyH.isUpPressed() || !keyH.isDownPressed() || !keyH.isLeftPressed() || !keyH.isRightPressed()) {
                        moving = false;
                    }

                    // Reset the delay counter after moving
                    moveDelayCounter = 0;
                }
            }

            // Update cursor position if a player unit is selected
            if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected()) {
                // Update cursor's position based on player unit's movement
                if (gp.selectedUnit.getIsMoving()) {
                    col = gp.selectedUnit.getCol();
                    row = gp.selectedUnit.getRow();
                    updatePosition();
                }
                // Update cursor's position based on enemies in selected player unit's range
                if (gp.selectedUnit.getIsAttacking()) {
                    List<ChaosUnit> enemiesInRange = gp.selectedUnit.getEnemiesInRange();
                    // Increment the delay counter
                    moveDelayCounter++;

                    // Only move the cursor when the delay counter reaches the threshold
                    if (moveDelayCounter >= moveDelayThreshold + 3) {
                        if (keyH.isRightPressed() && !moving) {
                            if (enemyIndex < enemiesInRange.size() - 1) {
                                enemyIndex++;  // Move to the next enemy
                            } else {
                                enemyIndex = 0;  // Wrap around to the first enemy
                            }
                            moving = true;
                        } else if (keyH.isLeftPressed() && !moving) {
                            if (enemyIndex > 0) {
                                enemyIndex--;  // Move to the previous enemy
                            } else {
                                enemyIndex = enemiesInRange.size() - 1;  // Wrap around to the last enemy
                            }
                            moving = true;
                        }

                        col = enemiesInRange.get(enemyIndex).getCol();
                        row = enemiesInRange.get(enemyIndex).getRow();
                        updatePosition();

                        // Reset the moving flag if the key is released
                        if (!keyH.isLeftPressed() || !keyH.isRightPressed()) {
                            moving = false;
                        }

                        // Reset the delay counter after moving
                        moveDelayCounter = 0;
                    }
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

            updatePosition();      // Update cursor position
            shiftPressed = true;  // Mark Shift key as handled
            gp.playSE(6);
        }
        // Reset shiftPressed if Shift key is released
        if (!keyH.isShiftPressed()) {
            shiftPressed = false;
        }
    }

    // Updates the cursor's position based on its current column and row
    public void updatePosition() {
        x = getX(col);
        y = getY(row);
    }

    // Loads the cursor sprite image
    public void loadImage() {
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream("/Cursor/Cursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
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
