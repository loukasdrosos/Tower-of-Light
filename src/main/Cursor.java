package main;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cursor {

    private int x, y; // x and y position on map
    private int movement = 2; // Sprite Tile Movement
    private String direction = "none";
    private BufferedImage sprite;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private boolean collisionOn;

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

    public void loadImage() {
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream("/Cursor/Cursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {

           // if (keyH.UnitSelected() == false) {
                if (keyH.isCursorUpPressed() == true) {
                    moveUp(movement);
                } else if (keyH.isCursorDownPressed() == true) {
                    moveDown(movement);
                } else if (keyH.isCursorLeftPressed() == true) {
                    moveLeft(movement);
                } else if (keyH.isCursorRightPressed() == true) {
                    moveRight(movement);
                }
          //  }
        /*    else {
                if (keyH.CursorUpPressed == true) {
                    this.direction = "up";
                } else if (keyH.CursorDownPressed == true) {
                    this.direction = "down";
                } else if (keyH.CursorLeftPressed == true) {
                    this.direction = "left";
                } else if (keyH.CursorRightPressed == true) {
                    this.direction = "right";
                }

                // Check tile collision
                setCollision(false);
                gp.cChecker.checkCursorTile(this);

                // If collision is false, cursor can move
                if (getCollision() == false) {
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
                }
            } */

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

    public void draw (Graphics2D g2) {
        BufferedImage image = null;

            if (keyH.UnitSelected() == false) {
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

    public boolean getCollision () {
        return collisionOn;
    }

    public void setCollision (boolean x) {
        this.collisionOn = x;
    }
}
