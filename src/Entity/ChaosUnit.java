package Entity;

import main.GamePanel;
import main.UtilityTool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ChaosUnit extends Entity {

    protected int moveDelayThreshold = 4; // Number of frames to wait before moving
    protected int moveSpeed = 6; // Speed at which the unit moves between tiles

    protected boolean boss;

    private boolean onPath = false;

    private int movementCounter = movement;

    public ChaosUnit(GamePanel gp) {
        super(gp);
        wait = true;
    }

    // Method to start the unit's turn (called at the beginning of its turn)
    @Override
    public void startTurn() {
        wait = false; // Allows the unit to perform actions
        onPath = true;
    }

    // Method to end the unit's turn (called at the end of its turn)
    @Override
    public void endTurn() {
        wait = true;    // Prevents the unit from performing actions
        onPath = false;
        movementCounter = movement;
        preCol = col;   // Save the current column as the previous column
        preRow = row;   // Save the current row as the previous row
        direction = "none";  // Reset the movement direction
    }

    @Override
    public void Defeated() {
        if (HP <= 0) {
            if (name != null) {
                gp.ui.addLogMessage(name + " is defeated");
            } else {
                gp.ui.addLogMessage(String.valueOf(getRace()) + " " + className + " is defeated");
            }
            dropItem();
            gp.ChaosUnits.remove(this);
        }
    }

    public void takeAction() {
        // If enemy phase
        if (!gp.TurnM.getPlayerPhase()) {
            // If the unit is allowed to move
            if (!wait) {
                List<LightUnit> playersInRange = new ArrayList<>();
                LightUnit targetPlayer;

                playersInRange = getPlayersInRange(getTilesWithPlayersInStaticAttackRange());

                if (!playersInRange.isEmpty()) {
                    targetPlayer = findKillablePlayerUnit(playersInRange);
                    if (targetPlayer != null) {
                        gp.battleSim.battleEnemyPhase(targetPlayer, this);
                        endTurn();
                        return;
                    }

                    targetPlayer = findMaxDamagePlayerUnit(playersInRange);
                    if (targetPlayer != null) {
                        gp.battleSim.battleEnemyPhase(targetPlayer, this);
                        endTurn();
                        return;
                    }
                }

                playersInRange = getPlayersInRange(getTilesWithPlayersInAttackRange());

                if (!playersInRange.isEmpty()) {
                    targetPlayer = findKillablePlayerUnit(playersInRange);
                    if (targetPlayer != null) {
                        // Move toward the target player unit before attacking
                        moveTowardsPlayer(targetPlayer);
                        return; // Exit after moving
                    }

                    targetPlayer = findMaxDamagePlayerUnit(playersInRange);
                    if (targetPlayer != null) {
                        // Move toward the target player unit before attacking
                        moveTowardsPlayer(targetPlayer);
                        return; // Exit after moving
                    }
                }

                // If on path and no other action was taken
                if (onPath) {
                    LightUnit nearestPlayer = findNearestPlayerUnit();
                    if (nearestPlayer != null) {
                        searchPath(nearestPlayer);
                    }
                }
            }
        }
    }

    public void moveTowardsPlayer(LightUnit targetPlayer) {
        // Calculate the path to the target player
        gp.pFinder.setNodes(targetPlayer, this);

        if (gp.pFinder.search()) {
            // Get the next tile in the path
            int nextCol = gp.pFinder.pathList.get(0).getCol();
            int nextRow = gp.pFinder.pathList.get(0).getRow();

            if (movementCounter > 0) {
                move(nextCol, nextRow);
            }

            // Check if the enemy is now in attack range after moving
            List<LightUnit> playersInRange = getPlayersInRange(getTilesWithPlayersInStaticAttackRange());
            if (playersInRange.contains(targetPlayer)) {
                gp.battleSim.battleEnemyPhase(targetPlayer, this);
                endTurn();
            }

            // If it uses all available movement stop the search
            if (movementCounter == 0) {
                endTurn();
            }
        } else {
            moveCloserToPlayer(targetPlayer);
        }
    }

    private void moveCloserToPlayer(LightUnit targetPlayer) {
        // Get the current position of the enemy
        int enemyCol = this.getCol();
        int enemyRow = this.getRow();

        // Get the target player's position
        int targetCol = targetPlayer.getCol();
        int targetRow = targetPlayer.getRow();

        // Determine the direction to move (manhattan distance)
        int moveCol = enemyCol;
        int moveRow = enemyRow;

        // Decide whether to move in the x or y direction
        if (Math.abs(targetCol - enemyCol) > Math.abs(targetRow - enemyRow)) {
            // Move towards the target player in the x direction if it's further away
            if (targetCol > enemyCol) {
                moveCol++; // Move right
            } else {
                moveCol--; // Move left
            }
        } else {
            // Move towards the target player in the y direction
            if (targetRow > enemyRow) {
                moveRow++; // Move down
            } else {
                moveRow--; // Move up
            }
        }

        // Check if the new position is valid before moving
        if (gp.cChecker.isWithinMap(moveCol, moveRow) && gp.cChecker.validTile(moveCol, moveRow)) {
            if (movementCounter > 0) {
                move(moveCol, moveRow); // Move to the valid tile
            }
        }

        // Check if the enemy is now in attack range after moving
        List<LightUnit> playersInRange = getPlayersInRange(getTilesWithPlayersInStaticAttackRange());
        if (playersInRange.contains(targetPlayer)) {
            gp.battleSim.battleEnemyPhase(targetPlayer, this);
        }

        // End the turn
        endTurn();
    }

    public void searchPath(LightUnit nearestPlayer) {
        int goalCol = nearestPlayer.getCol();
        int goalRow = nearestPlayer.getRow();

        gp.pFinder.setNodes(nearestPlayer, this);

        if (gp.pFinder.search()) {

            // Next col and row
            int nextCol = gp.pFinder.pathList.get(0).getCol();
            int nextRow = gp.pFinder.pathList.get(0).getRow();

            if (movementCounter > 0) {
                move(nextCol, nextRow);
            }

            // If it reaches the goal or uses all available movement stop the search
            if ((nextCol == goalCol && nextRow == goalRow) || movementCounter == 0) {
                endTurn();
            }
        } else {
            // Manual movement
            int nextCol, nextRow;

            if (gp.cChecker.validTile(col, row + 1)) {
                // Move down
                nextCol = col;
                nextRow = row + 1;
            } else if (gp.cChecker.validTile(col - 1, row)) {
                // Move left
                nextCol = col - 1;
                nextRow = row;
            } else if (gp.cChecker.validTile(col + 1, row)) {
                // Move right
                nextCol = col + 1;
                nextRow = row;
            } else if (gp.cChecker.validTile(col, row - 1)) {
                // Move up
                nextCol = col - 1;
                nextRow = row;
            } else {
                endTurn();
                return;
            }

            move(nextCol, nextRow);

            if (gp.cChecker.validTile(col, row + 1)) {
                // Move down
                nextCol = col;
                nextRow = row + 1;
            } else if (gp.cChecker.validTile(col - 1, row)) {
                // Move left
                nextCol = col - 1;
                nextRow = row;
            } else if (gp.cChecker.validTile(col + 1, row)) {
                // Move right
                nextCol = col + 1;
                nextRow = row;
            } else if (gp.cChecker.validTile(col, row - 1)) {
                // Move up
                nextCol = col - 1;
                nextRow = row;
            } else {
                endTurn();
                return;
            }

            move(nextCol, nextRow);

            if (gp.cChecker.validTile(col, row + 1)) {
                // Move down
                nextCol = col;
                nextRow = row + 1;
            } else if (gp.cChecker.validTile(col - 1, row)) {
                // Move left
                nextCol = col - 1;
                nextRow = row;
            } else if (gp.cChecker.validTile(col + 1, row)) {
                // Move right
                nextCol = col + 1;
                nextRow = row;
            } else if (gp.cChecker.validTile(col, row - 1)) {
                // Move up
                nextCol = col - 1;
                nextRow = row;
            } else {
                endTurn();
                return;
            }

            move(nextCol, nextRow);

            if (movementCounter == 0) {
                endTurn();
            }
        }
    }

    public void move(int nextCol, int nextRow) {
        moveDelayCounter++;  // Increment the delay counter

        // Only move if the counter reaches the threshold
        if (moveDelayCounter >= moveDelayThreshold) {
            moveDelayCounter = 0; // Reset the counter after moving

            if (col > nextCol) {
                direction = "left";
            } else if (col < nextCol) {
                direction = "right";
            } else if (row > nextRow) {
                direction = "up";
            } else if (row < nextRow) {
                direction = "down";
            }

            // Update the ChaosUnit's position gradually
            if (nextCol != col || nextRow != row) {
                int targetX = nextCol * gp.getTileSize(); // Target x position
                int targetY = nextRow * gp.getTileSize(); // Target y position

                // Gradually move towards the target x position
                if (x < targetX) {
                    x += moveSpeed;
                    if (x > targetX){
                        x = targetX;
                    }
                } else if (x > targetX) {
                    x -= moveSpeed;
                    if (x < targetX){
                        x = targetX;
                    }
                }

                // Gradually move towards the target y position
                if (y < targetY) {
                    y += moveSpeed;
                    if (y > targetY){
                        y = targetY;
                    }
                } else if (y > targetY) {
                    y -= moveSpeed;
                    if (y < targetY){
                        y = targetY;
                    }
                }

                // Check if the unit has reached the new tile
                if (x == targetX && y == targetY) {
                    col = nextCol;    // Update the unit's column
                    row = nextRow;    // Update the unit's row
                    movementCounter--;
                    updatePosition();   // Update the col and row based on the new position
                }
            }
        }
    }

    // Method to find the nearest player unit
    public LightUnit findNearestPlayerUnit() {
        LightUnit nearestPlayer = null;
        int nearestDistance = Integer.MAX_VALUE; // Initialize with maximum value

        // Loop through all player units
        for (LightUnit player : gp.LightUnits) {
            int playerCol = player.getCol();
            int playerRow = player.getRow();

            // Calculate Manhattan distance to the player unit
            int distance = Math.abs(playerCol - col) + Math.abs(playerRow - row);

            // Check if this player unit is the nearest one found so far
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPlayer = player;
            }
        }

        return nearestPlayer; // Return the nearest player unit, or null if none found
    }

    public void setStats() {
        if (!boss && level > 1) {
            UtilityTool uTool = new UtilityTool();

            for (int i = 1; i < level; i++) {
                if (uTool.getRandomNumber() <= HPGrowthRate) {
                    maxHP += 1;
                }
                if (uTool.getRandomNumber() <= strengthGrowthRate) {
                    strength += 1;
                }
                if (uTool.getRandomNumber() <= magicGrowthRate) {
                    magic += 1;
                }
                if (uTool.getRandomNumber() <= skillGrowthRate) {
                    skill += 1;
                }
                if (uTool.getRandomNumber() <= speedGrowthRate) {
                    speed += 1;
                }
                if (uTool.getRandomNumber() <= luckGrowthRate) {
                    luck += 1;
                }
                if (uTool.getRandomNumber() <= defenseGrowthRate) {
                    defense += 1;
                }
                if (uTool.getRandomNumber() <= resistanceGrowthRate) {
                    resistance += 1;
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
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;  // Reset sprite counter
        }
    }

    // Method to find all tiles with players in this unit's static attack range
    public List<int[]> getTilesWithPlayersInStaticAttackRange() {
        // List to store the positions of players that are within healing range
        List<int[]> tilesWithPlayersInRange = new ArrayList<>();

        // Determine the attack range (the maximum distance at which the unit can attack)
        int weaponRange = 0;
        if (attackType == AttackType.Physical && equippedWeapon != null) {
            weaponRange = equippedWeapon.getRange();  // Get range from equipped weapon
        }
        if (attackType == AttackType.Magical) {
            weaponRange = attackSpell.getRange(); // Get range from equipped attack spell
        }

        // Iterate over all possible tiles within the maximum attack range
        for (int dCol = -weaponRange; dCol <= weaponRange; dCol++) {
            for (int dRow = -weaponRange; dRow <= weaponRange; dRow++) {
                // Calculate Manhattan distance for the current offset (dCol, dRow)
                int manhattanDistance = Math.abs(dCol) + Math.abs(dRow);

                // Check if this tile is within the valid healing range (Manhattan distance between 1 and healingRange)
                if (manhattanDistance >= 1 && manhattanDistance <= weaponRange) {
                    // Calculate the actual column and row of the healing tile
                    int attackCol = col + dCol;
                    int attackRow = row + dRow;

                    // Ensure the attack tile is within the bounds of the game map
                    if (gp.cChecker.isWithinMap(attackCol, attackRow)) {
                        // Check if there is a player on the current tile
                        if (!gp.cChecker.noPlayerOnTile(attackCol, attackRow)) {
                            // Add the position of the enemy to the list
                            tilesWithPlayersInRange.add(new int[]{attackCol, attackRow});
                        }
                    }
                }
            }
        }

        // Return the list of players positions within range
        return tilesWithPlayersInRange;
    }

    // Method to find all tiles with players in this unit's attack range based on its current position
// Method to find all tiles with players in this unit's attack range based on its current position
    public List<int[]> getTilesWithPlayersInAttackRange() {
        List<int[]> tilesWithPlayersInRange = getTilesWithPlayersInStaticAttackRange();
        List<int[]> playersInRange = new ArrayList<>(); // New list to store the valid tiles

        // Loop through all the given tiles
        for (int[] tile : tilesWithPlayersInRange) {
            int attackCol = tile[0];
            int attackRow = tile[1];

            // Ensure the attack tile is within the bounds of the game map
            if (gp.cChecker.isWithinMap(attackCol, attackRow)) {
                // Check if there is a player on the current tile
                if (!gp.cChecker.noPlayerOnTile(attackCol, attackRow)) {
                    // Add the position of the enemy to the new list
                    playersInRange.add(new int[]{attackCol, attackRow});
                }
            }
        }

        return playersInRange; // Return the new list of found LightUnits
    }


    // Method to find the actual player units in this unit's in attack range
    public List<LightUnit> getPlayersInRange(List<int[]> tilesWithEnemiesInRange) {
        // List to store the player units that are within attack range
        List<LightUnit> playersInRange = new ArrayList<>();

        // Iterate over the list of tiles to find and collect the player units
        for (int[] tile : tilesWithEnemiesInRange) {
            int attackCol = tile[0];
            int attackRow = tile[1];

            // Retrieve the player unit on the current tile
            LightUnit playerunit = gp.cChecker.getPlayerOnTile(attackCol, attackRow);

            // If a player unit is found, add it to the list
            if (playerunit != null) {
                playersInRange.add(playerunit);
            }
        }
        // Return the list of player units within range
        return playersInRange;
    }

    public LightUnit findKillablePlayerUnit( List<LightUnit> playersInRange) {
        LightUnit killablePlayer = null;
        int damage = 0;

        // Loop through all player units
        for (LightUnit player : playersInRange) {

            // Calculate potential damage enemy can deal to this player
            if (attackType == AttackType.Physical && equippedWeapon != null) {
                damage = might - player.getEffDefense();
            }
            if (attackType == AttackType.Magical && attackSpell != null) {
                damage = might - player.getEffResistance();
            }

            // Check if the damage can kill the player
            if (damage >= player.getHP()) {
                killablePlayer = player;
                break; // Return the first player that can be killed
            }
        }

        return killablePlayer; // Return the player unit that can be killed, or null if none
    }

    public LightUnit findMaxDamagePlayerUnit( List<LightUnit> playersInRange) {
        LightUnit targetPlayer = null;
        int maxDamage = 0;
        int damage = 0;

        // Loop through all player units
        for (LightUnit player : playersInRange) {

            // Calculate potential damage enemy can deal to this player
            if (attackType == AttackType.Physical && equippedWeapon != null) {
                damage = might - player.getEffDefense();
            }
            if (attackType == AttackType.Magical && attackSpell != null) {
                damage = might - player.getEffResistance();
            }

            // Find the player this enemy does the most damage
            if (damage > maxDamage) {
                targetPlayer = player;
                break; // Return the first player that can be killed
            }
        }

        return targetPlayer; // Return the player unit that can be killed, or null if none
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

                // Check if the new tile is within the map bounds, not visited, within movement range, not wall and not occupied by a player or enemy unit
                if (gp.cChecker.isWithinMap(newCol, newRow) && !visited[newCol][newRow] && newDistance <= movement && gp.cChecker.validTile(newCol, newRow)) {
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
            weaponRange = attackSpell.getRange(); // Get range from equipped attack spell
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

    // Placeholder method for dropping items when defeated
    public void dropItem() {  }

    // Placeholder method to randomize race
    public void randomizeRace() {  }

    // Placeholder method to randomize items
    public void randomizeItems() {}

    // Getter

    // Return if the unit is a boss
    public boolean isBoss() { return boss;}
}

