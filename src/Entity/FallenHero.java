package Entity;

import Item.*;
import main.GamePanel;
import main.UtilityTool;

public class FallenHero extends ChaosUnit{
    public FallenHero(GamePanel gp, boolean canMove, int startCol, int startRow) {
        super(gp, canMove);
        this.col = startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX();          // Calculate initial x position based on column
        y = getY();          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
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
        className = "Fallen Hero";
        level = 8;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 22;
        strength = 8;
        magic = 0;
        skill = 4;
        speed = 8;
        luck = 0;
        defense = 5;
        resistance = 3;
        movement = 3;
        if (!canMove) {
            movement = 0;
        }
        setupGrowthRates();
        setStats();
        randomizeRace();
        randomizeItems();
        unitType = UnitType.Infantry;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"A fallen hero", "resurrected with the", "power of Chaos."};
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 90;
        strengthGrowthRate = 40;
        magicGrowthRate = 0;
        skillGrowthRate = 35;
        speedGrowthRate = 35;
        luckGrowthRate = 50;
        defenseGrowthRate = 60;
        resistanceGrowthRate = 25;
    }

    public void randomizeRace() {
        UtilityTool uTool = new UtilityTool();
        int randomNumber = uTool.getRandomNumber();

        // 25% chance for Human
        if (randomNumber <= 25) {
            race = UnitRace.Human;
        }
        // 25% chance for Orc
        else if (randomNumber > 25 && randomNumber <= 50) {
            race = UnitRace.Orc;
        }
        // 25% chance for Elf
        else if (randomNumber > 50 && randomNumber <= 75) {
            race = UnitRace.Elf;
        }
        // 25% chance for Tauren
        else if (randomNumber > 75 && randomNumber <= 100) {
            race = UnitRace.Tauren;
        }
    }

    public void randomizeItems() {
        UtilityTool uTool = new UtilityTool();
        int number = uTool.getRandomNumber();

        if (level <= 3) {
            equippedWeapon = new BlightedClaws();
        }
        if (level == 4) {
            if (number <= 50) {
                equippedWeapon = new BlightedClaws();
            } else if (number > 50 && number <= 100) {
                equippedWeapon = new VeninClaws();
            }
        }
        if (level > 4 && level <= 8) {
            equippedWeapon = new VeninClaws();
            if (number <= 40){
                trinket = new IronShield();
            }
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Up_1");
        up2 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Up_2");
        down1 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Down_1");
        down2 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Down_2");
        left1 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Left_1");
        left2 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Left_2");
        right1 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Right_1");
        right2 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Right_2");
        default1 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Default_1");
        default2 = setup("/ChaosUnits/Fallen_Hero/Fallen_Hero_Default_2");
        portrait = setupPortrait("/ChaosUnits/Fallen_Hero/Fallen_Hero_Portrait");
    }
}
