package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;
import main.UtilityTool;

import java.util.ArrayList;
import java.util.List;

public class Necromancer extends ChaosUnit{
    public Necromancer(GamePanel gp, int startCol, int startRow) {
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

    @Override
    public void dropItem() {
        Item item = new SilverShield();

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
                gp.ui.addLogMessage("Nuibaba: Ah, lord Jedah forgive me...");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Alm: Jedah? Grima's right hand? He is also here?");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Nuibaba: Of course, he and the young princess are waiting for you");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Alm: Princess? Celica? Is she alive?");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Nuibaba: She is, but not for long");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Alm: Jedah, Grima, you will pay for what you've done!");
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
