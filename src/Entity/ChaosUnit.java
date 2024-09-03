package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ChaosUnit extends Entity {

    protected int moveDelayThreshold = 7; // Number of frames to wait before moving
    protected int moveSpeed = 8; // Speed at which the unit moves between tiles

    public ChaosUnit(GamePanel gp, KeyHandler keyH, int startCol, int startRow) {
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
            System.out.println("Exception loadImage, ChaosUnit not loading properly");
        }
    }

    // Method to start the unit's turn (called at the beginning of its turn)
    @Override
    public void startTurn() {
        wait = false; // Allows the unit to perform actions
    }

    // Method to end the unit's turn (called at the end of its turn)
    @Override
    public void endTurn() {
        wait = true;    // Prevents the unit from performing actions
        preCol = col;   // Save the current column as the previous column
        preRow = row;   // Save the current row as the previous row
        direction = "none";  // Reset the movement direction
    }

    /* Calculate all valid tiles the unit can move to within its movement range with the use of Breadth-First-Search (BFS)
    BFS is well-suited for this scenario because explores all possible moves level by level, which means it considers
    all closer tiles before moving on to further ones. This is useful in grid-based games where movement range is limited */
    public List<int[]> calculateMovementRange() {
        List<int[]> movementRange = new ArrayList<>();

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
            movementRange.add(new int[]{currentCol, currentRow});

            // Checks all possible movement directions (up, down, left, right) from the current tile
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : directions) {
                int newCol = currentCol + dir[0];
                int newRow = currentRow + dir[1];
                int newDistance = currentDistance + 1;

                // Check if the new tile is within the map bounds, not visited, within movement range, not wall and not occupied by a player unit
                if (gp.cChecker.isWithinMap(newCol, newRow) && !visited[newCol][newRow] &&
                        newDistance <= movement && gp.cChecker.NonCollisionTile(newCol, newRow) && gp.cChecker.noPlayerOnTile(newCol, newRow)) {
                    // If the tile is valid, add it to the queue to be explored
                    queue.add(new int[]{newCol, newRow, newDistance});
                    visited[newCol][newRow] = true; // Mark the tile as visited
                }
            }
        }
        return movementRange; // Return the list of all valid move tiles
    }

    // Method to handle the unit's movement logic
    public void move() {
        if (!wait) { // If the unit is allowed to move
            moveDelayCounter++;  // Increment the delay counter

            // Only move if the counter reaches the threshold
            if (moveDelayCounter >= moveDelayThreshold) {
                moveDelayCounter = 0; // Reset the counter after moving

                // Determine the direction to move
                if (gp.cChecker.isWithinMap(col, row - 1) && gp.cChecker.validTile(col, row - 1)) {
                    direction = "up";
                } else if (gp.cChecker.isWithinMap(col + 1, row) && gp.cChecker.validTile(col + 1, row)) {
                    direction = "right";
                } else if (gp.cChecker.isWithinMap(col, row + 1) && gp.cChecker.validTile(col, row + 1)) {
                    direction = "down";
                } else if (gp.cChecker.isWithinMap(col - 1, row) && gp.cChecker.validTile(col - 1, row)) {
                    direction = "left";
                }

                // Determine target position based on direction
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

                if (targetCol == col && targetRow == row) {
                    endTurn();
                }

                // Update the ChaosUnit's position gradually
                if (targetCol != col || targetRow != row) {
                    int targetX = getX(targetCol); // Target x position
                    int targetY = getY(targetRow); // Target y position

                    // Gradually move towards the target x position
                    if (x < targetX) {
                        x += moveSpeed;
                        if (x > targetX) x = targetX;
                    } else if (x > targetX) {
                        x -= moveSpeed;
                        if (x < targetX) x = targetX;
                    }

                    // Gradually move towards the target y position
                    if (y < targetY) {
                        y += moveSpeed;
                        if (y > targetY) y = targetY;
                    } else if (y > targetY) {
                        y -= moveSpeed;
                        if (y < targetY) y = targetY;
                    }

                    // Check if the unit has reached the new tile
                    if (x == targetX && y == targetY) {
                        col = targetCol;    // Update the unit's column
                        row = targetRow;    // Update the unit's row
                        updatePosition();   // Update the col and row based on the new position
                        endTurn();          // End the turn after moving
                    }
                }
            }
        }
    }

    // Method to update the unit's state (called every frame)
    @Override
    public void update() {

        // Update the sprite animation
        spriteCounter++;  // Increment the sprite counter
        if (spriteCounter > 20) {   // Change sprite every 20 frames
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;  // Reset sprite counter
        }
    }

    //Load images for the unit's animations
    public void loadImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Right_2.png"));
            default1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Default_1.png"));
            default2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Default_2.png"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
