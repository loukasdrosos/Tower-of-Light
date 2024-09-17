package Entity;

import Item.*;
import main.GamePanel;
import main.UtilityTool;

public class Titan extends ChaosUnit{
    public Titan(GamePanel gp, boolean canMove, boolean boss, int startCol, int startRow) {
        super(gp, canMove);
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
        className = "Titan";
        level = 3;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 30;
        strength = 13;
        magic = 0;
        skill = 5;
        speed = 11;
        luck = 0;
        defense = 5;
        resistance = 1;
        movement = 3;
        if (!canMove) {
            movement = 0;
        }
        setupGrowthRates();
        setStats();
        if (boss) {
            level = 5;
            maxHP = 38;
            strength = 18;
            magic = 0;
            skill = 9;
            speed = 15;
            luck = 0;
            defense = 10;
            resistance = 4;
        }
        randomizeRace();
        randomizeItems();
        unitType = UnitType.Infantry;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"The corpse of an", "ancient giant.", "Has remarkable", "strength."};
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 100;
        strengthGrowthRate = 65;
        magicGrowthRate = 0;
        skillGrowthRate = 40;
        speedGrowthRate = 40;
        luckGrowthRate = 30;
        defenseGrowthRate = 35;
        resistanceGrowthRate = 15;
    }

    public void randomizeRace() {
        UtilityTool uTool = new UtilityTool();
        int randomNumber = uTool.getRandomNumber();

        // 50% chance for Human
        if (randomNumber <= 50) {
            race = UnitRace.Orc;
        }
        // 50% chance for Tauren
        else if (randomNumber > 50 && randomNumber <= 100) {
            race = UnitRace.Tauren;
        }
    }

    public void randomizeItems() {
        if (boss) {
            equippedWeapon = new SteelAxe();
        }
        else {
            UtilityTool uTool = new UtilityTool();

            if (level <= 4) {
                equippedWeapon = new IronAxe();
            }
            if (level > 4 && level <= 7) {
                int number = uTool.getRandomNumber();
                if (number <= 45) {
                    equippedWeapon = new IronAxe();
                } else if (number > 45 && number <= 85) {
                    equippedWeapon = new SteelAxe();
                } else if (number > 85 && number <= 100) {
                    equippedWeapon = new Hammer();
                }
                number = uTool.getRandomNumber();
                if (number <= 25) {
                    trinket = new IronShield();
                }
            }
            if (level > 7) {
                int number = uTool.getRandomNumber();
                if (number <= 65) {
                    equippedWeapon = new SteelAxe();
                } else if (number > 65 && number <= 85) {
                    equippedWeapon = new KillerAxe();
                } else if (number > 85 && number <= 100) {
                    equippedWeapon = new Hammer();
                }
                number = uTool.getRandomNumber();
                if (number <= 40) {
                    trinket = new IronShield();
                }
            }
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        if (race == UnitRace.Orc) {
            up1 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Up_1");
            up2 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Up_2");
            down1 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Down_1");
            down2 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Down_2");
            left1 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Left_1");
            left2 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Left_2");
            right1 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Right_1");
            right2 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Right_2");
            default1 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Default_1");
            default2 = setup("/ChaosUnits/Orc_Titan/Orc_Titan_Default_2");
            portrait = setupPortrait("/ChaosUnits/Orc_Titan/Orc_Titan_Portrait");
        }
        else if (race == UnitRace.Tauren) {
            up1 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Up_1");
            up2 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Up_2");
            down1 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Down_1");
            down2 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Down_2");
            left1 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Left_1");
            left2 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Left_2");
            right1 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Right_1");
            right2 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Right_2");
            default1 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Default_1");
            default2 = setup("/ChaosUnits/Tauren_Titan/Tauren_Titan_Default_2");
            portrait = setupPortrait("/ChaosUnits/Tauren_Titan/Tauren_Titan_Portrait");
        }
    }
}
