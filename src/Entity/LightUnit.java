package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;

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

    // Method to select a player unit (LightUnit) based on the cursor's position
    public void SelectPlayerUnit() {
        if (keyH.isAPressed()) {
            // Check if a player is currently selected
            if (gp.selectedUnit == null) {
                // Check if the cursor's position matches the position of this player unit (LightUnit)
                if (gp.cursor.getCol() == this.col && gp.cursor.getRow() == this.row && !this.wait) {
                    gp.selectedUnit = this; // Select player unit
                    isSelected = true; //Activate the selected player unit
                    isMoving = true; // Allow player to move
                }
            }
        }
    }

    // Method to deselect the currently selected player unit and reset its position
    public void UnselectPlayerUnit() {
        if (keyH.isZPressed()) {
            // Only proceed if a unit is selected, is marked as selected, and is currently moving
            if (gp.selectedUnit != null && isSelected && isMoving) {
                resetPosition(); // Return player to starting position
                gp.selectedUnit = null; // Deselect the player
            }
        }
    }

    // Method to end the turn of the currently selected player unit
    public void endSelectedUnitTurn() {
        if (keyH.isWPressed()) {
            // Only proceed if a unit is selected, is marked as selected, and is currently moving
            if (gp.selectedUnit != null && isSelected && !wait && isMoving) {
                endTurn(); // End player's turn
                gp.selectedUnit = null; // Deselect the player
            }
        }
    }

    /* Calculate all valid tiles the unit can move to within its movement range with the use of Breadth-First-Search (BFS)
    BFS is well-suited for this scenario because explores all possible moves level by level, which means it considers
    all closer tiles before moving on to further ones. This is useful in grid-based games where movement range is limited */
    public List<int[]> calculateValidMoves() {
        List<int[]> validMoves = new ArrayList<>();

        /* Use a queue for breadth-first search (BFS) to explore tiles within the movement range
        This queue will hold the tiles to be explored, with each tile being represented by its column, row,
        and the distance from the starting position */
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{preCol, preRow, 0}); // Start from the current position with 0 distance traveled

        // Track visited tiles to prevent revisiting the same tile
        boolean[][] visited = new boolean[gp.getMaxMapCol()][gp.getMaxMapRow()];
        visited[preCol][preRow] = true; // Mark the starting position as visited

        // Continue exploring tiles until there are no more to explore
        while (!queue.isEmpty()) {
            int[] current = queue.poll(); // Get the current tile from the front of the queue
            int currentCol = current[0];
            int currentRow = current[1];
            int currentDistance = current[2];

            // Add the current tile as a valid move option
            validMoves.add(new int[]{currentCol, currentRow});

            // Checks all possible movement directions (up, down, left, right) from the current tile
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : directions) {
                int newCol = currentCol + dir[0];
                int newRow = currentRow + dir[1];
                int newDistance = currentDistance + 1;

                // Check if the new tile is within the map bounds, not visited, within movement range, not wall and not occupied by an enemy unit
                if (gp.cChecker.isWithinMap(newCol, newRow) && !visited[newCol][newRow] &&
                        newDistance <= movement && gp.cChecker.NonCollisionTile(newCol, newRow) && gp.cChecker.noEnemyOnTile(newCol, newRow)) {
                    // If the tile is valid, add it to the queue to be explored
                    queue.add(new int[]{newCol, newRow, newDistance});
                    visited[newCol][newRow] = true; // Mark the tile as visited
                }
            }
        }
        return validMoves; // Return the list of all valid move tiles
    }

    @Override
    public void move() {
        // Check if the unit is not in a waiting state, is selected, and is allowed to move
        if (gp.selectedUnit != null && !wait && isSelected && isMoving) {
            moveDelayCounter++; // Increment the move delay counter

            // Check if the delay counter has reached or exceeded the move delay threshold
            if (moveDelayCounter >= moveDelayThreshold) {
                moveDelayCounter = 0; // Reset the move delay counter

                // Calculate all possible valid moves from the current position
                List<int[]> validMoves = calculateValidMoves();

                // Initialize the target position with the current position
                int targetCol = col;
                int targetRow = row;

                // Determine the intended move direction based on key presses
                if (keyH.isUpPressed()) {
                    direction = "up";
                } else if (keyH.isDownPressed()) {
                    direction = "down";
                } else if (keyH.isLeftPressed()) {
                    direction = "left";
                } else if (keyH.isRightPressed()) {
                    direction = "right";
                } else if (!keyH.isUpPressed() && !keyH.isDownPressed() &&
                        !keyH.isLeftPressed() && !keyH.isRightPressed()) {
                    direction = "none"; // If no key is pressed, stop movement
                }

                // Update target position based on the direction
                switch (direction) {
                    case "up":
                        targetRow = row - 1; // Move up
                        break;
                    case "down":
                        targetRow = row + 1; // Move down
                        break;
                    case "left":
                        targetCol = col - 1; // Move left
                        break;
                    case "right":
                        targetCol = col + 1; // Move right
                        break;
                }

                // Check if the target position is within the list of valid moves
                boolean canMove = false;
                for (int[] move : validMoves) {
                    if (move[0] == targetCol && move[1] == targetRow) {
                        canMove = true; // The move is valid
                        break;
                    }
                }

                // If the move is valid, update the unit's position
                if (canMove) {
                    col = targetCol;  // Update column position
                    row = targetRow;  // Update row position
                    updatePosition(); // Update pixel position based on the new tile position
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
        isMoving = false;   // Stop the unit's movement
        wait = true;        // Set the unit to a waiting state
        preCol = col;       // Update the previous column to the current column
        preRow = row;       // Update the previous row to the current row
        direction = "none"; // Reset the direction
        isSelected = false; // Deselect the unit
    }

    // Method to reset the unit's position to the previous position
    public void resetPosition() {
        isMoving = false;     // Stop the unit's movement
        col = preCol;   // Revert to the previous column
        row = preRow;   // Revert to the previous row
        x = getX(col);  // Update the x position in pixels
        y = getY(row);  // Update the y position in pixels
        direction = "none"; // Reset the direction
        isSelected = false;   // Deselect the unit
    }

    // Method to update the unit's state, called every frame
    @Override
    public void update() {
        // Allow movement only during the player's phase
        if (gp.TurnM.getPlayerPhase()) {
            SelectPlayerUnit();
            UnselectPlayerUnit();
            endSelectedUnitTurn();
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