package Entity;

import Item.*;
import main.GamePanel;
import main.KeyHandler;

public class Prince extends LightUnit{

    public Prince (GamePanel gp, KeyHandler keyH, int startCol, int startRow, MainHand mainhand, Trinket trinket) {
        super(gp, keyH);
        this.col =  startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX(col);          // Calculate initial x position based on column
        y = getY(row);          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        setupStats();
        setupGrowthRates();
        this.mainHand = mainhand;
        equippedWeapon = this.mainHand;
        this.trinket = trinket;
        calculateCombatStats();
        // Load unit's images for animations
        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage, LightUnit not loading properly");
        }
    }

    // Method to set up the unit's stats
    @Override
    public void setupStats() {
        name = "Alm";
        className = "Prince";
        level = 1;
        exp = 0;
        maxHP = 23;
        strength = 10;
        magic = 3;
        skill = 7;
        speed = 8;
        luck = 0;
        defense = 8;
        resistance = 30;
        movement = 3;
        vision = 5;
        type = UnitType.Human;
        boostStatsForClasses();
        HP = maxHP;
        armored = false;
        mounted = false;
        physical = true;
        magical = false;
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
