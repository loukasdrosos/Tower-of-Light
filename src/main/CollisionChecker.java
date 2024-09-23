package main;

import Entity.*;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // Method to check if the target position is within the boundaries of the map
    public boolean isWithinMap (int targetCol, int targetRow) {
        // The map is assumed to have a size of 50x50 tiles, with valid columns and rows between 1 and 50
        if (targetCol >= 1 && targetCol <= 50 && targetRow >= 1 && targetRow <= 50) {
            return true;
        }
        return false;
    }

    // Method to check if there is no enemy unit on the target tile
    public boolean noEnemyOnTile(int targetCol, int targetRow) {
        for (ChaosUnit enemy : gp.ChaosUnits) {
            if (targetCol == enemy.getCol() && targetRow == enemy.getRow()) {
                return false;
            }
        }
        return true;
    }

    // Method to check if there is an enemy unit on the specified tile
    public ChaosUnit getEnemyOnTile(int col, int row) {
        // Iterate over all enemy units in the game
        for (ChaosUnit enemy : gp.ChaosUnits) {
            // Check if the enemy's position matches the specified tile
            if (enemy.getCol() == col && enemy.getRow() == row) {
                return enemy; // Return the enemy unit found on the tile
            }
        }
        // Return null if no enemy is found on the tile
        return null;
    }

    // Method to check if there is a player unit on the specified tile
    public LightUnit getPlayerOnTile(int col, int row) {
        // Iterate over all player units in the game
        for (LightUnit player : gp.LightUnits) {
            // Check if the player's position matches the specified tile
            if (player.getCol() == col && player.getRow() == row) {
                return player; // Return the player unit found on the tile
            }
        }
        // Return null if no player is found on the tile
        return null;
    }

    // Method to check if there is no player unit on the target tile
    public boolean noPlayerOnTile (int targetCol, int targetRow) {
        for (LightUnit player : gp.LightUnits) {
            // If the currently selected unit is not null and is not the player being checked, used for player unit during player phase
            if (gp.selectedUnit!= null && player != gp.selectedUnit) {
                if (targetCol == player.getCol() && targetRow == player.getRow()) {
                    return false;
                }
            }
            // If no unit is currently selected, used for enemy units movement during enemy phase
            else if (gp.selectedUnit == null) {
                if (targetCol == player.getCol() && targetRow == player.getRow()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Method to check if the target tile does not have a collision property
    public boolean NonCollisionTile  (int targetCol, int targetRow) {
        int tileNum = gp.tileM.mapTileNum[gp.getCurrentMap()][targetCol][targetRow];
        if (gp.tileM.tile[tileNum].collision) {
            return false;
        }
        return true;
    }

    // Method to check if the target tile is valid for movement
    public boolean validTile (int targetCol, int targetRow) {
        if (noEnemyOnTile(targetCol, targetRow) && noPlayerOnTile(targetCol, targetRow) && NonCollisionTile(targetCol, targetRow)) {
            return true;
        }
        return false;
    }

    // Method to check if the tile is valid for placing Beacon of Light
    public boolean isValidBeaconOfLightTile(int targetCol, int targetRow) {
        for (int col = 0; col < gp.getMaxMapCol(); col++) {
            for (int row = 0; row < gp.getMaxMapRow(); row++) {
                if (gp.tileM.BeaconOfLightTiles[col][row]) {
                    // Calculate Manhattan distance between target tile and each Beacon of Light
                    int distance = Math.abs(targetCol - col) + Math.abs(targetRow - row);
                    if (distance < 1) {
                        return false;  // Too close to an existing beacon
                    }
                }
            }
        }
        return true;  // Valid tile, no beacons within 10-tile distance
    }
}


