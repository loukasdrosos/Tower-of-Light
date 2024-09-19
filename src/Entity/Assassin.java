package Entity;

import Item.*;
import main.GamePanel;
import main.UtilityTool;

public class Assassin extends ChaosUnit{
    public Assassin(GamePanel gp, boolean canMove, int startCol, int startRow) {
        super(gp, canMove);
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
        className = "Assassin";
        level = 3;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 20;
        strength = 7;
        magic = 0;
        skill = 11;
        speed = 13;
        luck = 0;
        defense = 6;
        resistance = 4;
        movement = 3;
        if (!canMove) {
            movement = 0;
        }
        setupGrowthRates();
        setStats();
        randomizeRace();
        randomizeItems();
        unitType = UnitType.Infantry;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"An expert in", "the art of", "death while", "staying hidden."};
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 80;
        strengthGrowthRate = 45;
        magicGrowthRate = 0;
        skillGrowthRate = 50;
        speedGrowthRate = 50;
        luckGrowthRate = 50;
        defenseGrowthRate = 30;
        resistanceGrowthRate = 35;
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

        if (level <= 10) {
            equippedWeapon = new SteelSword();
        }
        if (level > 10 && level <= 13) {
            int number = uTool.getRandomNumber();
            if (number <= 35){
                equippedWeapon = new SteelSword();
            }
            else if (number > 35 && number <= 65) {
                equippedWeapon = new KillingEdge();
            }
            else if (number > 65 && number <= 85) {
                equippedWeapon = new Armorslayer();
            }
            else if (number > 85 && number <= 100) {
                equippedWeapon = new HeavyBlade();
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
        if (level > 13 && level <= 15) {
            int number = uTool.getRandomNumber();
            if (number <= 35){
                equippedWeapon = new SilverSword();
            }
            else if (number > 35 && number <= 65) {
                equippedWeapon = new KillingEdge();
            }
            else if (number > 65 && number <= 85) {
                equippedWeapon = new Armorslayer();
            }
            else if (number > 85 && number <= 100) {
                equippedWeapon = new HeavyBlade();
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
        if (level > 15) {
            int number = uTool.getRandomNumber();
            if (number <= 70){
                equippedWeapon = new SilverSword();
            }
            else if (number > 70 && number <= 100) {
                equippedWeapon = new Mercurius();
            }
            number = uTool.getRandomNumber();
            if (number <= 35){
                trinket = new SilverShield();
            }
            else if (number > 35 && number <= 55) {
                trinket = new SteelShield();
            }
            else if (number > 55 && number <= 65) {
                trinket = new HexlockShield();
            }
            else if (number > 65 && number <= 100) {
                trinket = new DemonRing();
            }
        }
    }

    @Override
    public void dropItem() {
        UtilityTool uTool = new UtilityTool();
        int number = uTool.getRandomNumber();
        Item item = null;

        if (level <= 11) {
            if (number <= 10) {
                item = new BlessedSword();
            } else if (number > 10 && number <= 20) {
                item = new Wyrmslayer();
            } else if (number > 20 && number <= 30) {
                item = new SteelShield();
            } else if (number > 30 && number <= 40) {
                item = new KillerLance();
            } else if (number > 40 && number <= 50) {
                item = new KillingEdge();
            } else if (number > 50 && number <= 70) {
                item = new Concoction();
            }
        }
        else if (level > 11 && level <= 15) {
            if (number <= 10) {
                item = new Armorslayer();
            } else if (number > 10 && number <= 20) {
                item = new HeavyBlade();
            } else if (number > 20 && number <= 30) {
                item = new Wyrmslayer();
            } else if (number > 30 && number <= 40) {
                item = new BlessedSword();
            } else if (number > 40 && number <= 50) {
                item = new KillingEdge();
            } else if (number > 50 && number <= 60) {
                item = new SteelShield();
            } else if (number > 60 && number <= 80) {
                item = new Concoction();
            }
        }
        else if (level > 15) {
            if (number <= 10) {
                item = new Armorslayer();
            } else if (number > 10 && number <= 20) {
                item = new SilverShield();
            } else if (number > 20 && number <= 30) {
                item = new Wyrmslayer();
            } else if (number > 30 && number <= 50) {
                item = new SilverSword();
            } else if (number > 50 && number <= 65) {
                item = new Mercurius();
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
        up1 = setup("/ChaosUnits/Assasin/Assasin_Up_1");
        up2 = setup("/ChaosUnits/Assasin/Assasin_Up_2");
        down1 = setup("/ChaosUnits/Assasin/Assasin_Down_1");
        down2 = setup("/ChaosUnits/Assasin/Assasin_Down_2");
        left1 = setup("/ChaosUnits/Assasin/Assasin_Left_1");
        left2 = setup("/ChaosUnits/Assasin/Assasin_Left_2");
        right1 = setup("/ChaosUnits/Assasin/Assasin_Right_1");
        right2 = setup("/ChaosUnits/Assasin/Assasin_Right_2");
        default1 = setup("/ChaosUnits/Assasin/Assasin_Default_1");
        default2 = setup("/ChaosUnits/Assasin/Assasin_Default_2");
        portrait = setupPortrait("/ChaosUnits/Assasin/Assasin_Portrait");
    }
}
