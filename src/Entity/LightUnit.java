package Entity;

import Item.*;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import java.util.*;
import java.util.List;

public class LightUnit extends Entity {
    KeyHandler keyH;

    protected int moveDelayThreshold = 7; // Number of frames to wait before moving

    protected boolean isMoving = false;  // Track if the unit is moving
    protected boolean isAttacking = false; // Track whether the unit is Attacking
    protected boolean isHealing = false; // // Track whether the unit is Healing an ally
    private boolean zKeyReleased = true; // To track if Z key has been released
    private boolean xKeyReleased = true; // To track if X key has been released
    private boolean wKeyReleased = true; // To track if W key has been released
    private boolean aKeyReleased = true; // To track if A key has been released
    private boolean dKeyReleased = true; // To track if D key has been released
    private boolean sKeyReleased = true; // To track if S key has been released
    private boolean cKeyReleased = true; // To track if C key has been released
    private boolean bKeyReleased = true; // To track if B key has been released
    private boolean spaceKeyReleased = true; // To track if SPACE key has been released


    public MainHand mainHand = null; // Unit's main hand weapon
    public OffHand offHand = null; // Unit's offhand weapon

    public LightUnit(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;       // Reference to the key handler
    }

    // PLAYER UNIT'S ACTIONS

    // Method to select a player unit (LightUnit) based on the cursor's position
    public void SelectPlayerUnit() {
        if (keyH.isAPressed() && aKeyReleased) {
            aKeyReleased = false; // Mark that A key was pressed
            // Check if a player is currently selected
            if (gp.selectedUnit == null) {
                // Check if the cursor's position matches the position of this player unit (LightUnit)
                if (gp.cursor.getCol() == col && gp.cursor.getRow() == row) {
                    if (!wait) {
                        gp.selectedUnit = this; // Select player unit
                        isMoving = true; // Allow player to move
                        gp.playSE(5);
                    } else {
                        gp.ui.addLogMessage("Unit has ended its turn");
                    }
                }
            }
        }
        // Reset the A key release state when the key is no longer pressed
        if (!keyH.isAPressed()) {
            aKeyReleased = true;
        }
    }

    // Method to deselect the currently selected player unit and reset its position
    public void cancelAction() {
        if (keyH.isZPressed() && zKeyReleased) {
            zKeyReleased = false; // Mark that Z key was pressed

            // Cancel battle forecast for player
            if (gp.selectedUnit != null && gp.selectedUnit == this && isAttacking && !isMoving) {
                isMoving = true; // Player can move again
                isAttacking = false; // Reset the attacking flag
                gp.playSE(6);
            }
            // Cancel healing action for player
            else if (gp.selectedUnit != null && gp.selectedUnit == this && isHealing && !isMoving) {
                isMoving = true; // Player can move again
                isHealing = false; // Reset the healing flag
                gp.playSE(6);
            }

            // Cancel player movement and unselect player if player hasn't picked up an item
            else if (gp.selectedUnit != null && gp.selectedUnit == this && isMoving) {
                if (movement == movementInitial) {
                    resetPosition(); // Return player to starting position
                    gp.selectedUnit = null; // Deselect the player
                    gp.playSE(6);
                } else {
                    gp.ui.addLogMessage("Unit can only take action or end its turn");
                }
            }
        }

        // Reset the Z key release state when the key is no longer pressed
        if (!keyH.isZPressed()) {
            zKeyReleased = true;
        }
    }

    // Method to end the turn of the currently selected player unit
    public void endSelectedUnitTurn() {
        if (keyH.isWPressed() && wKeyReleased) {
            wKeyReleased = false; // Mark that W key was pressed
            // Only proceed if a unit is selected, is marked as selected, and is currently moving
            if (gp.selectedUnit != null && gp.selectedUnit == this && !wait && isMoving) {
                if (gp.cChecker.noPlayerOnTile(col, row)) {
                    // If HP < max HP and units doesn't take any action , it heals some HP
                    if (HP < maxHP) {
                        healEndTurn();
                    }
                    endTurn(); // End player's turn
                    gp.selectedUnit = null; // Deselect the player
                } else {
                    gp.ui.addLogMessage("Unit can't end its turn while on another unit's tile");
                }
            }
        }
        // Reset the wKeyReleased state when the key is no longer pressed
        if (!keyH.isWPressed()) {
            wKeyReleased = true;
        }
    }

