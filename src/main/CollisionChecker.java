package main;

import Entity.Entity;

public class CollisionChecker {

    GamePanel gp;
    private boolean canMove;
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

    public void simulate() {
        canMove = false;
        validSquare = false;

        if (gp.selectedUnit.allowedMove(gp.selectedUnit.getCol(), gp.selectedUnit.getRow()) == true) {
            canMove = true;
            validSquare = true;
        }
    }

    public boolean isValidSquare() {
        return validSquare;
    }

    public boolean isCanMove() {
        return canMove;
    }
}


