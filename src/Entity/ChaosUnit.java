package Entity;

import main.GamePanel;
import main.UtilityTool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ChaosUnit extends Entity {

    protected int moveDelayThreshold = 7; // Number of frames to wait before moving
    protected int moveSpeed = 8; // Speed at which the unit moves between tiles

    protected boolean boss;
    protected boolean canMove;

    public ChaosUnit(GamePanel gp, boolean isBoss, boolean canMove) {
        super(gp);
        this.boss = boss;
        this.canMove = canMove;
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

    @Override
    public void Defeated() {
        if (HP <= 0) {
            if (name != null) {
                gp.ui.addLogMessage(name + " is defeated.");
            }
            else {
                gp.ui.addLogMessage(String.valueOf(getRace()) + className + " is defeated.");
            }
            gp.simChaosUnits.remove(this);
        }
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

    // Calculates unit's attack range based on its possible movement
    public List<int[]> calculateAttackRange() {
        UtilityTool uTool = new UtilityTool();
        // List to store the positions of tiles within the attack range
        List<int[]> attackRange = new ArrayList<>();

        // Get the list of tiles the unit can move to
        List<int[]> movementRange = calculateMovementRange();

        // Determine the weapon range (the maximum distance at which the unit can attack)
        int weaponRange = 0;
        if (attackType == AttackType.Physical && equippedWeapon != null) {
            weaponRange = equippedWeapon.getRange(); // Get range from equipped weapon
        }
        if (attackType == AttackType.Magical) {
            // magic range
        }

        // For each tile in the movement range, calculate the attack range
        for (int[] moveTile : movementRange) {
            int moveCol = moveTile[0];
            int moveRow = moveTile[1];

            // Calculate all possible attack positions within the Manhattan distance
            for (int d = 1; d <= weaponRange; d++) {
                // Loop through all combinations of x and y differences that sum up to d
                for (int dx = -d; dx <= d; dx++) {
                    int dy = d - Math.abs(dx); // dy is the remainder to ensure Manhattan distance
                    // Check both positive and negative dy to cover all diagonals
                    for (int[] delta : new int[][]{{dx, dy}, {dx, -dy}}) {
                        int attackCol = moveCol + delta[0];
                        int attackRow = moveRow + delta[1];

                        // Check if the attack tile is within the map bounds and not already included in the list
                        if (gp.cChecker.isWithinMap(attackCol, attackRow) && !uTool.containsTile(attackRange, attackCol, attackRow)) {
                            // Add the tile to the attack range list
                            attackRange.add(new int[]{attackCol, attackRow});
                        }
                    }
                }
            }
        }
        return attackRange; // Return the list of tiles within the attack range
    }

    public void takeAction() {
        // If enemy phase
        if (!gp.TurnM.getPlayerPhase()) {
            // If the unit is allowed to move
            if (!wait) {
                endTurn();
            }
        }
    }

/*
    //WRONG MOVEMENT METHOD
    // Method to handle the unit's movement logic
    @Override
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

 */

    // Method to update the unit's state (called every frame)
    @Override
    public void update() {

        // Update the sprite animation
        spriteCounter++;  // Increment the sprite counter
        if (spriteCounter > 20) {   // Change sprite every 20 frames
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;  // Reset sprite counter
        }
    }

}

