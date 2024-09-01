package main;

import Entity.*;

public class CollisionChecker {

    GamePanel gp;
    private boolean validSquare;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean isWithinMap (int targetCol, int targetRow) {
        if (targetCol >= 1 && targetCol <= 50 && targetRow >= 1 && targetRow <= 50) {
            return true;
        }
        return false;
    }

    public boolean noEnemyOnTile(int targetCol, int targetRow) {
        for (ChaosUnit enemy : gp.simChaosUnits) {
            if (targetCol == enemy.getCol() && targetRow == enemy.getRow()) {
                return false;
            }
        }
        return true;
    }

    public boolean noPlayerOnTile (int targetCol, int targetRow) {
        for (LightUnit player : gp.simLightUnits) {
            if (gp.selectedUnit!= null && player != gp.selectedUnit) {
                if (targetCol == player.getCol() && targetRow == player.getRow()) {
                    return false;
                }
            }
            else if (gp.selectedUnit == null) {
                if (targetCol == player.getCol() && targetRow == player.getRow()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean NonCollisionTile  (int targetCol, int targetRow) {
        int tileNum = gp.tileM.mapTileNum[targetCol][targetRow];
        if (gp.tileM.tile[tileNum].collision == true) {
            return false;
        }
        return true;
    }

    public boolean validTile (int targetCol, int targetRow) {
        if (noEnemyOnTile(targetCol, targetRow) == true && noPlayerOnTile(targetCol, targetRow) == true && NonCollisionTile(targetCol, targetRow) == true) {
            return true;
        }
        return false;
    }
}


