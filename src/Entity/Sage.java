package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;
import main.KeyHandler;

public class Sage extends LightUnit{

    public Sage (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow) {
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
        className = "Sage";
        deathQuote = "I am sorry Alm...";
        finalMapQuote = " I'll never accept a god so horrible";
        level = 1;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
        maxHP = 20;
        strength = 0;
        magic = 100;
        skill = 5;
        speed = 7;
        luck = 0;
        defense = 4;
        resistance = 5;
        movementInitial = 20;
        movement = movementInitial;
        vision = 6;
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        attackSpell = new Thunder();
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"An expert in ", "magic and Alm's", "childhood friend,", "following him ", "wherever he goes."} ;
    }

    @Override
    public void checkLevelUpSpells() {
        if (level == 4) {
            healingSpell = new Heal();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
        if (level == 9) {
            attackSpell = new Sagittae();
            gp.ui.addLogMessage(name + " learned " + attackSpell.getName());
        }
        if (level == 12) {
            healingSpell = new Recover();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
        if (level == 18) {
            attackSpell = new Forseti();
            gp.ui.addLogMessage(name + " learned " + attackSpell.getName());
        }
        if (level == 20){
            healingSpell = new Physic();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 70;
        strengthGrowthRate = 0;
        magicGrowthRate = 80;
        skillGrowthRate = 60;
        speedGrowthRate = 60;
        luckGrowthRate = 50;
        defenseGrowthRate = 35;
        resistanceGrowthRate = 55;
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/LightUnits/Sage/Elf_Sage_Up_1");
        up2 = setup("/LightUnits/Sage/Elf_Sage_Up_2");
        down1 = setup("/LightUnits/Sage/Elf_Sage_Down_1");
        down2 = setup("/LightUnits/Sage/Elf_Sage_Down_2");
        left1 = setup("/LightUnits/Sage/Elf_Sage_Left_1");
        left2 = setup("/LightUnits/Sage/Elf_Sage_Left_2");
        right1 = setup("/LightUnits/Sage/Elf_Sage_Right_1");
        right2 = setup("/LightUnits/Sage/Elf_Sage_Right_2");
        default1 = setup("/LightUnits/Sage/Elf_Sage_Default_1");
        default2 = setup("/LightUnits/Sage/Elf_Sage_Default_2");
        portrait = setupPortrait("/LightUnits/Sage/Elf_Sage_Portrait");
    }
}
