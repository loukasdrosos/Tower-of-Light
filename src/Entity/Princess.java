package Entity;

import Item.*;
import Spells.*;
import main.GamePanel;
import main.KeyHandler;

import java.util.ArrayList;
import java.util.List;

public class Princess extends LightUnit{
    public Princess (GamePanel gp, KeyHandler keyH, String name, UnitRace race, int startCol, int startRow) {
        super(gp, keyH);
        this.col =  startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX();          // Calculate initial x position based on column
        y = getY();          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        this.name = name;
        this.race = race;
        trinket = new MageRing();
        potion = new Elixir();
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
        className = "Princess";
        deathQuote = "Everyone... protect Alm...";
        finalMapQuote = "It's our world, and we'll be the ones to shape it";
        level = maxLevel;
        if (level > maxLevel) {
            level = maxLevel;
        }
        exp = 0;
        maxHP = 40;
        strength = 0;
        magic = 29;
        skill = 25;
        speed = 27;
        luck = 0;
        defense = 17;
        resistance = 22;
        movement = 3;
        vision = 6;
        unitType = UnitType.Infantry;
        attackType = AttackType.Magical;
        attackSpell = new Ragnarok();
        healingSpell = new HolyBlessing();
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"The princess of", "Valentia and Alm's", "wife. Hopes to", "bring peace to", "this world."} ;
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/LightUnits/Princess/Human Princess_Up_1");
        up2 = setup("/LightUnits/Princess/Human Princess_Up_2");
        down1 = setup("/LightUnits/Princess/Human Princess_Down_1");
        down2 = setup("/LightUnits/Princess/Human Princess_Down_2");
        left1 = setup("/LightUnits/Princess/Human Princess_Left_1");
        left2 = setup("/LightUnits/Princess/Human Princess_Left_2");
        right1 = setup("/LightUnits/Princess/Human Princess_Right_1");
        right2 = setup("/LightUnits/Princess/Human Princess_Right_2");
        default1 = setup("/LightUnits/Princess/Human Princess_Default_1");
        default2 = setup("/LightUnits/Princess/Human Princess_Default_2");
        portrait = setupPortrait("/LightUnits/Princess/Human Princess_Portrait");
    }

    @Override
    public void Defeated() {
        if (HP <= 0) {
            List<Runnable> tasks = new ArrayList<>();
            int delay = 1000;  //1000 ms delay between each task

            if (deathQuote != null) {
                tasks.add(() -> {
                    gp.ui.addLogMessage(name + ": " + deathQuote);
                    gp.playSE(18);
                    gp.ui.addLogMessage(name + " is defeated");
                    if (gp.selectedUnit != null && gp.selectedUnit == this) {
                        gp.selectedUnit = null;
                    }
                    gp.simLightUnits.remove(this);
                });
            }
            tasks.add(() -> {
                gp.playMusic(1);
                gp.gameState = gp.gameOverState;
            });

            // Execute the tasks one by one with a delay
            executeWithDelay(tasks, delay);
        }
    }
}
