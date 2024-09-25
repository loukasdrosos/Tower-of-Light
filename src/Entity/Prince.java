package Entity;

import Item.*;
import main.GamePanel;
import main.KeyHandler;

import java.util.ArrayList;
import java.util.List;

public class Prince extends LightUnit{

    public Prince (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow) {
        super(gp, keyH);
        this.col =  startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX();          // Calculate initial x position based on column
        y = getY();          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        this.name = name;
        this.race = race;
        setupGrowthRates();
        mainHand = new Lightbringer(gp);
        equippedWeapon = this.mainHand;
        trinket = new RoyalShield();
        potion = new Vulnerary();
        setupStats();
        // Load unit's images for animations
        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage, LightUnit not loading properly");
        }
    }

    // Method to set up the unit's stats
    @Override
    public void setupStats() {
        className = "Prince";
        deathQuote = "Damn!! Just a bit more…";
        finalMapQuote = "It's over Grima! Your chain of tragedies ends here";
        level = 1;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
//        maxHP = 21;
//        strength = 10;
//        magic = 0;
//        skill = 5;
//        speed = 9;
//        luck = 0;
//        defense = 8;
//        resistance = 3;
    //    movementInitial = 3;

        maxHP = 21;
        strength = 80;
        magic = 80;
        skill = 80;
        speed = 80;
        luck = 80;
        defense = 80;
        resistance = 80;
        movementInitial = 40;
        movement = movementInitial;
        vision = 6;
        unitType = UnitType.Infantry;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        BeaconOfLight = true;
        description = new String[]{"The prince of ", "the kingdom of", "Valentia and the", "one worthy to wield", "the divine blade", "Lightbringer."} ;
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 80;
        strengthGrowthRate = 65;
        magicGrowthRate = 0;
        skillGrowthRate = 60;
        speedGrowthRate = 65;
        luckGrowthRate = 70;
        defenseGrowthRate = 60;
        resistanceGrowthRate = 25;
    }

    @Override
    public void goToNextMap() {
        if (gp.selectedUnit != null && isSelected && isMoving && !wait) {
            if (col == 50 && row == 1) {
                if (keyH.isSpacePressed()) {
                    if (gp.cChecker.noPlayerOnTile(col, row)) {
                        gp.aSetter.setNextMap();
                    } else if (!gp.cChecker.noPlayerOnTile(col, row)) {
                        gp.ui.addLogMessage(name + " has to be the only one on the stairs");
                    }
                }
            }
        }
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
                goToNextMap();
            }
            else if (gp.tileM.isItemWindowOpen()) {
                pickUpItem();
            }
        }

        // Update Prince's Lightbringer stats
        if (mainHand != null) {
            mainHand.update();
        }
        calculateCombatStats(); // Update Princes's combat stats since Lightbringer is getting stronger

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


    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/LightUnits/Prince/Human_Prince_Up_1");
        up2 = setup("/LightUnits/Prince/Human_Prince_Up_2");
        down1 = setup("/LightUnits/Prince/Human_Prince_Down_1");
        down2 = setup("/LightUnits/Prince/Human_Prince_Down_2");
        left1 = setup("/LightUnits/Prince/Human_Prince_Left_1");
        left2 = setup("/LightUnits/Prince/Human_Prince_Left_2");
        right1 = setup("/LightUnits/Prince/Human_Prince_Right_1");
        right2 = setup("/LightUnits/Prince/Human_Prince_Right_2");
        default1 = setup("/LightUnits/Prince/Human_Prince_Default_1");
        default2 = setup("/LightUnits/Prince/Human_Prince_Default_2");
        portrait = setupPortrait("/LightUnits/Prince/Human_Prince_Portrait");
    }

    @Override
    public void Defeated() {
        if (HP <= 0) {
            List<Runnable> tasks = new ArrayList<>();
            int delay = 1000;  //1000 ms delay between each task

            if (deathQuote != null) {
                tasks.add(() -> {
                    gp.ui.addLogMessage(name + ": " + deathQuote);
                    gp.playSE(18);
                    gp.ui.addLogMessage(name + " is defeated");
                    if (gp.selectedUnit != null && gp.selectedUnit == this) {
                        gp.selectedUnit = null;
                    }
                    gp.LightUnits.remove(this);
                });
            }
            tasks.add(() -> {
                gp.stopMusic();
                gp.playMusic(1);
                gp.gameState = gp.gameOverState;
            });

            // Execute the tasks one by one with a delay
            executeWithDelay(tasks, delay);
        }
    }
}