    // Method to choose which enemy to attack with physical attacks
    public void chooseTarget() {
        if (gp.selectedUnit != null && gp.selectedUnit == this) {
            List<ChaosUnit> enemiesInRange = getEnemiesInRange();  // To store the tiles with enemies

            if (keyH.isXPressed() && xKeyReleased) {
                xKeyReleased = false; // Mark that X key was pressed
                if (gp.cChecker.noPlayerOnTile(col, row)) {
                    if (!enemiesInRange.isEmpty()) {
                        isMoving = false;
                        isAttacking = true;
                    } else if (enemiesInRange.isEmpty()) {
                        gp.ui.addLogMessage("No enemy in unit's range");
                    }
                } else if (!gp.cChecker.noPlayerOnTile(col, row)) {
                    gp.ui.addLogMessage("Unit can't attack while on another unit's tile");
                }
            }

            // Process SPACE key press for attacking if already in attack mode
            if (isAttacking && keyH.isSpacePressed()) {
                gp.battleSim.battlePlayerPhase(this, gp.cChecker.getEnemyOnTile(gp.cursor.getCol(), gp.cursor.getRow()));
                endTurn();
                if (gp.selectedUnit != null && gp.selectedUnit == this) {
                    gp.selectedUnit = null;
                }
            }

            // Reset the X key release state when the key is no longer pressed
            if (!keyH.isXPressed()) {
                xKeyReleased = true;
            }
        }
    }

    // Physical units switch their equipped weapon
    public void switchWeapons() {
        if (gp.selectedUnit != null && gp.selectedUnit == this && isMoving && !wait) {
            if (attackType == AttackType.Physical) {

                if (keyH.isCPressed() && cKeyReleased) {
                    cKeyReleased = false; // Mark that C key was pressed

                    if ((mainHand != null && offHand == null) || (mainHand == null && offHand != null)) {
                        gp.ui.addLogMessage(name + " has no other weapon");
                    }
                    if (mainHand != null && offHand != null) {
                        if (equippedWeapon == mainHand) {
                            equippedWeapon = offHand;
                            calculateCombatStats();
                        } else if (equippedWeapon == offHand) {
                            equippedWeapon = mainHand;
                            calculateCombatStats();
                        }
                        gp.playSE(20);
                        gp.ui.addLogMessage(name + " equipped " + equippedWeapon.getName());
                    }
                }
                // Reset the C Key Released state when the key is no longer pressed
                if (!keyH.isCPressed()) {
                    cKeyReleased = true;
                }
            }
        }
    }


    // Method to choose which ally player to heal with healing spell
    public void healAlly() {
        if (gp.selectedUnit != null && gp.selectedUnit == this) {
            List<LightUnit> playersInRange = getPlayersInRange();  // To store the tiles with players

            if (keyH.isSPressed() && sKeyReleased) {
                sKeyReleased = false; // Mark that S key was pressed
                if (healingSpell != null) {
                    if (gp.cChecker.noPlayerOnTile(col, row)) {
                        if (!playersInRange.isEmpty()) {
                            isMoving = false;
                            isHealing = true;
                        } else if (playersInRange.isEmpty()) {
                            gp.ui.addLogMessage("No ally that needs healing in range");
                        }
                    } else if (!gp.cChecker.noPlayerOnTile(col, row)) {
                        gp.ui.addLogMessage(name + " can't heal while on another unit's tile");
                    }
                } else {
                    gp.ui.addLogMessage(name + " can't use healing spells");
                }
            }

            // Process SPACE key press for attacking if already in healing mode
            if (isHealing && keyH.isSpacePressed()) {
                LightUnit allyPlayer = gp.cChecker.getPlayerOnTile(gp.cursor.getCol(), gp.cursor.getRow());
                if (allyPlayer != null && allyPlayer != this) {
                    allyPlayer.healHP(healingSpell.getHeal());
                    gp.ui.addLogMessage(name + " healed " + allyPlayer.name);
                }
                gp.playSE(13);
                gainExperience(healingSpell.getExpEarned());
                endTurn();
                if (gp.selectedUnit != null && gp.selectedUnit == this) {
                    gp.selectedUnit = null;
                }
            }

            // Reset the X key release state when the key is no longer pressed
            if (!keyH.isSPressed()) {
                sKeyReleased = true;
            }
        }
    }

