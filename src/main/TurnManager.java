package main;

import Entity.*;

public class TurnManager {

    private boolean playerPhase = true; // Sets player or enemy turn
    private int turnCounter = 0; // Initialize the turn counter
    private boolean turnCompleted = false; // Flag to check if the turn has switched
    private int currentEnemyUnitIndex = 0; // Index to track the current enemy unit
    private boolean ePressed = false; // Flag to track if E was pressed in the last frame
    private boolean playerPhaseSoundPlayed = false;
    private boolean enemyPhaseSoundPlayed = false;

    GamePanel gp;
    KeyHandler keyH;

    public TurnManager(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    // Manages the turns between player and enemy phases
    public void manageTurns() {
        if (playerPhase) {
            // Player's turn phase
            if (!turnCompleted) {
                turnCounter++; // Increment the turn counter
                turnCompleted = true; // Mark the turn as completed to avoid multiple increments
                gp.playSE(1);  // Play sound effect for player phase start
            }

            // Play the sound effect for the player phase start only once
            if (!playerPhaseSoundPlayed) {
                gp.playSE(1);  // Play sound effect for player phase start
                playerPhaseSoundPlayed = true; // Mark that the sound has been played
                enemyPhaseSoundPlayed = false; // Reset enemy phase sound flag for the next switch
            }

            // Check if all player units have finished their turn
            boolean allPlayersWait = true;
            for (LightUnit unit : gp.simLightUnits) {
                if (!unit.getWait()) { // Check if any player unit hasn't finished its turn
                    allPlayersWait = false;
                    break;
                }
            }

            // If all player units have finished, switch to the enemy phase
            if (allPlayersWait) {
                playerPhase = false; // Switch to enemy phase
                turnCompleted = false; // Reset turn completion flag
                currentEnemyUnitIndex = 0; // Reset the enemy unit index

                // Set all player units' wait = true to prevent them from acting during the enemy phase
                for (LightUnit unit : gp.simLightUnits) {
                    unit.endTurn();
                }

                // Start the turn for the first enemy unit, if available
                if (!gp.simChaosUnits.isEmpty()) {
                    gp.simChaosUnits.get(currentEnemyUnitIndex).startTurn();
                }
            }
        }
        else {
            // Enemy's turn phase
            if (!turnCompleted) {
                turnCompleted = true; // Ensure we only print once per switch
            }

            // Play the sound effect for the enemy phase start only once
            if (!enemyPhaseSoundPlayed) {
                gp.playSE(2);  // Play sound effect for enemy phase start
                enemyPhaseSoundPlayed = true; // Mark that the sound has been played
                playerPhaseSoundPlayed = false; // Reset player phase sound flag for the next switch
            }

            // Chaos units' turn logic
            if (currentEnemyUnitIndex < gp.simChaosUnits.size()) {
                ChaosUnit currentEnemy = gp.simChaosUnits.get(currentEnemyUnitIndex);
                if (currentEnemy.getWait()) { // If the current unit has finished its turn
                    currentEnemy.endTurn(); // Ensure it's properly ended
                    currentEnemyUnitIndex++; // Move to the next unit

                    // Start the next enemy unit's turn, if available
                    if (currentEnemyUnitIndex < gp.simChaosUnits.size()) {
                        gp.simChaosUnits.get(currentEnemyUnitIndex).startTurn();
                    }
                }
                else {
                    currentEnemy.takeAction(); // Only take action if the unit hasn't finished its turn
                }
            } else {
                // All Chaos units have finished their turns, switch back to player phase
                playerPhase = true;
                turnCompleted = false;

                // Set all Chaos units' wait = true to prevent them from acting during the player phase
                for (ChaosUnit unit : gp.simChaosUnits) {
                    unit.endTurn();
                }

                // Activate turns for all player units
                for (LightUnit unit : gp.simLightUnits) {
                    unit.startTurn();
                }
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
                    if (!player.getWait() && player.getHP() < player.getMaxHP()) {
                        player.healEndTurn();
                    }
                    player.endTurn();
                }
            }
            else {
                gp.ui.addLogMessage("Can only end Player Phase when no unit is selected.");
            }
        }

        // Reset the flag when the 'E' key is released
        if (!keyH.isEPressed()) {
            ePressed = false;
        }
    }

    public void update() {
        manageTurns();
        endPlayerTurn();
    }

    // GETTERS

    // Returns whether it's currently the player's phase
    public boolean getPlayerPhase() {
        return playerPhase;
    }

    // Returns the current turn count
    public int getTurnCounter() {
        return turnCounter;
    }
}



