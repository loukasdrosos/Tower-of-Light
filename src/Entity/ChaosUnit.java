package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ChaosUnit extends Entity {

    protected int moveDelayThreshold = 7; // Frame threshold
    protected int moveSpeed = 8; // Speed at which the unit moves between tiles

    public ChaosUnit(GamePanel gp, KeyHandler keyH, int startCol, int startRow) {
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
            System.out.println("Exception loadImage, ChaosUnit not loading properly");
        }
    }

    @Override
    public void startTurn() {
        wait = false;
    }

    @Override
    public void endTurn() {
        wait = true;
        preCol = col;
        preRow = row;
        direction = "none";
    }

    public void move() {
        if (!wait) {
            // Increment the delay counter
            moveDelayCounter++;

            // Only move if the counter reaches the threshold
            if (moveDelayCounter >= moveDelayThreshold) {
                moveDelayCounter = 0; // Reset the counter after moving

                // Determine the direction to move
                if (gp.cChecker.isWithinMap(col, row - 1) && gp.cChecker.validTile(col, row - 1)) {
                    direction = "up";
                } else if (gp.cChecker.isWithinMap(col + 1, row) && gp.cChecker.validTile(col + 1, row)) {
                    direction = "right";
                } else if (gp.cChecker.isWithinMap(col, row + 1) && gp.cChecker.validTile(col, row + 1)) {
                    direction = "down";
                } else if (gp.cChecker.isWithinMap(col - 1, row) && gp.cChecker.validTile(col - 1, row)) {
                    direction = "left";
                }

                // Determine target position based on direction
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

                // Update the ChaosUnit's position gradually
                if (targetCol != col || targetRow != row) {
                    int targetX = getX(targetCol);
                    int targetY = getY(targetRow);

                    if (x < targetX) {
                        x += moveSpeed;
                        if (x > targetX) x = targetX;
                    } else if (x > targetX) {
                        x -= moveSpeed;
                        if (x < targetX) x = targetX;
                    }

                    if (y < targetY) {
                        y += moveSpeed;
                        if (y > targetY) y = targetY;
                    } else if (y > targetY) {
                        y -= moveSpeed;
                        if (y < targetY) y = targetY;
                    }

                    // Check if the unit has reached the new tile
                    if (x == targetX && y == targetY) {
                        col = targetCol;
                        row = targetRow;
                        updatePosition(); // Update the col and row based on the new position
                        endTurn();
                    }
                }
            }
        }
    }

    @Override
    public void update() {

        if (gp.TurnM.getPlayerPhase() == false) {
            move();
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