    // Use Potion
    public void usePotion() {
        if (gp.selectedUnit != null && gp.selectedUnit == this && isMoving && !wait) {

            if (keyH.isDPressed() && dKeyReleased) {
                dKeyReleased = false; // Mark that D key was pressed
                if (potion != null) {
                    if (gp.cChecker.noPlayerOnTile(col, row)) {
                        if (HP < maxHP && potion.getUses() > 0) {
                            healHP(potion.getHeal());
                            gp.playSE(12);
                            potion.usePotion();  // Reduce potion uses
                            gp.ui.addLogMessage(name + " used " + potion.getName());
                            if (potion.getUses() <= 0) {
                                potion = null;
                            }
                            endTurn();
                            gp.selectedUnit = null;
                        } else {
                            gp.ui.addLogMessage(name + " is at full health");
                        }
                    } else if (!gp.cChecker.noPlayerOnTile(col, row)) {
                        gp.ui.addLogMessage(name + " can't use potion while on another unit's tile");
                    }
                } else {
                    gp.ui.addLogMessage(name + " doesn't have a potion in inventory");
                }
            }
            // Reset the dKeyReleased state when the key is no longer pressed
            if (!keyH.isDPressed()) {
                dKeyReleased = true;
            }
        }
    }

    public void healHP(int heal) {
        HP += heal;
        if (HP > maxHP) {
            HP = maxHP;
        }
    }

    // Use Beacon Of Light
    public void useBeaconOfLight() {
        if (gp.selectedUnit != null && gp.selectedUnit == this && isMoving && !wait) {

            if (keyH.isBPressed() && bKeyReleased) {
                bKeyReleased = false; // Mark that B key was pressed
                if (BeaconOfLight) {
                    if (gp.cChecker.noPlayerOnTile(col, row)) {
                        // Check if the tile is a valid tile for Beacon of Light placement
                        if (gp.cChecker.isValidBeaconOfLightTile(col, row)) {
                            if (gp.TurnM.getActiveBeacons() < 3) {
                                if (gp.TurnM.getBeaconCooldownTimer() == 0) {
                                    gp.playSE(19);
                                    gp.tileM.addBeaconOfLightTile(this);
                                    gp.TurnM.addBeacon();
                                    gp.TurnM.spawnBoss();
                                    gp.TurnM.resetBeaconCooldownTimer();
                                    gp.aSetter.increaseEnemyLevel();
                                    gp.aSetter.setMusic();
                                    gp.ui.addLogMessage(name + " used Beacon of Light");
                                    gp.ui.addLogMessage("Alm: I can feel Lightbringer's power increasing");
                                    endTurn();
                                    gp.selectedUnit = null;
                                } else {
                                    gp.ui.addLogMessage("Beacon of Light cooldown is not yet zero");
                                }
                            } else {
                                gp.ui.addLogMessage("Maximum Beacons of Light activated");
                            }
                        } else {
                            gp.ui.addLogMessage("Beacons can't be used near each other");
                        }
                    } else {
                        gp.ui.addLogMessage(name + " can't use Beacon of Light while on another unit's tile");
                    }
                } else {
                    gp.ui.addLogMessage(name + " can't use Beacon of Light");
                }
            }
            // Reset the dKeyReleased state when the key is no longer pressed
            if (!keyH.isBPressed()) {
                bKeyReleased = true;
            }
        }
    }

