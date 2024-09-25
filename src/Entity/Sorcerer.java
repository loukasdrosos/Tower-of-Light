package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class Sorcerer extends ChaosUnit{
    public Sorcerer(GamePanel gp, int startCol, int startRow) {
        super(gp);
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
        name = "Jedah";
        className = "Sorcerer";
        level = 21;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 60;
        strength = 0;
        magic = 32;
        skill = 25;
        speed = 20;
        luck = 0;
        defense = 16;
        resistance = 23;
        movementInitial = 3;
        movement = movementInitial;
        race = UnitRace.Elf;
        attackSpell = new Goetia();
        trinket = new Dracoshield();
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"The right hand", "of Grima who wants", "to rule over", "the new world."};
    }

    @Override
    public void dropItem() {
        Item item = new Dracoshield();

        if (item != null) {
            gp.tileM.addItems(item, col, row);
        }
    }

    @Override
    public void Defeated() {
        if (HP <= 0) {
            List<Runnable> tasks = new ArrayList<>();
            int delay = 300;  //300 ms delay between each message and sound effect

            tasks.add(() -> {
                gp.ui.addLogMessage("");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Jedah: God Grima...forgive me");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Jedah: Defeat these fools and bring Chaos to the world");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Alm: This is your end Jedah");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Alm: This is for hurting my loved ones");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Jedah: Go and find the princess below HAHAHAHAH");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Alm: Herald of Chaos...you will be defeated");
            });

            // Execute the tasks one by one with a delay
            executeWithDelay(tasks, delay);

            if (name != null) {
                gp.ui.addLogMessage(name + " is defeated");
            } else {
                gp.ui.addLogMessage(String.valueOf(getRace()) + " " + className + " is defeated");
            }
            dropItem();
            gp.ChaosUnits.remove(this);
            gp.tileM.loadStairs();
        }
    }



    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Sorcerer/Sorcerer_Up_1");
        up2 = setup("/ChaosUnits/Sorcerer/Sorcerer_Up_2");
        down1 = setup("/ChaosUnits/Sorcerer/Sorcerer_Down_1");
        down2 = setup("/ChaosUnits/Sorcerer/Sorcerer_Down_2");
        left1 = setup("/ChaosUnits/Sorcerer/Sorcerer_Left_1");
        left2 = setup("/ChaosUnits/Sorcerer/Sorcerer_Left_2");
        right1 = setup("/ChaosUnits/Sorcerer/Sorcerer_Right_1");
        right2 = setup("/ChaosUnits/Sorcerer/Sorcerer_Right_2");
        default1 = setup("/ChaosUnits/Sorcerer/Sorcerer_Default_1");
        default2 = setup("/ChaosUnits/Sorcerer/Sorcerer_Default_2");
        portrait = setupPortrait("/ChaosUnits/Sorcerer/Sorcerer_Portrait");
    }
}
