package main;

import Entity.LightUnit;

public class UnitSelector {

    GamePanel gp;
    KeyHandler keyH;
    private boolean ePressed = false; // Flag to track if E was pressed in the last frame

    public UnitSelector(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    public void SelectPlayerUnit() {
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
    }

    public void DeSelectPlayerUnit() {
        if (keyH.isZPressed() == true) {
            if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected() == true && gp.selectedUnit.getIsMoving() == true) {
                gp.selectedUnit.resetPosition(); // Return player to starting position
                gp.selectedUnit = null; // Deselect the player
            }
        }
    }

    public void endSelectedUnitTurn() {
        if (keyH.isWPressed() == true) {
            if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected() == true && gp.selectedUnit.getIsMoving() == true) {
                gp.selectedUnit.endTurn(); // End player's turn
                gp.selectedUnit = null; // Deselect the player
            }
        }
    }

    public void endPlayerTurn() {
        if (keyH.isEPressed() == true && ePressed == false) {
            // Check if E is pressed and it wasn't pressed in the last frame
            ePressed = true; // Set the flag so we don't register another press immediately

            if (gp.selectedUnit == null) {
                // End the turn for all player units
                for (LightUnit player : gp.simLightUnits) {
                    player.endTurn();
                }
            }
        }

        if (keyH.isEPressed() == false) {
            ePressed = false; // Reset the flag when E is not pressed
        }
    }

    public void update() {
        if (gp.TurnM.getPlayerPhase() == true) {
            SelectPlayerUnit();
            DeSelectPlayerUnit();
            endSelectedUnitTurn();
            endPlayerTurn();
        }
    }
}