    public void pickUpItem() {
        if (gp.selectedUnit != null && gp.selectedUnit == this && !wait && isMoving && !isAttacking && !isHealing) {

            Item droppedItem = gp.ui.getSelectedItem();
            int droppedItemIndex = gp.ui.getItemOnSlot();

            if (keyH.isSpacePressed() && spaceKeyReleased) {
                spaceKeyReleased = false;
                if (gp.cChecker.noPlayerOnTile(col, row)) {
                    if (droppedItem != null) {

                        if (droppedItem instanceof Weapon) {
                            if (attackType == AttackType.Physical) {
                                if (droppedItem instanceof MainHand) {
                                    MainHand tileWeapon = (MainHand) droppedItem;

                                    // Check if the selected unit's main hand slot is null
                                    if (mainHand == null) {
                                        // Pick up the weapon, remove it from the tile's item list
                                        mainHand = tileWeapon;
                                        gp.playSE(20);
                                        gp.ui.addLogMessage(name + " picked up " + tileWeapon.getName());
                                        gp.tileM.removeItem(droppedItemIndex, col, row);
                                        movement = 0;
                                        preCol = col;
                                        preRow = row;
                                        calculateCombatStats();
                                    }
                                    // If the mainHand slot is not null and the current weapon is removable
                                    else if (mainHand != null && mainHand.isRemovable()) {
                                        // Swap the weapons between the player and the tile
                                        if (equippedWeapon == mainHand) {
                                            equippedWeapon = tileWeapon;
                                        }
                                        MainHand tempWeapon = mainHand;
                                        mainHand = tileWeapon;
                                        gp.playSE(20);
                                        gp.ui.addLogMessage(name + " picked up " + tileWeapon.getName());
                                        gp.tileM.switchItem(droppedItemIndex, tempWeapon, col, row);
                                        movement = 0;
                                        preCol = col;
                                        preRow = row;
                                        calculateCombatStats();
                                    } else if (!mainHand.isRemovable()) {
                                        gp.ui.addLogMessage(mainHand.getName() + " is not removable");
                                    }
                                }

                                if (droppedItem instanceof OffHand) {
                                    OffHand tileWeapon = (OffHand) droppedItem;

                                    // Check if the selected unit's offhand slot is null
                                    if (offHand == null) {
                                        // Pick up the weapon, remove it from the tile's item list
                                        offHand = tileWeapon;
                                        gp.playSE(20);
                                        gp.ui.addLogMessage(name + " picked up " + tileWeapon.getName());
                                        gp.tileM.removeItem(droppedItemIndex, col, row);
                                        movement = 0;
                                        preCol = col;
                                        preRow = row;
                                        calculateCombatStats();
                                    }
                                    // If the mainHand slot is not null and the current weapon is removable
                                    else if (offHand != null && offHand.isRemovable()) {
                                        // Swap the weapons between the player and the tile
                                        if (equippedWeapon == offHand) {
                                            equippedWeapon = tileWeapon;
                                        }
                                        OffHand tempWeapon = offHand;
                                        offHand = tileWeapon;
                                        gp.playSE(20);
                                        gp.ui.addLogMessage(name + " picked up " + tileWeapon.getName());
                                        gp.tileM.switchItem(droppedItemIndex, tempWeapon, col, row);
                                        movement = 0;
                                        preCol = col;
                                        preRow = row;
                                        calculateCombatStats();
                                    } else if (!offHand.isRemovable()) {
                                        gp.ui.addLogMessage(offHand.getName() + " is not removable");
                                    }
                                }
                            } else if (attackType == AttackType.Magical) {
                                gp.ui.addLogMessage(name + " can't use weapons");
                            }
                        }

                        if (droppedItem instanceof Trinket) {
                            Trinket tileTrinket = (Trinket) droppedItem;

                            // Check if the selected unit's trinket slot is null
                            if (trinket == null) {
                                // Pick up the trinket, remove it from the tile's item list
                                trinket = tileTrinket;
                                gp.playSE(20);
                                gp.ui.addLogMessage(name + " picked up " + tileTrinket.getName());
                                gp.tileM.removeItem(droppedItemIndex, col, row);
                                movement = 0;
                                preCol = col;
                                preRow = row;
                                calculateCombatStats();
                            }
                            // If the trinket slot is not null
                            else {
                                // Swap the trinket items between the player and the tile
                                Trinket tempTrinket = trinket;
                                trinket = tileTrinket;
                                gp.playSE(20);
                                gp.ui.addLogMessage(name + " picked up " + tileTrinket.getName());
                                gp.tileM.switchItem(droppedItemIndex, tempTrinket, col, row);
                                movement = 0;
                                preCol = col;
                                preRow = row;
                                calculateCombatStats();
                            }
                        }

                        if (droppedItem instanceof Potion) {
                            Potion tilePotion = (Potion) droppedItem;

                            // Check if the selected unit's potion slot is null
                            if (potion == null) {
                                // Pick up the trinket, remove it from the tile's item list
                                potion = tilePotion;
                                gp.ui.addLogMessage(name + " picked up " + tilePotion.getName());
                                gp.tileM.removeItem(droppedItemIndex, col, row);
                                movement = 0;
                                preCol = col;
                                preRow = row;
                                calculateCombatStats();
                            }
                            // If the potion slot is not null
                            else {
                               gp.ui.addLogMessage("Can only pick up potions if potion slot is empty");
                            }
                        }

                    } else {
                        gp.ui.addLogMessage("No item on this slot");
                    }
                } else {
                    gp.ui.addLogMessage("Can't pick up items when on another unit's tile");
                }
            }
            // Reset the SPACE key release state when the key is no longer pressed
            if (!keyH.isSpacePressed()) {
                spaceKeyReleased = true;
            }
        }
    }

