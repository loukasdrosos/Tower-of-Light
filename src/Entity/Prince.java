package Entity;

import Item.*;
import Spells.AttackSpell;
import Spells.Thoron;
import main.GamePanel;
import main.KeyHandler;

public class Prince extends LightUnit{

    public Prince (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow, MainHand mainhand, Trinket trinket) {
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
        this.mainHand = mainhand;
        equippedWeapon = this.mainHand;
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
        className = "Prince";
        level = 1;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
        maxHP = 23;
        strength = 10;
        magic = 3;
        skill = 7;
        speed = 10;
        luck = 0;
        defense = 8;
        resistance = 3;
        movement = 3;
        vision = 6;
        unitType = UnitType.Infantry;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"The prince of ", "the kingdom of", "Valentia and the", "one worthy to wield", "the divine blade", "Lightbringer."} ;
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 80;
        strengthGrowthRate = 60;
        magicGrowthRate = 35;
        skillGrowthRate = 60;
        speedGrowthRate = 65;
        luckGrowthRate = 70;
        defenseGrowthRate = 45;
        resistanceGrowthRate = 25;
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
