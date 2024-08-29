package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ChaosUnit extends Entity {

    public ChaosUnit(GamePanel gp, KeyHandler keyH, int startX, int startY) {
        this.gp = gp;
        this.keyH = keyH;
        setX(startX);
        setY(startY);

        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage, ChaosUnit not loading properly");
        }
    }

    //Images of a unit's animations
    public void loadImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Right_2.png"));
            default1 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Default_1.png"));
            default2 = ImageIO.read(getClass().getResourceAsStream("/ChaosUnits/Human_Herald_of_Chaos_Default_2.png"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
