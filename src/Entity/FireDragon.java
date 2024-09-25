package Entity;

import Item.*;
import main.GamePanel;
import main.UtilityTool;

import java.util.ArrayList;
import java.util.List;

public class FireDragon extends ChaosUnit{
    public FireDragon(GamePanel gp, boolean boss, int startCol, int startRow) {
        super(gp);
        this.col = startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX();          // Calculate initial x position based on column
        y = getY();          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        this.boss = boss;
        setupStats();
        // Load unit's images for animations
        try {
            loadImage();
        } catch (Exception e) {
            System.out.println("Exception loadImage, ChaosUnit not loading properly");
        }
    }

    // Method to set up the unit's stats
    @Override
    public void setupStats() {
        className = "Fire Dragon";
        level = gp.aSetter.getEnemyLevel();
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 20;
        strength = 10;
        magic = 0;
        skill = 4;
        speed = 7;
        luck = 0;
        defense = 10;
        resistance = 7;
        movement = 3;
        setupGrowthRates();
        setStats();
        if (boss) {
            level = 13;
            maxHP = 45;
            strength = 20;
            magic = 0;
            skill = 9;
            speed = 14;
            luck = 2;
            defense = 20;
            resistance = 14;
        }
        race = UnitRace.Dragon;
        equippedWeapon = new FireBreath();
        unitType = UnitType.Infantry;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"Dragons that expel", "breaths of fire", "in order to", "singe their foes."};
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 90;
        strengthGrowthRate = 55;
        magicGrowthRate = 0;
        skillGrowthRate = 45;
        speedGrowthRate = 40;
        luckGrowthRate = 10;
        defenseGrowthRate = 45;
        resistanceGrowthRate = 35;
    }

    @Override
    public void dropItem() {
        Item item = null;
        if (boss) {
            item = new MageRing();
        }
        else {
            UtilityTool uTool = new UtilityTool();
            int number = uTool.getRandomNumber();

            if (level <= 20) {
                if (number <= 20) {
                    item = new SilverShield();
                } else if (number > 20 && number <= 30) {
                    item = new SilverSword();
                } else if (number > 30 && number <= 40) {
                    item = new SilverLance();
                } else if (number > 40 && number <= 50) {
                    item = new MageRing();
                } else if (number > 50 && number <= 70) {
                    item = new Elixir();
                }
            }
            if (level > 20) {
                if (number <= 10) {
                    item = new SilverShield();
                } else if (number > 10 && number <= 15) {
                    item = new Gradivus();
                } else if (number > 15 && number <= 20) {
                    item = new Mercurius();
                } else if (number > 20 && number <= 30) {
                    item = new SilverSword();
                } else if (number > 30 && number <= 40) {
                    item = new SilverLance();
                } else if (number > 40 && number <= 50) {
                    item = new MageRing();
                } else if (number > 50 && number <= 70) {
                    item = new Elixir();
                } else if (number > 70 && number <= 75) {
                    item = new Dracoshield();
                }
            }
        }

        if (item != null) {
            gp.tileM.addItems(item, col, row);
        }
    }

    @Override
    public void Defeated() {
        if (HP <= 0) {
            if (name != null) {
                gp.ui.addLogMessage(name + " is defeated");
            } else {
                gp.ui.addLogMessage(String.valueOf(getRace()) + " " + className + " is defeated");
            }
            if (boss) {
                List<Runnable> tasks = new ArrayList<>();
                int delay = 300;  //300 ms delay between each message and sound effect

                tasks.add(() -> {
                    gp.ui.addLogMessage("");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: The dragon was as strong as the legends said");
                });

                tasks.add(() -> {
                    gp.ui.addLogMessage("Alm: They Chaos army is getting stronger but we won't give up");
                });

                // Execute the tasks one by one with a delay
                executeWithDelay(tasks, delay);

                gp.tileM.loadStairs();
            }
            dropItem();
            gp.ChaosUnits.remove(this);
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Up_1");
        up2 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Up_2");
        down1 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Down_1");
        down2 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Down_2");
        left1 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Left_1");
        left2 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Left_2");
        right1 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Right_1");
        right2 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Right_2");
        default1 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Default_1");
        default2 = setup("/ChaosUnits/Fire_Dragon/Fire_Dragon_Default_2");
        portrait = setupPortrait("/ChaosUnits/Fire_Dragon/Fire_Dragon_Portrait");
    }

}
