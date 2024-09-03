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

    // Method to select a player unit (LightUnit) based on the cursor's position
    public void SelectPlayerUnit() {
        if (keyH.isAPressed()) {
            // Check if the cursor's position matches the position of any player unit (LightUnit)
            if (gp.selectedUnit == null) {
                for (LightUnit unit : gp.simLightUnits) {
                    if (gp.cursor.getCol() == unit.getCol() && gp.cursor.getRow() == unit.getRow() && !unit.getWait()) {
                        gp.selectedUnit = unit; // Select player unit
                        gp.selectedUnit.setIsSelected(true); //Activate the selected player unit
                        gp.selectedUnit.setIsMoving(true); // Allow player to move
                        break; // Exit loop once a match is found
                    }
                }
            }
        }
    }

    // Method to deselect the currently selected player unit and reset its position
    public void DeSelectPlayerUnit() {
        if (keyH.isZPressed()) {
            // Only proceed if a unit is selected, is marked as selected, and is currently moving
            if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected() && gp.selectedUnit.getIsMoving()) {
                gp.selectedUnit.resetPosition(); // Return player to starting position
                gp.selectedUnit = null; // Deselect the player
            }
        }
    }

    // Method to end the turn of the currently selected player unit
    public void endSelectedUnitTurn() {
        if (keyH.isWPressed()) {
            // Only proceed if a unit is selected, is marked as selected, and is currently moving
            if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected() && gp.selectedUnit.getIsMoving() && gp.cChecker.validTile(gp.selectedUnit.getCol(), gp.selectedUnit.getRow())) {
                gp.selectedUnit.endTurn(); // End player's turn
                gp.selectedUnit = null; // Deselect the player
            }
        }
    }

    // Method to end the turn for all player units when the 'E' key is pressed
    public void endPlayerTurn() {
        if (keyH.isEPressed() && !ePressed) {
            // Check if E is pressed and it wasn't pressed in the last frame
            ePressed = true; // Set the flag so we don't register another press immediately

            if (gp.selectedUnit == null) {
                // End the turn for all player units
                for (LightUnit player : gp.simLightUnits) {
                    player.endTurn();
                }
            }
        }

        // Reset the flag when the 'E' key is released
        if (!keyH.isEPressed()) {
            ePressed = false;
        }
    }

    // Method to update the unit selection logic during the player's phase
    public void update() {
        // Only process inputs during the player's phase
        if (gp.TurnM.getPlayerPhase()) {
            SelectPlayerUnit(); // Check for unit selection
            DeSelectPlayerUnit(); // Check for unit deselection
            endSelectedUnitTurn(); // Check to end the selected unit's turn
            endPlayerTurn(); // Check to end the player's overall turn
        }
    }
}
