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

    private int activeBeacons = 0; // Count of currently active beacons
    private int beaconCooldown = 1; // Cooldown period for each beacon
    private int beaconCooldownTimer = beaconCooldown; // Cooldown timer for beacons of light

    private boolean enemiesSpawned;
    private boolean boss1Spawned, boss2Spawned, boss3Spawned, boss4Spawned, boss5Spawned, boss6Spawned;

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
            if (!turnCompleted && !gp.battleSim.isBattleInProgress()) {
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
            for (LightUnit unit : gp.LightUnits) {
                if (!unit.getWait()) { // Check if any player unit hasn't finished its turn
                    allPlayersWait = false;
                    break;
                }
            }

            // If all player units have finished, switch to the enemy phase
            if (allPlayersWait && !gp.battleSim.isBattleInProgress()) {
                playerPhase = false; // Switch to enemy phase
                turnCompleted = false; // Reset turn completion flag
                currentEnemyUnitIndex = 0; // Reset the enemy unit index

                // Set all player units' wait = true to prevent them from acting during the enemy phase
                for (LightUnit unit : gp.LightUnits) {
                    unit.endTurn();
                }

                if (!enemiesSpawned) {
                    gp.aSetter.spawnEnemies();
                    if (activeBeacons == 3) {
                        if (!boss1Spawned) {
                            gp.aSetter.spawnBosses();
                            boss1Spawned = true;
                        }
                         else if (!boss2Spawned) {
                            gp.aSetter.spawnBosses();
                            boss2Spawned = true;
                        }
                        else if (!boss3Spawned) {
                            gp.aSetter.spawnBosses();
                            boss3Spawned = true;
                        }
                        else if (!boss4Spawned) {
                            gp.aSetter.spawnBosses();
                            boss4Spawned = true;
                        }
                        else if (!boss5Spawned) {
                            gp.aSetter.spawnBosses();
                            boss5Spawned = true;
                        }
                        else if (!boss6Spawned) {
                            gp.aSetter.spawnBosses();
                            boss6Spawned = true;
                        }
                    }
                    enemiesSpawned = true;
                }

                // Start the turn for the first enemy unit, if available
                if (!gp.ChaosUnits.isEmpty()) {
                    gp.ChaosUnits.get(currentEnemyUnitIndex).startTurn();
                }
            }
        }
        else {
            // Enemy's turn phase
            if (!turnCompleted && !gp.battleSim.isBattleInProgress()) {
                turnCompleted = true; // Ensure we only print once per switch
            }

            // Play the sound effect for the enemy phase start only once
            if (!enemyPhaseSoundPlayed) {
                gp.playSE(2);  // Play sound effect for enemy phase start
                enemyPhaseSoundPlayed = true; // Mark that the sound has been played
                playerPhaseSoundPlayed = false; // Reset player phase sound flag for the next switch
            }

            // Chaos units' turn logic
            if (currentEnemyUnitIndex < gp.ChaosUnits.size()) {
                ChaosUnit currentEnemy = gp.ChaosUnits.get(currentEnemyUnitIndex);
                if (currentEnemy.getWait()) { // If the current unit has finished its turn
                    currentEnemy.endTurn(); // Ensure it's properly ended
                    currentEnemyUnitIndex++; // Move to the next unit

                    // Start the next enemy unit's turn, if available
                    if (currentEnemyUnitIndex < gp.ChaosUnits.size()) {
                        gp.ChaosUnits.get(currentEnemyUnitIndex).startTurn();
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
                for (ChaosUnit unit : gp.ChaosUnits) {
                    unit.endTurn();
                }

                // Activate turns for all player units
                for (LightUnit unit : gp.LightUnits) {
                    unit.startTurn();
                }

                enemiesSpawned = false;
                reduceBeaconCooldown();
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
                for (LightUnit player : gp.LightUnits) {
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

    public void resetBeaconCooldownTimer() {
        beaconCooldownTimer = beaconCooldown;
    }

    public void addBeacon() {
        activeBeacons++;
    }

    // Decrease the cooldown timer for Beacons of Light
    public void reduceBeaconCooldown() {
        if (beaconCooldownTimer > 0) {
            beaconCooldownTimer--;
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

    // Returns the number of active Beacons of light
    public int getActiveBeacons() { return activeBeacons;}

    // Returns the cooldown counter for Beacons of light
    public int getBeaconCooldownTimer() { return beaconCooldownTimer; }

}





