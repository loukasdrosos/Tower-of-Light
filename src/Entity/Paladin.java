package Entity;

import Item.*;
import main.GamePanel;
import main.KeyHandler;

public class Paladin extends LightUnit{
    public Paladin (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow) {
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
        mainHand = new IronLance();
        equippedWeapon = this.mainHand;
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
        className = "Paladin";
        deathQuote = "Gah. Jeez, I’m such an idiot...";
        finalMapQuote = "You're gonna pay for what you did to Valentia!";
        level = 1;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
        maxHP = 28;
        strength = 12;
        magic = 0;
        skill = 7;
        speed = 4;
        luck = 0;
        defense = 13;
        resistance = 3;
        movement = 3;
        vision = 6;
        unitType = UnitType.Armored;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"A soldier", "who relies on his", "armor a bit", "too much."} ;
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 95;
        strengthGrowthRate = 70;
        magicGrowthRate = 0;
        skillGrowthRate = 55;
        speedGrowthRate = 45;
        luckGrowthRate = 35;
        defenseGrowthRate = 75;
        resistanceGrowthRate = 35;
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/LightUnits/Paladin/Tauren_Paladin_Up_1");
        up2 = setup("/LightUnits/Paladin/Tauren_Paladin_Up_2");
        down1 = setup("/LightUnits/Paladin/Tauren_Paladin_Down_1");
        down2 = setup("/LightUnits/Paladin/Tauren_Paladin_Down_2");
        left1 = setup("/LightUnits/Paladin/Tauren_Paladin_Left_1");
        left2 = setup("/LightUnits/Paladin/Tauren_Paladin_Left_2");
        right1 = setup("/LightUnits/Paladin/Tauren_Paladin_Right_1");
        right2 = setup("/LightUnits/Paladin/Tauren_Paladin_Right_2");
        default1 = setup("/LightUnits/Paladin/Tauren_Paladin_Default_1");
        default2 = setup("/LightUnits/Paladin/Tauren_Paladin_Default_2");
        portrait = setupPortrait("/LightUnits/Paladin/Tauren_Paladin_Portrait");
    }
}
