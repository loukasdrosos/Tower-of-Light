package Entity;

import Item.*;
import Spells.Eclipse;
import main.GamePanel;
import main.UtilityTool;

public class ChaosDragon extends ChaosUnit{
    public ChaosDragon(GamePanel gp, int startCol, int startRow) {
        super(gp);
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
        className = "Chaos Dragon";
        level = gp.aSetter.getEnemyLevel();
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 23;
        strength = 0;
        magic = 11;
        skill = 6;
        speed = 8;
        luck = 0;
        defense = 7;
        resistance = 10;
        movement = 3;
        setupGrowthRates();
        setStats();
        race = UnitRace.Dragon;
        attackSpell = new Eclipse();
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"A resurrected", "dragon that now", "possesses", "dark magic."};
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 80;
        strengthGrowthRate = 0;
        magicGrowthRate = 60;
        skillGrowthRate = 45;
        speedGrowthRate = 50;
        luckGrowthRate = 10;
        defenseGrowthRate = 35;
        resistanceGrowthRate = 45;
    }

    @Override
    public void dropItem() {
        Item item = null;
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
                item = new DemonRing();
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

        if (item != null) {
            gp.tileM.addItems(item, col, row);
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Up_1");
        up2 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Up_2");
        down1 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Down_1");
        down2 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Down_2");
        left1 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Left_1");
        left2 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Left_2");
        right1 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Right_1");
        right2 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Right_2");
        default1 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Default_1");
        default2 = setup("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Default_2");
        portrait = setupPortrait("/ChaosUnits/Chaos_Dragon/Chaos_Dragon_Portrait");
    }
}
