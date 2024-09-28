package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;
import main.KeyHandler;

public class Cleric extends LightUnit{
    public Cleric (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow) {
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
        className = "Cleric";
        deathQuote = "My fate’s come to an end too...";
        finalMapQuote = "This world doesn't exist as a plaything for dragons";
        level = 1;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
        maxHP = 21;
        strength = 0;
        magic = 5;
        skill = 4;
        speed = 6;
        luck = 0;
        defense = 3;
        resistance = 8;
        movementInitial = 3;
        movement = movementInitial;
        vision = 6;
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        attackSpell = new Seraphim();
        healingSpell = new Heal();
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"A follower of ", "the God of Light", "turned dark due", "to the uprise ", "of the Chaos army."} ;
    }

    @Override
    public void checkLevelUpSpells() {
        if (level == 6) {
            healingSpell = new Recover();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
        if (level == 14) {
            healingSpell = new Physic();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
        if (level == 18) {
            attackSpell = new Aureola();
            gp.ui.addLogMessage(name + " learned " + attackSpell.getName());
        }
        if (level == 20){
            healingSpell = new HolyBlessing();
            gp.ui.addLogMessage(name + " learned " + healingSpell.getName());
        }
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 65;
        strengthGrowthRate = 0;
        magicGrowthRate = 60;
        skillGrowthRate = 55;
        speedGrowthRate = 50;
        luckGrowthRate = 80;
        defenseGrowthRate = 25;
        resistanceGrowthRate = 75;
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/LightUnits/Cleric/Elf_Cleric_Up_1");
        up2 = setup("/LightUnits/Cleric/Elf_Cleric_Up_2");
        down1 = setup("/LightUnits/Cleric/Elf_Cleric_Down_1");
        down2 = setup("/LightUnits/Cleric/Elf_Cleric_Down_2");
        left1 = setup("/LightUnits/Cleric/Elf_Cleric_Left_1");
        left2 = setup("/LightUnits/Cleric/Elf_Cleric_Left_2");
        right1 = setup("/LightUnits/Cleric/Elf_Cleric_Right_1");
        right2 = setup("/LightUnits/Cleric/Elf_Cleric_Right_2");
        default1 = setup("/LightUnits/Cleric/Elf_Cleric_Default_1");
        default2 = setup("/LightUnits/Cleric/Elf_Cleric_Default_2");
        portrait = setupPortrait("/LightUnits/Cleric/Elf_Cleric_Portrait");
    }
}
