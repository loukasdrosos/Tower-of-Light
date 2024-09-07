package Entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    // Variables representing the unit's position on the map in pixels and tiles
    protected int x, y;  // x and y position on the map in pixels
    protected int col, row, preCol, preRow; // Current and previous tile positions (columns and rows)

    protected BufferedImage default1, default2, up1, up2, down1, down2, left1, left2, right1, right2, portrait; // BufferedImages for different animation frames and directions
    protected int spriteCounter = 0;  // Counter to control sprite animation timing
    protected int spriteNum = 1;      // Variable to track which sprite frame to display

    // Movement-related variables
    protected String direction = "none"; // Direction the unit is currently moving in
    protected int moveDelayCounter = 0;  // Counter for delaying movement, allowing smooth movement between tiles
    protected boolean wait = false; // Variable that controls if unit can take action or not

    // Unit stats
    protected String name; // Unit's name
    protected String className; // Unit's class
    protected String[] description; // Information about the unit
    protected int level; // Unit's level
    protected int maxLevel = 20; // Unit's max level it can reach
    protected int exp; // Unit's experience points
    protected int maxExp = 100;
    protected int HP; // Unit's current Health Points
    protected int MaxHP; // Unit's Max Health Points
    protected int strength; // Unit's strentgh, used for physical attack damage
    protected int magic; // Unit's magic, used for magic attack damage
    protected int skill; // // Unit's skill, used for accuracy, critical hit rate, and skill activation
    protected int speed; // // Unit's speed if unit's speed is 5 points higher than the opponent’s, attacking unit performs a follow-up attack
    protected int luck; // Unit's luck, used to calculate hit, avoid, and to dodge critical hits, and skill activation
    protected int defense; // Unit's defense against physical attacks
    protected int resistance; // Unit's defence against magic attacks
    protected int movement; // The number of tiles the unit can move

    // Unit Growth Rates (chance of stat increasing by 1 when unit levels up), only for player units
    protected int HPGrowthRate;
    protected int strengthGrowthRate;
    protected int magicGrowthRate;
    protected int skillGrowthRate;
    protected int speedGrowthRate;
    protected int luckGrowthRate;
    protected int defenseGrowthRate;
    protected int resistanceGrowthRate;

    /* Growth Rate Calculations
       Growth rates for each stat is a number between 1 and 100, and we use a randomizer to get a random number,
       if the number is between 1 and the growth rate value then stat is increased by 1 during level up, else it is not
     */

    // Unit's Combat stats
    protected int attack; // Strength or Magic + Weapon’s or Spell's Might
    protected int hitRate; // Weapon’s Hit rate + [(Skill x 3 + Luck) / 2]
    protected int critical; // Weapon’s Critical + (Skill / 2), critical hits deal damage multiplied by 3
    protected int effSpeed; // Unit's effective speed = (unit's speed - weapon's weight) - (enemy's speed - enemy weapons' weight)
                            // if it's > 5 then attacking unit performs a follow-up attack
    protected int avoid; // (Speed x 3 + Luck) / 2

    // Unit types, human, orc, tauren, elf or dragon
    // can be mounted (on a horse) or armored, attacks strong against these types deal damage multiplied by 3
    protected enum UnitType {Human, Orc, Tauren, Elf, Dragon}
    protected UnitType type;  // Variable to store the unit type
    protected boolean armored; // Unit's armored status
    protected boolean mounted; // Unit's mounted status

    /* BATTLE CALCULATIONS

     Attack (damage)= Attack – Enemy’s Defence or Resistance
     Hit rate = (Hit rate  – Enemy’s Avoid) [%]
     Critical rate = (Critical  – Enemy’s Luck) [%]
     Level difference (LD) 	= enemy’s Level – player’s Level
     Experience from doing no damage = 0
     Exp. from damaging enemy = (31 + LD) / 3, if LD >= 0
                              = 10, if LD = -1
                              = Max[ (33 + LD) / 3, 1], if LD <= -2
     Exp. from killing enemy  = 20 + (LD x 3), if LD >= 0
                              = 20, if LD = -1
                              = Max[ 26 + (LD) x 3), 7], if LD <= -2
     */

    GamePanel gp;

    public Entity (GamePanel gp) {
        this.gp = gp;
    }

    // Method to update the unit's pixel position based on its current tile position
    public void updatePosition () {
        x = getX(col);
        y = getY(row);
    }

    public BufferedImage setup (String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.getTileSize(), gp.getTileSize());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setupPortrait (String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, 8 *gp.getTileSize(), 8 *gp.getTileSize());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void drawUnitAnimation(Graphics2D g2) {
        BufferedImage image = null;

        // Select the correct image based on the unit's direction and current sprite frame
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
        // Draw the selected image at the unit's position, scaled to the tile size
        g2.drawImage(image, x, y, null);
    }

    // Placeholder methods

    public void draw(Graphics2D g2) {   }  // Placeholder method to draw the unit on the screen

    public void loadImage() {  }   // Placeholder method that loads images for the unit's animation frames

    public void startTurn() {  }  // Placeholder method to start the unit's turn

    public void endTurn() {  }   // Placeholder method to end the unit's turn

    public void update() {    }  // Placeholder method to update the unit's state

    public void move() {    }  // Placeholder method to move a unit

    public void setupStats() {   }  // Placeholder method to set up the unit's stats

    public void setupGrowthRates() {   }  // Placeholder method to set up the player growth rates

    // Getter methods

    public int getX(int col) { return col * gp.getTileSize(); } // Calculate x position in pixels based on column

    public int getY(int row) { return row * gp.getTileSize(); } // Calculate y position in pixels based on row

    public int getCol() { return col; } // Get the unit's current column

    public int getRow() { return row; } // Get the unit's current row

    public boolean getWait () { return wait; } // Get the unit's wait status

    public BufferedImage getPortrait() { return portrait; } // Get the unit's portrait image

    public String getName() { return name; } // Get the unit's name

    public String getClassName() { return className; } // Get the unit's class name

    public String[] getDescription() { return description; } // Get the unit's description

    public int getLevel() { return level; } // Get the unit's level

    public int getMaxLevel() { return maxLevel; } // Get the unit's max level

    public int getExp() { return exp; } // Get the unit's experience points

    public int getMaxExp() { return maxExp; } // Get the unit's max experience points

    public int getStrength() { return strength; } // Get the unit's strength

    public int getMagic() { return magic; } // Get the unit's magic

    public int getSkill() { return skill; } // Get the unit's skill

    public int getSpeed() { return speed; } // Get the unit's speed

    public int getLuck() { return strength; } // Get the unit's luck

    public int getDefense() { return defense; } // Get the unit's defense

    public int getResistance() { return resistance; } // Get the unit's resistance

    public int getMovement() { return movement; } // Get the unit's movement

    public boolean isArmored() { return armored; } // Get the unit's armored status

    public boolean isMounted() { return mounted; } // Get the unit's mounted status

    public UnitType getType() { return type; } // Get the unit's type
}




