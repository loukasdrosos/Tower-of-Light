package Entity;

import Item.*;
import main.GamePanel;
import main.KeyHandler;

public class Warrior extends LightUnit{
    public Warrior (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow) {
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
        mainHand = new Ragnell();
        offHand = new Wyrmslayer();
        equippedWeapon = this.mainHand;
        potion = new Elixir();
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
        className = "Warrior";
        deathQuote = "I'll take a proud death over a life of kneeling to a mad god!";
        finalMapQuote = "Yours isn't the world I fought to protect!";
        level = 10;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
        maxHP = 30;
        strength = 16;
        magic = 0;
        skill = 17;
        speed = 16;
        luck = 5;
        defense = 13;
        resistance = 5;
        movementInitial = 6;
        movement = movementInitial;
        vision = 6;
        unitType = UnitType.Infantry;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"A warrior known for", "his heroic actions.", "Only he can", "wield the mystic", "sword Ragnell."} ;
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 85;
        strengthGrowthRate = 65;
        magicGrowthRate = 0;
        skillGrowthRate = 70;
        speedGrowthRate = 65;
        luckGrowthRate = 55;
        defenseGrowthRate = 40;
        resistanceGrowthRate = 30;
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/LightUnits/Warrior/Human_Warrior_Up_1");
        up2 = setup("/LightUnits/Warrior/Human_Warrior_Up_2");
        down1 = setup("/LightUnits/Warrior/Human_Warrior_Down_1");
        down2 = setup("/LightUnits/Warrior/Human_Warrior_Down_2");
        left1 = setup("/LightUnits/Warrior/Human_Warrior_Left_1");
        left2 = setup("/LightUnits/Warrior/Human_Warrior_Left_2");
        right1 = setup("/LightUnits/Warrior/Human_Warrior_Right_1");
        right2 = setup("/LightUnits/Warrior/Human_Warrior_Right_2");
        default1 = setup("/LightUnits/Warrior/Human_Warrior_Default_1");
        default2 = setup("/LightUnits/Warrior/Human_Warrior_Default_2");
        portrait = setupPortrait("/LightUnits/Warrior/Human_Warrior_Portrait");
    }
}
