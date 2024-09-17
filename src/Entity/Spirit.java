package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;
import main.UtilityTool;

public class Spirit extends ChaosUnit{
    public Spirit(GamePanel gp, boolean canMove, int startCol, int startRow) {
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
        className = "Spirit";
        level = 15;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 20;
        strength = 0;
        magic = 8;
        skill = 7;
        speed = 10;
        luck = 0;
        defense = 2;
        resistance = 6;
        movement = 3;
        if (!canMove) {
            movement = 0;
        }
        setupGrowthRates();
        setStats();
        randomizeRace();
        randomizeItems();
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"An angry spirit", "awaken to try and", "find peace again."};
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 65;
        strengthGrowthRate = 0;
        magicGrowthRate = 50;
        skillGrowthRate = 40;
        speedGrowthRate = 50;
        luckGrowthRate = 30;
        defenseGrowthRate = 20;
        resistanceGrowthRate = 35;
    }

    public void randomizeRace() {
        UtilityTool uTool = new UtilityTool();
        int randomNumber = uTool.getRandomNumber();

        // 50% chance for Human
        if (randomNumber <= 50) {
            race = UnitRace.Human;
        }
        // 50% chance for Elf
        else if (randomNumber > 50 && randomNumber <= 100) {
            race = UnitRace.Elf;
        }
    }

    public void randomizeItems() {
        UtilityTool uTool = new UtilityTool();
        int number = uTool.getRandomNumber();

        if (level <= 10) {
            attackSpell = new Fire();
        }
        if (level > 10 && level <= 12) {
            attackSpell = new Fimbulvetr();
            if (number <= 15) {
                trinket = new MageRing();
            }
        }
        if (level > 12 && level <= 18) {
            attackSpell = new Valflame();
            if (number <= 35){
                trinket = new MageRing();
            }
        }
        if (level > 18) {
            attackSpell = new Valflame();
             if (number <= 60) {
                trinket = new MageRing();
            }
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Spirit/Spirit_Up_1");
        up2 = setup("/ChaosUnits/Spirit/Spirit_Up_2");
        down1 = setup("/ChaosUnits/Spirit/Spirit_Down_1");
        down2 = setup("/ChaosUnits/Spirit/Spirit_Down_2");
        left1 = setup("/ChaosUnits/Spirit/Spirit_Left_1");
        left2 = setup("/ChaosUnits/Spirit/Spirit_Left_2");
        right1 = setup("/ChaosUnits/Spirit/Spirit_Right_1");
        right2 = setup("/ChaosUnits/Spirit/Spirit_Right_2");
        default1 = setup("/ChaosUnits/Spirit/Spirit_Default_1");
        default2 = setup("/ChaosUnits/Spirit/Spirit_Default_2");
        portrait = setupPortrait("/ChaosUnits/Spirit/Spirit_Portrait");
    }
}
