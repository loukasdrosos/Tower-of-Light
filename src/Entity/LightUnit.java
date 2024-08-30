package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LightUnit extends Entity{

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

    public void move() {
        // Capture the intended move direction
        if (keyH.isUpPressed() == true) {
            direction = "up";
        } else if (keyH.isDownPressed() == true) {
            direction = "down";
        } else if (keyH.isLeftPressed() == true) {
            direction = "left";
        } else if (keyH.isRightPressed() == true) {
            direction = "right";
        } else if (keyH.isUpPressed() == false && keyH.isDownPressed() == false
                && keyH.isLeftPressed() == false && keyH.isRightPressed() == false) {
            direction = "none"; // If no key is pressed, stop movement
        }

        // Calculate target position based on direction
        int targetCol = col;
        int targetRow = row;

        switch (direction) {
            case "up":
                targetRow = row - 1;
                break;
            case "down":
                targetRow = row + 1;
                break;
            case "left":
                targetCol = col - 1;
                break;
            case "right":
                targetCol = col + 1;
                break;
        }

        // Check if the move is allowed and update position accordingly
        if (allowedMove(targetCol, targetRow) == true && gp.cChecker.validTile(targetCol, targetRow) == true) {
            col = targetCol;
            row = targetRow;
            updatePosition();
        }
    }

    @Override
    public void update() {
        if (wait == false) {
            if (isSelected == true && isMoving == true) {
                moveDelayCounter++;  // Increment the delay counter
                if (moveDelayCounter >= moveDelayThreshold) {  // Only move the unit when the delay counter reaches the threshold
                    move(); // Move the selected unit
                    moveDelayCounter = 0; // Reset the delay counter after moving
                }
            }
        }

        // Update sprite animation
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
}