package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    protected int x, y; // x and y position on map
    protected int col, row, preCol, preRow;

    protected BufferedImage default1, default2, up1, up2, down1, down2, left1, left2, right1, right2;
    protected String direction = "none";
    protected int spriteSpeed = 2; // Sprite Tile Movement
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    protected boolean wait;
    protected boolean isMoving;

    GamePanel gp;
    KeyHandler keyH;

    //Images of a unit's animations, all images are set to error figure if something doesn't load properly
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

    //UNIT MOVEMENT
    public void moveUp () {
        this.row -= 1;
        y = getY(row);
    }

    public void moveDown () {
        this.row += 1;
        y = getY(row);
    }

    public void moveLeft () {
        this.col -= 1;
        x = getX(col);
    }

    public void moveRight () {
        this.col += 1;
        x = getX(col);
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

    //GETTERS && SETTERS

    public int getX(int col) {
        return col * gp.getTileSize();
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY(int row) {
        return row * gp.getTileSize();
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPreCol() {
        return preCol;
    }

    public void setPreCol(int preCol) {
        this.preCol = preCol;
    }

    public int getPreRow() {
        return preRow;
    }

    public void setPreRow(int preRow) {
        this.preRow = preRow;
    }
}




