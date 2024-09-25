package Entity;

import Item.Dracoshield;
import Spells.Expiration;
import Spells.Goetia;
import main.GamePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChaosGod extends ChaosUnit{
    public ChaosGod(GamePanel gp, int startCol, int startRow) {
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
        name = "Grima";
        className = "Chaos God";
        level = maxLevel;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 100;
        strength = 0;
        magic = 35;
        skill = 30;
        speed = 28;
        luck = 0;
        defense = 33;
        resistance = 33;
        movement = 3;
        race = UnitRace.Dragon;
        attackSpell = new Expiration();
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"The Chaos God who", "wants to destroy", "every trace of", "light from the world."};
    }

    @Override
    public void Defeated() {
        if (HP <= 0) {
            Graphics 2D g2;

            List<Runnable> tasks = new ArrayList<>();
            int delay = 700;  //700 ms delay between each task

            tasks.add(() -> {
                gp.stopMusic();
                gp.playMusic(10);
                gp.ui.drawFinale();
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Grima: NOOOOO!");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Alm: This is the end Grima!");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Alm: This time you will not return");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Grima: Even if it's not me, someone else will take my place");
            });

            tasks.add(() -> {
                gp.ui.addLogMessage("Alm: And we will be there to stop him!");
            });

            tasks.add(() -> {
                if (name != null) {
                    gp.ui.addLogMessage(name + " is defeated");
                } else {
                    gp.ui.addLogMessage(String.valueOf(getRace()) + " " + className + " is defeated");
                }
            });

            tasks.add(() -> {
                gp.ChaosUnits.clear();
                gp.gameState = gp.creditsState;
            });

            // Execute the tasks one by one with a delay
            executeWithDelay(tasks, delay);

        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Chaos_God/Chaos_God_Up_1");
        up2 = setup("/ChaosUnits/Chaos_God/Chaos_God_Up_2");
        down1 = setup("/ChaosUnits/Chaos_God/Chaos_God_Down_1");
        down2 = setup("/ChaosUnits/Chaos_God/Chaos_God_Down_2");
        left1 = setup("/ChaosUnits/Chaos_God/Chaos_God_Left_1");
        left2 = setup("/ChaosUnits/Chaos_God/Chaos_God_Left_2");
        right1 = setup("/ChaosUnits/Chaos_God/Chaos_God_Right_1");
        right2 = setup("/ChaosUnits/Chaos_God/Chaos_God_Right_2");
        default1 = setup("/ChaosUnits/Chaos_God/Chaos_God_Default_1");
        default2 = setup("/ChaosUnits/Chaos_God/Chaos_God_Default_2");
        portrait = setupPortrait("/ChaosUnits/Chaos_God/Chaos_God_Portrait");
    }
}
