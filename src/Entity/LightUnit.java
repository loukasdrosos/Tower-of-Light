package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LightUnit extends Entity{

    private boolean isSelected = false;

    public LightUnit (GamePanel gp, KeyHandler keyH, int startCol, int startRow) {
        this.gp = gp;
        this.keyH = keyH;
        this.col =  startCol;
        this.row = startRow;
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row;

        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage, LightUnit not loading properly");
        }
    }

    @Override
    public void update() {

        if (this.isSelected == true) {
            if (keyH.isUnitUpPressed() == true) {
                this.direction = "up";
            } else if (keyH.isUnitDownPressed() == true) {
                this.direction = "down";
            } else if (keyH.isUnitLeftPressed() == true) {
                this.direction = "left";
            } else if (keyH.isUnitRightPressed() == true) {
                this.direction = "right";
            } else if (keyH.isUnitUpPressed() == false && keyH.isUnitDownPressed() == false
               && keyH.isUnitLeftPressed() == false && keyH.isUnitRightPressed() == false) {
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
                    moveUp();
                    break;
                case "down" :
                    moveDown();
                    break;
                case "left" :
                    moveLeft();
                    break;
                case "right" :
                    moveRight();
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

    //Images of a unit's animations
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

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            default:
                if (spriteNum == 1) {
                    image = default1;
                }
                if (spriteNum == 2) {
                    image = default2;
                }
        }
        g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
    }

    //GETTERS && SETTERS

    public boolean getIsSelected () {
        return isSelected;
    }

    public void setIsSelected (boolean x) {
        this.isSelected = x;
    }
}