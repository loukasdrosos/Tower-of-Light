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

    public void manageTurns() {
        if (playerPhase) {
            if (!turnCompleted) {
                turnCounter++; // Increment the turn counter only once per full cycle
                System.out.println("Turn: " + turnCounter);
                System.out.println("Player Phase ");
                turnCompleted = true; // Ensure we only increment once per switch
            }

            // Player's turn logic
            boolean allPlayersWait = true;
            for (LightUnit unit : gp.simLightUnits) {
                if (!unit.getWait()) { // Check if any player unit hasn't finished its turn
                    allPlayersWait = false;
                    break;
                }
            }

            // If all player units have finished, switch to the Chaos units' turn
            if (allPlayersWait) {
                playerPhase = false;
                turnCompleted = false;
                currentEnemyUnitIndex = 0; // Reset the enemy unit index

                // Set all player units' wait = true to prevent them from acting during the Chaos units' turn
                for (LightUnit unit : gp.simLightUnits) {
                    unit.endTurn();
                }

                // Activate the first Chaos unit's turn
                if (!gp.simChaosUnits.isEmpty()) {
                    gp.simChaosUnits.get(currentEnemyUnitIndex).startTurn();
                }
            }

        }
        else {
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

                    if (currentEnemyUnitIndex < gp.simChaosUnits.size()) {
                        // Start the next unit's turn
                        gp.simChaosUnits.get(currentEnemyUnitIndex).startTurn();
                    }
                }
                // If the current unit is still waiting, do nothing and let it continue moving
            } else {
                // All Chaos units have finished their turns, switch back to player phase
                playerPhase = true;
                turnCompleted = false;

                // Set all Chaos units' wait = true to prevent them from acting during the player's turn
                for (ChaosUnit unit : gp.simChaosUnits) {
                    unit.endTurn();
                }

                // Activate player's turn
                for (LightUnit unit : gp.simLightUnits) {
                    unit.startTurn();
                }
            }
        }
    }

    // GETTERS

    public boolean getPlayerPhase() {
        return playerPhase;
    }

    public int getTurnCounter() {
        return turnCounter;
    }
}



