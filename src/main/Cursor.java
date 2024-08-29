package main;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cursor {

    private int x, y; // x and y position on map
    private int spriteSpeed = 4; // Sprite Tile Movement
    private String direction = "none";
    private BufferedImage sprite;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private boolean moving = false;
    private int pixelCounter = 0;
    private int standCounter = 0;

    GamePanel gp;
    private KeyHandler keyH;

    public Cursor (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage, Cursor not loading properly");
        }
    }

    public void setStartingPosition(int startX, int startY) {
        setX(startX);
        setY(startY);
    }

    //CURSOR MOVEMENT
    public void moveUp (int spriteSpeed) {
        this.y -= spriteSpeed;
    }

    public void moveDown (int spriteSpeed) {
        this.y += spriteSpeed;
    }

    public void moveLeft (int spriteSpeed) {
        this.x -= spriteSpeed;
    }

    public void moveRight (int spriteSpeed) {
        this.x += spriteSpeed;
    }

    public void loadImage() {
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream("/Cursor/Cursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (moving == false) {

            if (gp.selectedUnit == null) {

                if (keyH.isCursorUpPressed() == true || keyH.isCursorDownPressed() == true
                || keyH.isCursorLeftPressed() == true || keyH.isCursorRightPressed() == true) {

                    if (keyH.isCursorUpPressed() == true) {
                        moveUp(spriteSpeed);
                    } else if (keyH.isCursorDownPressed() == true) {
                        moveDown(spriteSpeed);
                    } else if (keyH.isCursorLeftPressed() == true) {
                        moveLeft(spriteSpeed);
                    } else if (keyH.isCursorRightPressed() == true) {
                        moveRight(spriteSpeed);
                    }

                    moving  = true;
                }
            }
            if (gp.selectedUnit != null) {
                if (keyH.isCursorUpPressed() == true) {
                    moveUp(spriteSpeed);
                } else if (keyH.isCursorDownPressed() == true) {
                    moveDown(spriteSpeed);
                } else if (keyH.isCursorLeftPressed() == true) {
                    moveLeft(spriteSpeed);
                } else if (keyH.isCursorRightPressed() == true) {
                    moveRight(spriteSpeed);
                }
            }
            else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }
        if (moving == true) {
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

        pixelCounter += spriteSpeed;
        if (pixelCounter == 16) {
            moving = false;
            pixelCounter = 0;
        }
    }

    public void draw (Graphics2D g2) {
        BufferedImage image = null;

            if (gp.selectedUnit == null) {
                if (spriteNum == 1) {
                    image = sprite;
                }
                if (spriteNum == 2) {
                    image = null;
                }
            } else  {
                   image = sprite;
            }
       g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
    }

    //GETTERS && SETTERS

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
}
