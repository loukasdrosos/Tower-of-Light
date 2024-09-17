package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;
import main.KeyHandler;

public class Mage extends LightUnit{
    public Mage (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow) {
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
        className = "Mage";
        deathQuote = "Nooo... I don’t want to die yet...";
        finalMapQuote = "A battle between god and mortal? Sounds exhilarating to me";
        level = 1;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
        maxHP = 16;
        strength = 0;
        magic = 4;
        skill = 3;
        speed = 6;
        luck = 0;
        defense = 3;
        resistance = 4;
        movement = 3;
        vision = 6;
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        attackSpell = new Fire();
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"A novice mage", "and a fast", "learner with", "huge potential"} ;
    }

    @Override
    public void checkLevelUpSpells() {
        if (level == 5) {
            attackSpell = new Thunder();
            gp.ui.addLogMessage(name + " learned " + attackSpell.getName());
        }
        if (level == 7) {
            healingSpell = new Heal();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
        if (level == 10) {
            attackSpell = new Fimbulvetr();
            gp.ui.addLogMessage(name + " learned " + attackSpell.getName());
        }
        if (level == 14) {
            healingSpell = new Recover();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
        if (level == 16) {
            attackSpell = new Thoron();
            gp.ui.addLogMessage(name + " learned " + attackSpell.getName());
        }
        if (level == 19){
            healingSpell = new Physic();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 75;
        strengthGrowthRate = 0;
        magicGrowthRate = 75;
        skillGrowthRate = 60;
        speedGrowthRate = 70;
        luckGrowthRate = 60;
        defenseGrowthRate = 35;
        resistanceGrowthRate = 45;
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/LightUnits/Mage/Human_Mage_Up_1");
        up2 = setup("/LightUnits/Mage/Human_Mage_Up_2");
        down1 = setup("/LightUnits/Mage/Human_Mage_Down_1");
        down2 = setup("/LightUnits/Mage/Human_Mage_Down_2");
        left1 = setup("/LightUnits/Mage/Human_Mage_Left_1");
        left2 = setup("/LightUnits/Mage/Human_Mage_Left_2");
        right1 = setup("/LightUnits/Mage/Human_Mage_Right_1");
        right2 = setup("/LightUnits/Mage/Human_Mage_Right_2");
        default1 = setup("/LightUnits/Mage/Human_Mage_Default_1");
        default2 = setup("/LightUnits/Mage/Human_Mage_Default_2");
        portrait = setupPortrait("/LightUnits/Mage/Human_Mage_Portrait");
    }
}
