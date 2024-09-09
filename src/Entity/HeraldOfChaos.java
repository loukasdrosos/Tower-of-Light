package Entity;

import Item.Weapon;
import main.GamePanel;

public class HeraldOfChaos extends ChaosUnit{

    public HeraldOfChaos(GamePanel gp, boolean isBoss, boolean canMove, int startCol, int startRow, Weapon weapon) {
        super(gp, isBoss, canMove);
        this.col = startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX(col);          // Calculate initial x position based on column
        y = getY(row);          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        setupStats();
        equippedWeapon = weapon;
        calculateCombatStats();

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
        level = 1;
        maxHP = 30;
        strength = 8;
        magic = 5;
        skill = 7;
        speed = 8;
        luck = 0;
        defense = 6;
        resistance = 4;
        movement = 3;
        type = UnitType.Human;
        boostStatsForClasses();
        HP = maxHP;
        armored = false;
        mounted = false;
        physical = true;
        magical = false;
        description = new String[]{"Princess of Valentia", "and Alm's wife.", "Controlled by the", "Chaos God, she has", "become the Herald", "of Chaos."};
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