    public void move() {
        // Check if the unit is not in a waiting state, is selected, and is allowed to move
        if (gp.selectedUnit != null && !wait && gp.selectedUnit == this && isMoving) {
            moveDelayCounter++; // Increment the move delay counter

            // Check if the delay counter has reached or exceeded the move delay threshold
            if (moveDelayCounter >= moveDelayThreshold) {
                moveDelayCounter = 0; // Reset the move delay counter

                // Calculate all possible valid moves from the current position
                List<int[]> validMoves = calculateValidMovement();

                // Initialize the target position with the current position
                int targetCol = col;
                int targetRow = row;

                // Determine the intended move direction based on key presses
                if (keyH.isUpPressed()) {
                    direction = "up";
                } else if (keyH.isDownPressed()) {
                    direction = "down";
                } else if (keyH.isLeftPressed()) {
                    direction = "left";
                } else if (keyH.isRightPressed()) {
                    direction = "right";
                } else if (!keyH.isUpPressed() && !keyH.isDownPressed() &&
                        !keyH.isLeftPressed() && !keyH.isRightPressed()) {
                    direction = "none"; // If no key is pressed, stop movement
                }

                // Update target position based on the direction
                switch (direction) {
                    case "up":
                        targetRow = row - 1; // Move up
                        break;
                    case "down":
                        targetRow = row + 1; // Move down
                        break;
                    case "left":
                        targetCol = col - 1; // Move left
                        break;
                    case "right":
                        targetCol = col + 1; // Move right
                        break;
                }

                // Check if the target position is within the list of valid moves
                boolean canMove = false;
                for (int[] move : validMoves) {
                    if (move[0] == targetCol && move[1] == targetRow) {
                        canMove = true; // The move is valid
                        break;
                    }
                }

                // If the move is valid and the unit is actually moving to a new position
                if (canMove && (col != targetCol || row != targetRow)) {
                    // Update column and row positions
                    col = targetCol;
                    row = targetRow;
                    updatePosition(); // Update pixel position based on the new tile position

                    // Play the movement sound effect only if the position has changed
                    if (unitType == UnitType.Cavalry) {
                        gp.playSE(8);
                    } else if (unitType == UnitType.Armored) {
                        gp.playSE(7);
                    } else if (unitType == UnitType.Infantry){
                        gp.playSE(4);
                    }
                }
            }
        }
    }

    // Method to start the unit's turn, enabling actions
    @Override
    public void startTurn() {
        wait = false;
    }

    // Method to end the unit's turn, disabling actions and resetting state
    @Override
    public void endTurn() {
        isMoving = false;   // Stop the unit's movement
        isAttacking = false; // Set attacking flag as false
        isHealing = false; // Set the healing flag as false
        wait = true;        // Set the unit to a waiting state
        movement = movementInitial; // Reset the unit's available movement
        preCol = col;       // Update the previous column to the current column
        preRow = row;       // Update the previous row to the current row
        direction = "none"; // Reset the direction
        gp.tileM.findAllVisibleTiles();
    }

    // Method to reset the unit's position to the previous position
    public void resetPosition() {
        isMoving = false;     // Stop the unit's movement
        col = preCol;   // Revert to the previous column
        row = preRow;   // Revert to the previous row
        x = getX();  // Update the x position in pixels
        y = getY();  // Update the y position in pixels
        direction = "none"; // Reset the direction
    }

    public void healEndTurn() {
        HP += (maxHP * 5) / 100;
        gp.ui.addLogMessage(name + " recovered some health.");
        if (HP > maxHP) {
            HP = maxHP;
        }
    }

    @Override
    public void Defeated() {
        if (HP <= 0) {
            if (deathQuote != null) {
                gp.ui.addLogMessage(name + ": " + deathQuote);
            }
            gp.playSE(18);
            gp.ui.addLogMessage(name + " is defeated.");
            if (gp.selectedUnit != null && gp.selectedUnit == this) {
                gp.selectedUnit = null;
            }
            gp.LightUnits.remove(this);
        }
    }

