package Entity;

import Item.*;
import main.GamePanel;
import main.UtilityTool;

public class ChaosPaladin extends ChaosUnit{
    public ChaosPaladin(GamePanel gp, boolean canMove, boolean boss, int startCol, int startRow) {
        super(gp, canMove);
        this.col = startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX();          // Calculate initial x position based on column
        y = getY();          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        this.boss = boss;
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
        className = "Chaos Paladin";
        level = 15;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 28;
        strength = 12;
        magic = 0;
        skill = 7;
        speed = 4;
        luck = 0;
        defense = 12;
        resistance = 3;
        movement = 3;
        if (!canMove) {
            movement = 0;
        }
        setupGrowthRates();
        setStats();
        if (boss) {
            level = 17;
            maxHP = 50;
            strength = 25;
            magic = 0;
            skill = 15;
            speed = 12;
            luck = 0;
            defense = 25;
            resistance = 10;
        }
        randomizeRace();
        randomizeItems();
        unitType = UnitType.Armored;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"The corpse of a", "fallen paladin.", "Has remarkable", "strength and defense."};
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 100;
        strengthGrowthRate = 50;
        magicGrowthRate = 0;
        skillGrowthRate = 35;
        speedGrowthRate = 30;
        luckGrowthRate = 40;
        defenseGrowthRate = 45;
        resistanceGrowthRate = 25;
    }

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

    public void randomizeItems() {
        if (boss) {
            equippedWeapon = new SilverLance();
            trinket = new Dracoshield();
        }
        else {
            UtilityTool uTool = new UtilityTool();

            if (level <= 16) {
                equippedWeapon = new SteelLance();
            }
            if (level > 16 && level <= 20) {
                int number = uTool.getRandomNumber();
                if (number <= 25) {
                    equippedWeapon = new SteelLance();
                } else if (number > 25 && number <= 55) {
                    equippedWeapon = new KillerLance();
                } else if (number > 55 && number <= 70) {
                    equippedWeapon = new HexlockSpear();
                }else if (number > 70 && number <= 90) {
                    equippedWeapon = new SilverLance();
                } else if (number > 90 && number <= 100) {
                    equippedWeapon = new Ridersbane();
                }
                number = uTool.getRandomNumber();
                if (number <= 50) {
                    trinket = new SteelShield();
                } else if (number > 50 && number <= 70) {
                    trinket = new HexlockShield();
                }
            }
            if (level > 20 && level <= 24) {
                int number = uTool.getRandomNumber();
                if (number <= 45) {
                    equippedWeapon = new SilverLance();
                } else if (number > 45 && number <= 85) {
                    equippedWeapon = new KillerLance();
                } else if (number > 85 && number <= 100) {
                    equippedWeapon = new Ridersbane();
                }
                number = uTool.getRandomNumber();
                if (number <= 40) {
                    trinket = new SilverShield();
                } else if (number > 40 && number <= 70) {
                    trinket = new HexlockShield();
                }
            }
            if (level > 24) {
                int number = uTool.getRandomNumber();
                if (number <= 70) {
                    equippedWeapon = new SilverLance();
                } else if (number > 70 && number <= 100) {
                    equippedWeapon = new Gradivus();
                }
                number = uTool.getRandomNumber();
                if (number <= 10) {
                    trinket = new Dracoshield();
                } else if (number > 10 && number <= 60) {
                    trinket = new SilverShield();
                } else if (number > 60 && number <= 100) {
                    trinket = new HexlockShield();
                }
            }
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Up_1");
        up2 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Up_2");
        down1 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Down_1");
        down2 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Down_2");
        left1 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Left_1");
        left2 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Left_2");
        right1 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Right_1");
        right2 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Right_2");
        default1 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Default_1");
        default2 = setup("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Default_2");
        portrait = setupPortrait("/ChaosUnits/Chaos_Paladin/Chaos_Paladin_Portrait");
    }

}
