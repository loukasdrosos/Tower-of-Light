package main;

import Entity.*;

public class TurnManager {

    boolean playerPhase = true; // Sets player or enemy turn
    int turnCounter = 0; // Initialize the turn counter
    boolean turnCompleted = false; // Flag to check if the turn has switched

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
                // Set all player units' wait = true to prevent them from acting during the Chaos units' turn
                for (LightUnit unit : gp.simLightUnits) {
                    unit.endTurn();
                }
                // Activate Chaos units' turn
                for (ChaosUnit unit : gp.simChaosUnits) {
                    unit.startTurn();
                }
            }

        } else {
            if (!turnCompleted) {
                System.out.println("Enemy Phase");
                turnCompleted = true; // Ensure we only print once per switch
            }

            // Chaos units' turn logic
            boolean allEnemiesWait = true;
            for (ChaosUnit unit : gp.simChaosUnits) {
                if (!unit.getWait()) { // Check if any Chaos unit hasn't finished its turn
                    allEnemiesWait = false;
                    break;
                }
            }

            // If all Chaos units have finished, switch to the player's turn
            if (allEnemiesWait) {
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

    public boolean getPlayerPhase() {
        return playerPhase;
    }
}



