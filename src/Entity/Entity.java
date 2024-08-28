package Entity;

import main.Cursor;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    private int x, y; // x and y position on map

    public BufferedImage default1, default2, up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "none";
    public int movement = 2; // Sprite Tile Movement
    public int spriteCounter = 0;
    public int spriteNum = 1;
    private boolean collisionOn;

    GamePanel gp;

    KeyHandler keyH;
    private Cursor cursor;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getCollision () {
        return collisionOn;
    }

    public void setCollision (boolean x) {
        this.collisionOn = x;
    }

    //Images of a unit's animations
    public void loadImage() {
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
            default1 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
            default2 = ImageIO.read(getClass().getResourceAsStream("/LightUnits/Error_Figure.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveUp (int movement) {
        this.y -= movement;
    }

    public void moveDown (int movement) {
        this.y += movement;
    }

    public void moveLeft (int movement) {
        this.x -= movement;
    }

    public void moveRight (int movement) {
        this.x += movement;
    }

    public void update() {
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




