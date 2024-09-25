package Entity;

import Item.*;
import main.GamePanel;

public class HeraldOfChaos extends ChaosUnit{

    public HeraldOfChaos(GamePanel gp, int startCol, int startRow) {
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
        name = "Celica";
        className = "Herald of Chaos";
        level = maxLevel;
        if (level > maxLevel) {
            level = maxLevel;
        }
        maxHP = 70;
        strength = 30;
        magic = 29;
        skill = 25;
        speed = 23;
        luck = 0;
        defense = 25;
        resistance = 30;
        movement = 3;
        equippedWeapon = new EdgeOfChaos();
        trinket =  new DemonRing();
        race = UnitRace.Human;
        unitType = UnitType.Infantry;
        attackType = AttackType.Physical;
        boostStatsForClasses();
        HP = maxHP;
        calculateCombatStats();
        description = new String[]{"Princess of Valentia", "and Alm's wife.", "Controlled by the", "Chaos God, she has", "become the Herald", "of Chaos."};
    }

    @Override
    public void dropItem() {
        Item item = new EdgeOfChaos();

        if (item != null) {
            gp.tileM.addItems(item, col, row);
        }
    }

    @Override
    public void Defeated() {
        if (HP <= 0) {
            if (name != null) {
                gp.ui.addLogMessage(name + " is defeated");
            } else {
                gp.ui.addLogMessage(String.valueOf(getRace()) + " " + className + " is defeated");
            }
            dropItem();
            gp.aSetter.addPrincess(col, row);
            gp.ChaosUnits.remove(this);
            gp.tileM.loadStairs();
            gp.ui.addLogMessage("");
            gp.ui.addLogMessage("Alm: Celica, are you okay?");
            gp.ui.addLogMessage("Celica: Alm, i am so sorry..");
            gp.ui.addLogMessage("Alm: Don't worry, it's not your fault");
            gp.ui.addLogMessage("Alm: Let's go and defeat Grima together!");
            gp.ui.addLogMessage("Celica: Let's bring light back to the world!");
        }
            }


    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Up_1");
        up2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Up_2");
        down1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Down_1");
        down2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Down_2");
        left1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Left_1");
        left2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Left_2");
        right1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Right_1");
        right2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Right_2");
        default1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Default_1");
        default2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Default_2");
        portrait = setupPortrait("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Portrait");
    }
}
