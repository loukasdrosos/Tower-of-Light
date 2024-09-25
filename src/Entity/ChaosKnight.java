package Entity;

import Item.*;
import main.GamePanel;
import main.UtilityTool;

public class ChaosKnight extends ChaosUnit{
    public ChaosKnight(GamePanel gp, int startCol, int startRow) {
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
        className = "Chaos Knight";
        level = gp.aSetter.getEnemyLevel();
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 25;
        strength = 9;
        magic = 0;
        skill = 7;
        speed = 8;
        luck = 0;
        defense = 10;
        resistance = 6;
        movement = 4;
        setupGrowthRates();
        setStats();
        randomizeRace();
        randomizeItems();
        unitType = UnitType.Mounted;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"A soldier and his", "horse resurrected", "to fight for", "the Chaos army."};
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 85;
        strengthGrowthRate = 45;
        magicGrowthRate = 0;
        skillGrowthRate = 40;
        speedGrowthRate = 40;
        luckGrowthRate = 45;
        defenseGrowthRate = 40;
        resistanceGrowthRate = 30;
    }

    @Override
    public void randomizeRace() {
        UtilityTool uTool = new UtilityTool();
        int randomNumber = uTool.getRandomNumber();

        // 25% chance for Human
        if (randomNumber <= 25) {
            race = UnitRace.Human;
        }
        // 25% chance for Orc
        else if (randomNumber > 25 && randomNumber <= 50) {
            race = UnitRace.Orc;
        }
        // 25% chance for Elf
        else if (randomNumber > 50 && randomNumber <= 75) {
            race = UnitRace.Elf;
        }
        // 25% chance for Tauren
        else if (randomNumber > 75 && randomNumber <= 100) {
            race = UnitRace.Tauren;
        }
    }

    @Override
    public void randomizeItems() {
        UtilityTool uTool = new UtilityTool();

        if (level <= 12) {
            equippedWeapon = new SteelLance();
        }
        if (level > 12 && level <= 14) {
            int number = uTool.getRandomNumber();
            if (number <= 35){
                equippedWeapon = new SteelLance();
            }
            else if (number > 35 && number <= 75) {
                equippedWeapon = new KillerLance();
            }
            else if (number > 75 && number <= 85) {
                equippedWeapon = new HexlockSpear();
            }
            else if (number > 85 && number <= 100) {
                equippedWeapon = new Ridersbane();
            }
            number = uTool.getRandomNumber();
            if (number <= 25){
                trinket = new IronShield();
            }
            else if (number > 25 && number <= 50) {
                trinket = new SteelShield();
            }
            else if (number > 50 && number <= 60) {
                trinket = new HexlockShield();
            }
        }
        if (level > 14 && level <= 16) {
            int number = uTool.getRandomNumber();
            if (number <= 35){
                equippedWeapon = new SilverLance();
            }
            else if (number > 35 && number <= 65) {
                equippedWeapon = new KillerLance();
            }
            else if (number > 65 && number <= 85) {
                equippedWeapon = new Ridersbane();
            }
            else if (number > 85 && number <= 100) {
                equippedWeapon = new SlimLance();
            }
            number = uTool.getRandomNumber();
            if (number <= 25){
                trinket = new SteelShield();
            }
            else if (number > 25 && number <= 50) {
                trinket = new SilverShield();
            }
            else if (number > 50 && number <= 60) {
                trinket = new HexlockShield();
            }
        }
        if (level > 16) {
            int number = uTool.getRandomNumber();
            if (number <= 70){
                equippedWeapon = new SilverLance();
            }
            else if (number > 70 && number <= 100) {
                equippedWeapon = new Gradivus();
            }
            number = uTool.getRandomNumber();
            if (number <= 45){
                trinket = new SilverShield();
            }
            else if (number > 45 && number <= 55) {
                trinket = new DemonRing();
            }
            else if (number > 55 && number <= 75) {
                trinket = new HexlockShield();
            }
            else if (number > 75 && number <= 100) {
                trinket = new SpeedRing();
            }
        }
    }

    @Override
    public void dropItem() {
        UtilityTool uTool = new UtilityTool();
        int number = uTool.getRandomNumber();
        Item item = null;

        if (level <= 12) {
            if (number <= 10) {
                item = new SteelLance();
            } else if (number > 10 && number <= 20) {
                item = new Ridersbane();
            } else if (number > 20 && number <= 30) {
                item = new SteelShield();
            } else if (number > 30 && number <= 40) {
                item = new KillerLance();
            } else if (number > 40 && number <= 50) {
                item = new HexlockSpear();
            } else if (number > 50 && number <= 70) {
                item = new Concoction();
            }
        }
        else if (level > 12 && level <= 16) {
            if (number <= 10) {
                item = new HexlockShield();
            } else if (number > 10 && number <= 20) {
                item = new Ridersbane();
            } else if (number > 20 && number <= 30) {
                item = new SteelShield();
            } else if (number > 30 && number <= 40) {
                item = new KillerLance();
            } else if (number > 40 && number <= 50) {
                item = new HexlockSpear();
            } else if (number > 50 && number <= 70) {
                item = new Concoction();
            } else if (number > 70 && number <= 75) {
                item = new SilverLance();
            }
        }
        else if (level > 16) {
            if (number <= 10) {
                item = new Wyrmslayer();
            } else if (number > 10 && number <= 20) {
                item = new SilverShield();
            } else if (number > 20 && number <= 30) {
                item = new Ridersbane();
            } else if (number > 30 && number <= 50) {
                item = new SilverLance();
            } else if (number > 50 && number <= 65) {
                item = new Gradivus();
            } else if (number > 65 && number <= 85) {
                item = new Elixir();
            }
        }

        if (item != null) {
            gp.tileM.addItems(item, col, row);
        }
    }


    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Up_1");
        up2 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Up_2");
        down1 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Down_1");
        down2 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Down_2");
        left1 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Left_1");
        left2 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Left_2");
        right1 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Right_1");
        right2 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Right_2");
        default1 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Default_1");
        default2 = setup("/ChaosUnits/Chaos_Knight/Chaos_Knight_Default_2");
        portrait = setupPortrait("/ChaosUnits/Chaos_Knight/Chaos_Knight_Portrait");
    }
}
