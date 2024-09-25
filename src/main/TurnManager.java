package main;

import Entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private boolean reinforcmentsAdded = false;

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
                if (!reinforcmentsAdded && gp.getCurrentMap() == 1){
                    reinforcmentsAdded = true;
                    gp.aSetter.addIagoIke();
                }
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

    public void spawnBoss() {
        if (activeBeacons == 3) {
            List<Runnable> tasks = new ArrayList<>();
            int delay = 300;  //300 ms delay between each message and sound effect
            if (!boss1Spawned && gp.getCurrentMap() == 0) {
                gp.aSetter.spawnBosses();
                boss1Spawned = true;

                tasks.add(() -> {
                    gp.ui.addLogMessage("");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: That Titan seems like its leading the others");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: We better take care of it if we want to reach the next floor");
                });

            }
            else if (!boss2Spawned && gp.getCurrentMap() == 1) {
                gp.aSetter.spawnBosses();
                boss2Spawned = true;

                tasks.add(() -> {
                    gp.ui.addLogMessage("");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Nuibaba: Your fate ends here prince Alm");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: Who are you?");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Nuibaba: I am Nuibaba, one of the Chaos army's commanders");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: You will fall here then");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Nuibaba: Try and withstand my magic!");
                });

            }
            else if (!boss3Spawned && gp.getCurrentMap() == 2) {
                gp.aSetter.spawnBosses();
                boss3Spawned = true;

                tasks.add(() -> {
                    gp.ui.addLogMessage("");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: No way! Is that a Fire Dragon!");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: I thought their existence was only a legend");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: We better be careful");
                });

            }
            else if (!boss4Spawned && gp.getCurrentMap() == 3) {
                gp.aSetter.spawnBosses();
                boss4Spawned = true;

                tasks.add(() -> {
                    gp.ui.addLogMessage("");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: That Chaos Paladin is their leader");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: It's holding a Dracoshield so watch out");
                });

            }
            else if (!boss5Spawned && gp.getCurrentMap() == 4) {
                gp.aSetter.spawnBosses();
                boss5Spawned = true;

                tasks.add(() -> {
                    gp.ui.addLogMessage("");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: You managed to make it here young prince");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: Jedah! What have you done to my family?");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: Your parents weren't strong enough i'm afraid");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: They couldn't defeat the Herald of Chaos");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: The princess however is alive");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: Mother, father...NOOOOOOO");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: You will pay for what you've done!");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: Where is Celica, TELL ME");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: HAHAHAHAHA! Don't worry, she is not harmed!");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: The princess awaits you on the next floor");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: That is if you manage to defeat the Herald of Chaos there");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: Herald of Chaos?");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: Grima's strongest warrior that holds the Edge of Chaos");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: A weapon as powerful as that Lightbringer of yours");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: We will not lose!");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: The Chaos army and Grima WILL be defeated!");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Jedah: Foolish prince your path ends here!");
                });

            }
            else if (!boss6Spawned && gp.getCurrentMap() == 5) {
                gp.aSetter.spawnBosses();
                boss6Spawned = true;

                tasks.add(() -> {
                    gp.ui.addLogMessage("");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Celica: ...");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: Celica, is that you? CELICA!!");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Celica: Your struggles end here");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: Huh?");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Celica: I, the Herald of Chaos, will bring glory to Grima");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: Celica what's going on?");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: Why are you attacking me Celica?");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: It must be the Edge of Chaos! It possessed her!");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: We have to defeat her...");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: I'll get you back Celica, just wait");
                });

            }

            // Execute the tasks one by one with a delay
            executeWithDelay(tasks, delay);
        }
    }

    protected void executeWithDelay(List<Runnable> tasks, int delay) {
        Timer timer = new Timer();

        for (int i = 0; i < tasks.size(); i++) {
            int taskIndex = i;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    tasks.get(taskIndex).run();
                }
            }, delay * i);
        }

        // Cancel the timer after all tasks have been executed
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
            }
        }, delay * tasks.size());
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

    public void resetBeaconsofLight() {
        if (gp.getCurrentMap() != 6) {
            activeBeacons = 0;
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





