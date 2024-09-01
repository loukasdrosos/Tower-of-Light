package main;

import Entity.LightUnit;

public class UnitSelector {

    GamePanel gp;
    KeyHandler keyH;

    public UnitSelector(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    public void UnitSelection() {
        if (keyH.isAPressed() == true) {
            // Check if the cursor's position matches the position of any player unit (LightUnit)
            if (gp.selectedUnit == null) {
                for (LightUnit unit : gp.simLightUnits) {
                    if (gp.cursor.getCol() == unit.getCol() && gp.cursor.getRow() == unit.getRow() && unit.getWait() == false) {
                        gp.selectedUnit = unit; // Select player unit
                        gp.selectedUnit.setIsSelected(true); //Activate the selected player unit
                        gp.selectedUnit.setIsMoving(true); // Allow player to move
                        break; // Exit loop once a match is found
                    }
                }
            }
        }

        if (keyH.isZPressed() == true) {
            if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected() == true && gp.selectedUnit.getIsMoving() == true) {
                gp.selectedUnit.resetPosition(); // Return player to starting position
                gp.selectedUnit = null; // Deselect the player
            }
        }

        if (keyH.isWPressed() == true) {
            if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected() == true && gp.selectedUnit.getIsMoving() == true) {
                gp.selectedUnit.endTurn(); // End player's turn
                gp.selectedUnit = null; // Deselect the player
            }
        }
    }

    public void update() {
        UnitSelection();
    }
}
