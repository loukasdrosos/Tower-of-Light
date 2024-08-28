package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.io.IOException;

public class LightUnit extends Entity{

    private boolean selected = false;

    public LightUnit (GamePanel gp, KeyHandler keyH, int startX, int startY) {
        this.gp = gp;
        this.keyH = keyH;
        setX(startX);
        setY(startY);

        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage in LightUnit not working properly");
        }
    }

    public boolean getSelected () {
        return selected;
    }

    public void setSelected (boolean x) {
        this.selected = x;
    }

    @Override
    public void update() {

        if (this.selected == true) {
            if (keyH.UnitUpPressed == true) {
                this.direction = "up";
            } else if (keyH.UnitDownPressed == true) {
                this.direction = "down";
            } else if (keyH.UnitLeftPressed == true) {
                this.direction = "left";
            } else if (keyH.UnitRightPressed == true) {
                this.direction = "right";
            } else if (keyH.UnitUpPressed == false && keyH.UnitDownPressed == false
               && keyH.UnitLeftPressed == false && keyH.UnitRightPressed == false) {
                this.direction = "none";
            }
        }

       /* // Check tile collision
        setCollision(false);
        gp.cChecker.checkEntityTile(this);

        // If collision is false, unit can move
        if (getCollision() == false) { */
            switch (direction) {
                case "up" :
                    moveUp(movement);
                    break;
                case "down" :
                    moveDown(movement);
                    break;
                case "left" :
                    moveLeft(movement);
                    break;
                case "right" :
                    moveRight(movement);
                    break;
            }
     // }

        spriteCounter++;
        if (spriteCounter > 20) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    @Override
    public void loadImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Right_2.png"));
            default1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Default_1.png"));
            default2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Human_Prince_Default_2.png"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}