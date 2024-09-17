package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;

public class Necromancer extends ChaosUnit{
    public Necromancer(GamePanel gp, boolean canMove, int startCol, int startRow) {
        super(gp, canMove);
        this.col = startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX();          // Calculate initial x position based on column
        y = getY();          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        boss = true;
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
        name = "Nuibaba";
        className = "Necromancer";
        level = 9;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 36;
        strength = 0;
        magic = 14;
        skill = 11;
        speed = 10;
        luck = 0;
        defense = 10;
        resistance = 12;
        movement = 3;
        if (!canMove) {
            movement = 0;
        }
        race = UnitRace.Elf;
        attackSpell = new Fenrir();
        trinket = new SilverShield();
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"A powerful dark", "mage and a high", "ranking member of", "Grima's clergy."};
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Necromancer/Necromancer_Up_1");
        up2 = setup("/ChaosUnits/Necromancer/Necromancer_Up_2");
        down1 = setup("/ChaosUnits/Necromancer/Necromancer_Down_1");
        down2 = setup("/ChaosUnits/Necromancer/Necromancer_Down_2");
        left1 = setup("/ChaosUnits/Necromancer/Necromancer_Left_1");
        left2 = setup("/ChaosUnits/Necromancer/Necromancer_Left_2");
        right1 = setup("/ChaosUnits/Necromancer/Necromancer_Right_1");
        right2 = setup("/ChaosUnits/Necromancer/Necromancer_Right_2");
        default1 = setup("/ChaosUnits/Necromancer/Necromancer_Default_1");
        default2 = setup("/ChaosUnits/Necromancer/Necromancer_Default_2");
        portrait = setupPortrait("/ChaosUnits/Necromancer/Necromancer_Portrait");
    }
}
