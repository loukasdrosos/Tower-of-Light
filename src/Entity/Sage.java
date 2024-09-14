package Entity;

import Item.Trinket;
import Spells.*;
import main.GamePanel;
import main.KeyHandler;

public class Sage extends LightUnit{

    public Sage (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow, Trinket trinket) {
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
        this.trinket = trinket;
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
        AttackSpell Thunder = new Thunder();
        className = "Sage";
        deathQuote = "I am sorry everyone, i failed you";
        level = 1;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
        maxHP = 20;
        strength = 0;
        magic = 7;
        skill = 5;
        speed = 7;
        luck = 0;
        defense = 4;
        resistance = 5;
        movement = 3;
        vision = 6;
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        attackSpell = Thunder;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"The prince of ", "the kingdom of", "Valentia and the", "one worthy to wield", "the divine blade", "Lightbringer."} ;
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 85;
        strengthGrowthRate = 0;
        magicGrowthRate = 55;
        skillGrowthRate = 50;
        speedGrowthRate = 50;
        luckGrowthRate = 65;
        defenseGrowthRate = 35;
        resistanceGrowthRate = 45;
        // Total Growth Rates = 440
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
}