    public void gainExperience(int gainedExperience) {
        if (level < maxLevel) {
            exp += gainedExperience;
            gp.ui.addLogMessage(name + " received " + gainedExperience + " exp");
            if (exp >= 100) {
                levelUp();
            }
        }
    }

    public void levelUp() {
        if (level < maxLevel) {
            level++;
        }
        exp -= 100;
        calculateLevelUpStats();
        if (attackType == AttackType.Magical) {
            checkLevelUpSpells();
        }
        calculateCombatStats();
        if (exp >= 100) {
            levelUp();
        }
    }

    public void calculateLevelUpStats() {
        UtilityTool uTool = new UtilityTool();
        List<Runnable> tasks = new ArrayList<>();
        int delay = 300;  //300 ms delay between each message and sound effect

        gp.ui.addLogMessage("");
        gp.playSE(10);
        gp.ui.addLogMessage(name + " reached level " + level);

        // HP increase
        if (uTool.getRandomNumber() <= HPGrowthRate) {
            tasks.add(() -> {
                maxHP += 1;
                gp.playSE(11);
                gp.ui.addLogMessage("HP increased by 1");
            });
        }

        // Strength increase
        if (uTool.getRandomNumber() <= strengthGrowthRate) {
            tasks.add(() -> {
                strength += 1;
                gp.playSE(11);
                gp.ui.addLogMessage("Strength increased by 1");
            });
        }

        // Magic increase
        if (uTool.getRandomNumber() <= magicGrowthRate) {
            tasks.add(() -> {
                magic += 1;
                gp.playSE(11);
                gp.ui.addLogMessage("Magic increased by 1");
            });
        }

        // Skill increase
        if (uTool.getRandomNumber() <= skillGrowthRate) {
            tasks.add(() -> {
                skill += 1;
                gp.playSE(11);
                gp.ui.addLogMessage("Skill increased by 1");
            });
        }

        // Speed increase
        if (uTool.getRandomNumber() <= speedGrowthRate) {
            tasks.add(() -> {
                speed += 1;
                gp.playSE(11);
                gp.ui.addLogMessage("Speed increased by 1");
            });
        }

        // Luck increase
        if (uTool.getRandomNumber() <= luckGrowthRate) {
            tasks.add(() -> {
                luck += 1;
                gp.playSE(11);
                gp.ui.addLogMessage("Luck increased by 1");
            });
        }

        // Defense increase
        if (uTool.getRandomNumber() <= defenseGrowthRate) {
            tasks.add(() -> {
                defense += 1;
                gp.playSE(11);
                gp.ui.addLogMessage("Defense increased by 1");
            });
        }

        // Resistance increase
        if (uTool.getRandomNumber() <= resistanceGrowthRate) {
            tasks.add(() -> {
                resistance += 1;
                gp.playSE(11);
                gp.ui.addLogMessage("Resistance increased by 1");
            });
        }

        // Execute the tasks one by one with a delay
        executeWithDelay(tasks, delay);
    }

