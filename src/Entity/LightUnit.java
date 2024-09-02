package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class LightUnit extends Entity{

    protected int moveDelayThreshold = 7; // Number of frames to wait before moving

    protected boolean isSelected = false;  // Track if the unit is selected
    protected boolean isMoving = false;  // Track if the unit is moving

    public LightUnit (GamePanel gp, KeyHandler keyH, int startCol, int startRow) {
        this.gp = gp;           // Reference to the game panel
        this.keyH = keyH;       // Reference to the key handler
        this.col =  startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX(col);          // Calculate initial x position based on column
        y = getY(row);          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row

        // Load unit's images for animations
        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage, LightUnit not loading properly");
        }
    }

    // Method to handle the movement of the LightUnit
    public void move() {
        // Check if the unit is not in a waiting state
        if (!wait) {
            // Only move the unit if it is selected and is able to move
            if (isSelected && isMoving) {
                moveDelayCounter++;  // Increment the delay counter
                // Check if the delay counter has reached the threshold to move the unit
                if (moveDelayCounter >= moveDelayThreshold) {
                    moveDelayCounter = 0; // Reset the counter after moving

                    // Capture the intended move direction
                    if (keyH.isUpPressed()) {
                        direction = "up";
                    } else if (keyH.isDownPressed()) {
                        direction = "down";
                    } else if (keyH.isLeftPressed()) {
                        direction = "left";
                    } else if (keyH.isRightPressed()) {
                        direction = "right";
                    } else if (!keyH.isUpPressed()  && !keyH.isDownPressed()
                            && !keyH.isLeftPressed() && !keyH.isRightPressed()) {
                        direction = "none"; // If no key is pressed, stop movement
                    }

                    // Calculate target position based on direction
                    int targetCol = col;
                    int targetRow = row;

                    switch (direction) {
                        case "up":
                            targetRow = row - 1;
                            break;
                        case "down":
                            targetRow = row + 1;
                            break;
                        case "left":
                            targetCol = col - 1;
                            break;
                        case "right":
                            targetCol = col + 1;
                            break;
                    }

                    // Check if the move is allowed and update position accordingly
                    if (allowedMove(targetCol, targetRow) && gp.cChecker.validTile(targetCol, targetRow)) {
                        col = targetCol;    // Update the column position
                        row = targetRow;    // Update the row position
                        updatePosition();   // Update the pixel position based on the new tile position
                    }
                }
            }
        }
    }

    // Method to start the unit's turn, enabling actions
    @Override
    public void startTurn() {
        wait = false;
    }

    // Method to end the unit's turn, disabling actions and resetting state
    @Override
    public void endTurn() {
        isSelected = false; // Deselect the unit
        isMoving = false;   // Stop the unit's movement
        wait = true;        // Set the unit to a waiting state
        preCol = col;       // Update the previous column to the current column
        preRow = row;       // Update the previous row to the current row
        direction = "none"; // Reset the direction
    }

    // Method to reset the unit's position to the previous position
    public void resetPosition() {
        setIsMoving(false);     // Stop the unit's movement
        setIsSelected(false);   // Deselect the unit
        col = preCol;   // Revert to the previous column
        row = preRow;   // Revert to the previous row
        x = getX(col);  // Update the x position in pixels
        y = getY(row);  // Update the y position in pixels
        direction = "none"; // Reset the direction
    }

    // Method to update the unit's state, called every frame
    @Override
    public void update() {
        // Allow movement only during the player's phase
        if (gp.TurnM.getPlayerPhase()) {
            move();
        }

        // Update sprite animation
        spriteCounter++;
        // Toggle between sprite frames
        if (spriteCounter > 20) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;  // Reset the sprite counter
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Left_2.png"));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/LightUnits/Human_Prince_Right_1.png")));
            right2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Right_2.png"));
            default1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Default_1.png"));
            default2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Default_2.png"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    // Getters && Setters

    public boolean getIsSelected () { return isSelected; } // Return whether the unit is selected

    public void setIsSelected (boolean x) { this.isSelected = x; } // Set whether the unit is selected

    public boolean getIsMoving () { return isMoving; } // Return whether the unit is moving

    public void setIsMoving(boolean x) { this.isMoving = x; } // Set whether the unit is moving
}