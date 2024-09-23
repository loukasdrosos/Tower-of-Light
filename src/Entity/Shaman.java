package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;
import main.UtilityTool;

public class Shaman extends ChaosUnit{
    public Shaman(GamePanel gp,int startCol, int startRow) {
        super(gp);
        this.col = startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX();          // Calculate initial x position based on column
        y = getY();          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        setupStats();
        // Load unit's images for animations
        try {
            loadImage();
        } catch (Exception e) {
            System.out.println("Exception loadImage, ChaosUnit not loading properly");
        }
    }

    // Method to set up the unit's stats
    @Override
    public void setupStats() {
        className = "Shaman";
        level = 3;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 23;
        strength = 0;
        magic = 6;
        skill = 4;
        speed = 4;
        luck = 0;
        defense = 7;
        resistance = 7;
        movement = 3;
        setupGrowthRates();
        setStats();
        randomizeRace();
        randomizeItems();
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"A mage that", "only uses dark", "magic and wants", "to spread Chaos."};
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 80;
        strengthGrowthRate = 0;
        magicGrowthRate = 50;
        skillGrowthRate = 45;
        speedGrowthRate = 40;
        luckGrowthRate = 30;
        defenseGrowthRate = 30;
        resistanceGrowthRate = 45;
    }

    @Override
    public void randomizeRace() {
        UtilityTool uTool = new UtilityTool();
        int randomNumber = uTool.getRandomNumber();

        // 50% chance for Human
        if (randomNumber <= 50) {
            race = UnitRace.Human;
        }
        // 50% chance for Elf
        else if (randomNumber > 50 && randomNumber <= 100) {
            race = UnitRace.Elf;
        }
    }

    @Override
    public void randomizeItems() {
        UtilityTool uTool = new UtilityTool();
        int number = uTool.getRandomNumber();

        if (level <= 7) {
            attackSpell = new Flux();
        }
        if (level > 7 && level <= 12) {
            attackSpell = new Miasma();
            if (number <= 35){
                trinket = new IronShield();
            }
            else if (number > 35 && number <= 45) {
                trinket = new SteelShield();
            }
        }
        if (level > 12) {
            attackSpell = new Nosferatu();
            if (number <= 15){
                trinket = new IronShield();
            }
            else if (number > 15 && number <= 30) {
                trinket = new SpeedRing();
            }
            else if (number > 30 && number <= 55) {
                trinket = new SteelShield();
            }
            else if (number > 55 && number <= 60) {
                trinket = new MageRing();
            }
        }
    }

    @Override
    public void dropItem() {
        UtilityTool uTool = new UtilityTool();
        int number = uTool.getRandomNumber();
        Item item = null;

        if (level <= 7) {
            if (number <= 10) {
                item = new Torch();
            } else if (number > 10 && number <= 20) {
                item = new SteelSword();
            } else if (number > 20 && number <= 30) {
                item = new Vulnerary();
            } else if (number > 30 && number <= 40) {
                item = new SteelLance();
            } else if (number > 40 && number <= 50) {
                item = new SlimLance();
            } else if (number > 50 && number <= 60) {
                item = new IronShield();
            }
        }
        else if (level > 7 && level <= 12) {
            if (number <= 10) {
                item = new SteelShield();
            } else if (number > 10 && number <= 20) {
                item = new Torch();
            } else if (number > 20 && number <= 30) {
                item = new SteelLance();
            } else if (number > 30 && number <= 40) {
                item = new SteelShield();
            } else if (number > 40 && number <= 50) {
                item = new SteelSword();
            } else if (number > 50 && number <= 70) {
                item = new Concoction();
            }
        }
        else if (level > 12) {
            if (number <= 10) {
                item = new Torch();
            } else if (number > 10 && number <= 20) {
                item = new BlessedRing();
            } else if (number > 20 && number <= 30) {
                item = new MageRing();
            } else if (number > 30 && number <= 40) {
                item = new BlessedSword();
            } else if (number > 40 && number <= 50) {
                item = new SpeedRing();
            } else if (number > 50 && number <= 70) {
                item = new Concoction();
            }
        }

        if (item != null) {
            gp.tileM.addItems(item, col, row);
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Shaman/Shaman_Up_1");
        up2 = setup("/ChaosUnits/Shaman/Shaman_Up_2");
        down1 = setup("/ChaosUnits/Shaman/Shaman_Down_1");
        down2 = setup("/ChaosUnits/Shaman/Shaman_Down_2");
        left1 = setup("/ChaosUnits/Shaman/Shaman_Left_1");
        left2 = setup("/ChaosUnits/Shaman/Shaman_Left_2");
        right1 = setup("/ChaosUnits/Shaman/Shaman_Right_1");
        right2 = setup("/ChaosUnits/Shaman/Shaman_Right_2");
        default1 = setup("/ChaosUnits/Shaman/Shaman_Default_1");
        default2 = setup("/ChaosUnits/Shaman/Shaman_Default_2");
        portrait = setupPortrait("/ChaosUnits/Shaman/Shaman_Portrait");
    }
}