    // Method to update the unit's state, called every frame
    @Override
    public void update() {
        // Allow movement only during the player's phase
        if (gp.TurnM.getPlayerPhase()) {
            if (!gp.tileM.isItemWindowOpen()) {
                move();
                SelectPlayerUnit();
                cancelAction();
                endSelectedUnitTurn();
                chooseTarget();
                usePotion();
                healAlly();
                switchWeapons();
                useBeaconOfLight();
            }
            else if (gp.tileM.isItemWindowOpen()) {
                pickUpItem();
            }
        }

        // Update sprite animation
        spriteCounter++;
        // Toggle between sprite frames
        if (spriteCounter > 20) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;  // Reset the sprite counter
        }
    }

    public void setNextLevel(int newCol, int newRow) {
        col = newCol;
        row = newRow;
        preCol = col;
        preRow = row;
        updatePosition();
        startTurn();
    }

    /* Calculate all valid tiles the unit can move to within its movement range with the use of Breadth-First-Search (BFS)
    BFS is used in this scenario because it explores all possible moves starting from closer tiles before moving on to further ones  */
    public List<int[]> calculateValidMovement() {
        List<int[]> validMoves = new ArrayList<>();

        /* Use a queue for breadth-first search (BFS) to explore tiles within the movement range
        This queue will hold the tiles to be explored, with each tile being represented by its column, row,
        and the distance from the starting position */
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{preCol, preRow, 0}); // Start from the current position with 0 distance traveled

        // Track visited tiles to prevent revisiting the same tile
        boolean[][] visited = new boolean[gp.getMaxMapCol()][gp.getMaxMapRow()];
        visited[preCol][preRow] = true; // Mark the starting position as visited

        // Continue exploring tiles until there are no more to explore
        while (!queue.isEmpty()) {
            int[] current = queue.poll(); // Get the current tile from the front of the queue
            int currentCol = current[0];
            int currentRow = current[1];
            int currentDistance = current[2];

            // Add the current tile as a valid move option
            validMoves.add(new int[]{currentCol, currentRow});

            // Checks all possible movement directions (up, down, left, right) from the current tile
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : directions) {
                int newCol = currentCol + dir[0];
                int newRow = currentRow + dir[1];
                int newDistance = currentDistance + 1;

                // Check if the new tile is within the map bounds, not visited, within movement range, not wall and not occupied by an enemy or player unit
                if (gp.cChecker.isWithinMap(newCol, newRow) && !visited[newCol][newRow] && newDistance <= movement && gp.cChecker.validTile(newCol, newRow)) {
                    // If the tile is valid, add it to the queue to be explored
                    queue.add(new int[]{newCol, newRow, newDistance});
                    visited[newCol][newRow] = true; // Mark the tile as visited
                }
            }
        }
        return validMoves; // Return the list of all valid move tiles
    }

    // Calculate all tiles the unit can see to within its vision range with the use of Breadth-First-Search (BFS)
    public List<int[]> getVisibleTiles() {
        List<int[]> visibleTiles = new ArrayList<>();

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{preCol, preRow, 0}); // Start from the current position with 0 distance traveled

        // Track visited tiles to prevent revisiting the same tile
        boolean[][] visited = new boolean[gp.getMaxMapCol()][gp.getMaxMapRow()];
        visited[preCol][preRow] = true; // Mark the starting position as visited

        // Continue exploring tiles until there are no more to explore
        while (!queue.isEmpty()) {
            int[] current = queue.poll(); // Get the current tile from the front of the queue
            int currentCol = current[0];
            int currentRow = current[1];
            int currentDistance = current[2];

            // Add the current tile as a vision tile
            visibleTiles.add(new int[]{currentCol, currentRow});

            // Checks all possible directions (up, down, left, right) from the current tile
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : directions) {
                int newCol = currentCol + dir[0];
                int newRow = currentRow + dir[1];
                int newDistance = currentDistance + 1;

                // Check if the new tile is within the map bounds, not visited and within vision range
                if (gp.cChecker.isWithinMap(newCol, newRow) && !visited[newCol][newRow] && newDistance <= vision ) {
                    // If the tile is valid, add it to the queue to be explored
                    queue.add(new int[]{newCol, newRow, newDistance});
                    visited[newCol][newRow] = true; // Mark the tile as visited
                }
            }
        }
        return visibleTiles; // Return the list of all visible tiles
    }

    // Method to find all tiles with enemies in this unit's attack range based on its current position
    public List<int[]> getTilesWithEnemiesInRange() {
        // List to store the positions of enemies that are within attack range
        List<int[]> tilesWithEnemiesInRange = new ArrayList<>();

        // Determine the attack range (the maximum distance at which the unit can attack)
        int weaponRange = 0;
        if (attackType == AttackType.Physical && equippedWeapon != null) {
            weaponRange = equippedWeapon.getRange();  // Get range from equipped weapon
        }
        if (attackType == AttackType.Magical) {
            weaponRange = attackSpell.getRange(); // Get range from equipped attack spell
        }

        // Iterate over all possible tiles within the maximum attack range
        for (int dCol = -weaponRange; dCol <= weaponRange; dCol++) {
            for (int dRow = -weaponRange; dRow <= weaponRange; dRow++) {
                // Calculate Manhattan distance for the current offset (dCol, dRow)
                int manhattanDistance = Math.abs(dCol) + Math.abs(dRow);

                // Check if this tile is within the valid attack range (Manhattan distance between 1 and weaponRange)
                if (manhattanDistance >= 1 && manhattanDistance <= weaponRange) {
                    // Calculate the actual column and row of the attack tile
                    int attackCol = col + dCol;
                    int attackRow = row + dRow;

                    // Ensure the attack tile is within the bounds of the game map
                    if (gp.cChecker.isWithinMap(attackCol, attackRow)) {
                        // Check if there is an enemy on the current tile
                        if (!gp.cChecker.noEnemyOnTile(attackCol, attackRow)) {
                            // Add the position of the enemy to the list
                            tilesWithEnemiesInRange.add(new int[]{attackCol, attackRow});
                        }
                    }
                }
            }
        }

        // Return the list of enemies' positions within range
        return tilesWithEnemiesInRange;
    }

    // Method to find the actual enemy units in this unit's attack range based on this unit's attack range
    public List<ChaosUnit> getEnemiesInRange() {
        // List to store the enemy units that are within attack range
        List<ChaosUnit> enemiesInRange = new ArrayList<>();

        // Get the positions of tiles with enemies within the attack range
        List<int[]> tilesWithEnemiesInRange = getTilesWithEnemiesInRange();

        // Iterate over the list of tiles to find and collect the enemy units
        for (int[] tile : tilesWithEnemiesInRange) {
            int attackCol = tile[0];
            int attackRow = tile[1];

            // Retrieve the enemy unit on the current tile
            ChaosUnit enemyUnit = gp.cChecker.getEnemyOnTile(attackCol, attackRow);

            // If an enemy unit is found, add it to the list
            if (enemyUnit != null) {
                enemiesInRange.add(enemyUnit);
            }
        }

        // Return the list of enemy units within range
        return enemiesInRange;
    }

    // Method to find all tiles with players in this unit's healing range based on its current position
    public List<int[]> getTilesWithPlayersInRange() {
        // List to store the positions of players that are within healing range
        List<int[]> tilesWithPlayersInRange = new ArrayList<>();

        // Determine the healing range (the maximum distance at which the unit can heal)
        int healingRange = 0;
        if (healingSpell != null) {
            healingRange = healingSpell.getRange(); // Get range from healing spell
        }

        // Iterate over all possible tiles within the maximum healing range
        for (int dCol = -healingRange; dCol <= healingRange; dCol++) {
            for (int dRow = -healingRange; dRow <= healingRange; dRow++) {
                // Calculate Manhattan distance for the current offset (dCol, dRow)
                int manhattanDistance = Math.abs(dCol) + Math.abs(dRow);

                // Check if this tile is within the valid healing range (Manhattan distance between 1 and healingRange)
                if (manhattanDistance >= 1 && manhattanDistance <= healingRange) {
                    // Calculate the actual column and row of the healing tile
                    int attackCol = col + dCol;
                    int attackRow = row + dRow;

                    // Ensure the heLING tile is within the bounds of the game map
                    if (gp.cChecker.isWithinMap(attackCol, attackRow)) {
                        // Check if there is a player on the current tile
                        if (!gp.cChecker.noPlayerOnTile(attackCol, attackRow)) {
                            // Add the position of the enemy to the list
                            tilesWithPlayersInRange.add(new int[]{attackCol, attackRow});
                        }
                    }
                }
            }
        }

        // Return the list of players positions within range
        return tilesWithPlayersInRange;
    }

    // Method to find the actual player units in this unit's healing range based on this unit's healing range
    public List<LightUnit> getPlayersInRange() {
        // List to store the enemy units that are within attack range
        List<LightUnit> playersInRange = new ArrayList<>();

        // Get the positions of tiles with enemies within the attack range
        List<int[]> tilesWithPlayersInRange = getTilesWithPlayersInRange();

        // Iterate over the list of tiles to find and collect the enemy units
        for (int[] tile : tilesWithPlayersInRange) {
            int attackCol = tile[0];
            int attackRow = tile[1];

            // Retrieve the enemy unit on the current tile
            LightUnit playerUnit = gp.cChecker.getPlayerOnTile(attackCol, attackRow);

            // If an ally player unit is found, it's not this player unit, and has HP less than full then add it to the list
            if (playerUnit != null && playerUnit != this && playerUnit.HP < playerUnit.maxHP) {
                playersInRange.add(playerUnit);
            }
        }

        // Return the list of enemy units within range
        return playersInRange;
    }

    // Placeholder for prince
    public void goToNextMap() { }

    // Getters && Setters

    public boolean getIsMoving () { return isMoving; } // Return whether the unit is moving

    public boolean getIsAttacking () { return isAttacking; } // Return whether the unit is attacking

    public boolean getIsHealing () { return isHealing; } // Return whether the unit is healing an ally

}