package Entity;

import Item.Potion;
import Item.Trinket;
import Item.Weapon;
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
    protected int maxHP; // Unit's Max Health Points
    protected int strength; // Unit's strentgh, used for physical attack damage
    protected int magic; // Unit's magic, used for magic attack damage
    protected int skill; // // Unit's skill, used for accuracy, critical hit rate, and skill activation
    protected int speed; // // Unit's speed if unit's speed is 5 points higher than the opponent’s, attacking unit performs a follow-up attack
    protected int luck; // Unit's luck, used to calculate hit, avoid, and to dodge critical hits, and skill activation
    protected int defense; // Unit's defense against physical attacks
    protected int resistance; // Unit's defence against magic attacks
    protected int movement; // The number of tiles the unit can move
    protected int vision; // The number of tiles the unit can see

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
    protected int bonusStrength; // Trinket's Strength
    protected int bonusMagic; // Trinket's Magic
    protected int bonusSkill; // Trinket's Skill
    protected int bonusSpeed; // Trinket's Speed
    protected int bonusDefense; // Trinket's Defense
    protected int bonusResistance; // Trinket's Resistance
    protected int bonusVision; //  Trinket's Vision

    protected int effStrength; // Strength + Trinket's Strength
    protected int effMagic; // Magic + Trinket's Magic
    protected int effSkill; // Skill + Trinket's Skill
    protected int effSpeed; // Speed + Trinket's Speed
    protected int effDefense; // Defense + Trinket's Defense
    protected int effResistance; // Resistance + Trinket's Resistance
    protected int effVision; // Vision + Trinket's Vision

    protected int might; // eff.Strength or eff.Magic + Weapon’s or Spell's Might
    protected int critical; // Weapon’s Critical + (eff.Skill / 2), critical hits deal damage multiplied by 3
    protected int hitRate; // Weapon’s Hit rate + [(eff.Skill x 3 + Luck) / 2]
    protected int evade; // (eff.Speed x 3 + Luck) / 2


    // Unit types, human, orc, tauren, elf or dragon
    // can be mounted (on a horse) or armored, attacks strong against these types deal damage multiplied by 3
    protected enum UnitType {Human, Orc, Tauren, Elf, Dragon}
    protected UnitType type;  // Variable to store the unit type
    protected boolean armored; // Unit's armored status
    protected boolean mounted; // Unit's mounted status
    protected boolean physical; // Physical unit
    protected boolean magical; // Magical unit

    public Weapon equippedWeapon = null; // Unit's equipped weapon, player units can switch between main hand and offhand weapon
    public Trinket trinket = null; // Unit's trinket item
    public Potion potionSlot1 = null; // Unit's 1st Potion slot
    public Potion potionSlot2 = null; // Unit's 2nd Potion slot

    /* BATTLE CALCULATIONS

     Attack (damage)= Might – Enemy’s eff.Defence or eff.Resistance
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

    public void calculateCombatStats() {
        if (trinket != null) {
            bonusStrength = trinket.getStrength();
            bonusMagic = trinket.getMagic();
            bonusSkill = trinket.getSkill();
            bonusSpeed = trinket.getSpeed();
            bonusDefense =  trinket.getDefense();
            bonusResistance = trinket.getResistance();
            bonusVision = trinket.getVision();
        }
        if (trinket == null) {
            bonusStrength = 0;
            bonusMagic = 0;
            bonusSkill = 0;
            bonusSpeed = 0;
            bonusDefense = 0;
            bonusResistance = 0;
            bonusVision = 0;
        }
        effStrength = strength + bonusStrength;
        effMagic = magic + bonusMagic;
        effSkill = skill + bonusSkill;
        effSpeed = speed + bonusSpeed;
        effDefense = defense + bonusDefense;
        effResistance = resistance + bonusResistance;
        effVision = vision + bonusVision;

        if (physical) {
            might = effStrength + equippedWeapon.getMight();
            critical = (effSkill/2) + equippedWeapon.getCrit();
            hitRate = (((effSkill * 3) + luck) / 2) + equippedWeapon.getHit();
        }
        if (magical){
            might = effMagic + equippedWeapon.getMight();
            critical = (effSkill/2) + equippedWeapon.getCrit();
            hitRate = (((effSkill * 3) + luck) / 2) + equippedWeapon.getHit();
        }
        evade = ((effSpeed * 3) + luck) / 2;
    }

    // Stat modifiers for its class
    public void boostStatsForClasses() {
        if (type == UnitType.Human) {
            skill += 3;
            speed += 2;
            maxHP -= 2;
            if (maxHP < 0){
                maxHP = 0;
            }
        }
        if (type == UnitType.Orc) {
            maxHP += 3;
            strength += 2;
            skill -= 2;
            if (skill < 0){
                skill = 0;
            }
        }
        if (type == UnitType.Tauren) {
            strength += 3;
            defense += 2;
            resistance -= 2;
            if (resistance < 0){
                resistance = 0;
            }
        }
        if (type == UnitType.Elf) {
            speed += 3;
            resistance += 2;
            strength -= 2;
            if (strength < 0){
                strength = 0;
            }
        }
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

    public void draw (Graphics2D g2) {
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

    public int getExp() { return exp; } // Get the unit's experience points

    public int getMaxExp() { return maxExp; } // Get the unit's max experience points

    public int getHP() { return HP; } // Get the unit's health points

    public int getMaxHP() { return maxHP; } // Get the unit's max health points

    public int getStrength() { return strength; } // Get the unit's strength

    public int getMagic() { return magic; } // Get the unit's magic

    public int getSkill() { return skill; } // Get the unit's skill

    public int getSpeed() { return speed; } // Get the unit's speed

    public int getLuck() { return luck; } // Get the unit's luck

    public int getDefense() { return defense; } // Get the unit's defense

    public int getResistance() { return resistance; } // Get the unit's resistance

    public int getMovement() { return movement; } // Get the unit's movement

    public int getVision() { return vision; } // Get the unit's vision

    public boolean isArmored() { return armored; } // Get the unit's armored status

    public boolean isMounted() { return mounted; } // Get the unit's mounted status

    public boolean isPhysical() { return physical; } // Get the unit's physical status

    public boolean isMagical() { return magical; } // Get the unit's magical status

    public UnitType getType() { return type; } // Get the unit's type

    public int getMight() {return might;} // Get the unit's might

    public int getCritical() {return critical;} // Get the unit's critical

    public int getHitRate() {return hitRate;} // Get the unit's hit rate

    public int getEvade() {return evade; } // Get the unit's evade

    public int getEffStrength() {return effStrength;} // Get the unit's effective strength

    public int getEffMagic() {return effMagic;} // Get the unit's effective magic

    public int getEffSkill() {return effSkill;} // Get the unit's effective skill

    public int getEffSpeed() {return effSpeed;} // Get the unit's effective speed

    public int getEffDefense() {return effDefense;} // Get the unit's effective defence

    public int getEffResistance() {return effResistance;} // Get the unit's effective resistance

    public int getEffVision() {return effVision;} // Get the unit's effective vision

    public int getBonusStrength() {return bonusStrength;} // Get the unit's bonus strength

    public int getBonusMagic() {return bonusMagic;} // Get the unit's bonus magic

    public int getBonusSkill() {return bonusSkill;} // Get the unit's bonus skill

    public int getBonusSpeed() {return bonusSpeed;} // Get the unit's bonus speed

    public int getBonusDefense() {return bonusDefense;} // Get the unit's bonus defence

    public int getBonusResistance() {return bonusResistance;} // Get the unit's bonus resistance

    public int getBonusVision() {return bonusVision;} // Get the unit's bonus vision

}




