package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;
import main.KeyHandler;

public class DarkMage extends LightUnit{
    public DarkMage (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow) {
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
        trinket = new SilverShield();
        potion = new Concoction();
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
        className = "Dark Mage";
        deathQuote = "Tsk, my bad...";
        finalMapQuote = "Who needs gods when you're as strong as me";
        level = 10;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
        maxHP = 30;
        strength = 0;
        magic = 15;
        skill = 10;
        speed = 10;
        luck = 3;
        defense = 11;
        resistance = 13;
        movementInitial = 6;
        movement = movementInitial;
        vision = 6;
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        attackSpell = new Miasma();
        healingSpell = new Heal();
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"An expert in", "dark magic who", "wants to be the,", "strongest sorcerer", "in the world."} ;
    }

    @Override
    public void checkLevelUpSpells() {
        if (level == 13){
            healingSpell = new Recover();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
        if (level == 15) {
            attackSpell = new Nosferatu();
            gp.ui.addLogMessage(name + " learned " + attackSpell.getName());
        }
        if (level == 18) {
            healingSpell = new Physic();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
        if (level == 20) {
            attackSpell = new Goetia();
            gp.ui.addLogMessage(name + " learned " + attackSpell.getName());
        }
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 90;
        strengthGrowthRate = 0;
        magicGrowthRate = 60;
        skillGrowthRate = 60;
        speedGrowthRate = 55;
        luckGrowthRate = 40;
        defenseGrowthRate = 50;
        resistanceGrowthRate = 50;
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Up_1");
        up2 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Up_2");
        down1 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Down_1");
        down2 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Down_2");
        left1 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Left_1");
        left2 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Left_2");
        right1 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Right_1");
        right2 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Right_2");
        default1 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Default_1");
        default2 = setup("/LightUnits/Dark_Mage/Orc_Dark_Mage_Default_2");
        portrait = setupPortrait("/LightUnits/Dark_Mage/Orc_Dark_Mage_Portrait");
    }
}
