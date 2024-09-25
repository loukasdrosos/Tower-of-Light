package Entity;

import Item.*;
import main.GamePanel;
import main.KeyHandler;

public class Knight extends LightUnit{
    public Knight (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow) {
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
        className = "Knight";
        if (race == UnitRace.Elf) {
            deathQuote = "I have lived my life without regrets...";
            finalMapQuote = "This is my world! I won't allow you to endanger it!";
            description = new String[]{"The commander of ", "the kingdom's", "army and the", "bodyguard of", "the royal family"} ;
            mainHand = new SteelLance();
            trinket = new IronShield();
        }
        else {
            deathQuote = "I’m no good after all. I’m sorry, everyone...";
            finalMapQuote = "This is a battle we cannot lose!";
            description = new String[]{"A young lord", "who hopes to", "rule Valentia one", "day and strives to", "defeat Alm for", "the crown."} ;
            mainHand = new IronLance();
        }
        equippedWeapon = mainHand;
        level = 1;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
//        maxHP = 25;
//        strength = 9;
//        magic = 0;
//        skill = 7;
//        speed = 8;
//        luck = 0;
//        defense = 9;
//        resistance = 6;
//        movementInitial = 4;
        maxHP = 21;
        strength = 80;
        magic = 80;
        skill = 80;
        speed = 80;
        luck = 80;
        defense = 80;
        resistance = 80;
        movementInitial = 40;
        movement = movementInitial;
        vision = 6;
        unitType = UnitType.Cavalry;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 95;
        strengthGrowthRate = 65;
        magicGrowthRate = 0;
        skillGrowthRate = 55;
        speedGrowthRate = 55;
        luckGrowthRate = 50;
        defenseGrowthRate = 65;
        resistanceGrowthRate = 25;
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        if (race == UnitRace.Elf) {
            up1 = setup("/LightUnits/Elf_Knight/Elf_Knight_Up_1");
            up2 = setup("/LightUnits/Elf_Knight/Elf_Knight_Up_2");
            down1 = setup("/LightUnits/Elf_Knight/Elf_Knight_Down_1");
            down2 = setup("/LightUnits/Elf_Knight/Elf_Knight_Down_2");
            left1 = setup("/LightUnits/Elf_Knight/Elf_Knight_Left_1");
            left2 = setup("/LightUnits/Elf_Knight/Elf_Knight_Left_2");
            right1 = setup("/LightUnits/Elf_Knight/Elf_Knight_Right_1");
            right2 = setup("/LightUnits/Elf_Knight/Elf_Knight_Right_2");
            default1 = setup("/LightUnits/Elf_Knight/Elf_Knight_Default_1");
            default2 = setup("/LightUnits/Elf_Knight/Elf_Knight_Default_2");
            portrait = setupPortrait("/LightUnits/Elf_Knight/Elf_Knight_Portrait");
        }
        else {
            up1 = setup("/LightUnits/Orc_Knight/Orc_Knight_Up_1");
            up2 = setup("/LightUnits/Orc_Knight/Orc_Knight_Up_2");
            down1 = setup("/LightUnits/Orc_Knight/Orc_Knight_Down_1");
            down2 = setup("/LightUnits/Orc_Knight/Orc_Knight_Down_2");
            left1 = setup("/LightUnits/Orc_Knight/Orc_Knight_Left_1");
            left2 = setup("/LightUnits/Orc_Knight/Orc_Knight_Left_2");
            right1 = setup("/LightUnits/Orc_Knight/Orc_Knight_Right_1");
            right2 = setup("/LightUnits/Orc_Knight/Orc_Knight_Right_2");
            default1 = setup("/LightUnits/Orc_Knight/Orc_Knight_Default_1");
            default2 = setup("/LightUnits/Orc_Knight/Orc_Knight_Default_2");
            portrait = setupPortrait("/LightUnits/Orc_Knight/Orc_Knight_Portrait");
        }
    }
}
