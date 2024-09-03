package main;

import Entity.*;

public class TurnManager {

    private boolean playerPhase = true; // Sets player or enemy turn
    private int turnCounter = 0; // Initialize the turn counter
    private boolean turnCompleted = false; // Flag to check if the turn has switched
    private int currentEnemyUnitIndex = 0; // Index to track the current enemy unit

    GamePanel gp;

    public TurnManager(GamePanel gp) {
        this.gp = gp;
    }

    // Manages the turns between player and enemy phases
    public void manageTurns() {
        if (playerPhase) {
            // Player's turn phase
            if (!turnCompleted) {
                turnCounter++; // Increment the turn counter
                System.out.println("Turn: " + turnCounter);
                System.out.println("Player Phase ");
                turnCompleted = true; // Mark the turn as completed to avoid multiple increments
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
                System.out.println("Enemy Phase");
                turnCompleted = true; // Ensure we only print once per switch
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
                    currentEnemy.move(); // Only move if the unit hasn't finished its turn
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



