package main;

import Entity.ChaosUnit;
import Entity.LightUnit;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font arial_30;
    Font arial_20;
    Graphics2D g2;

    // Variables to control the phase dipslay
    int phaseRectWidth = 300; // Width of the phase rectangle
    int phaseRectHeight = 50; // Height of the phase rectangle
    int phaseRectX = 69 * 16; // Starting X position of the rectangle

    // Draw the turn counter rectangle (smaller rectangle)
    int turnRectX = 52 * 16;     // Position X
    int turnRectY = 2 * 16 - 40; // Position Y
    int turnRectWidth = 150;   // Width of the rectangle
    int turnRectHeight = 50;   // Height of the rectangle

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial_20 = new Font("Arial", Font.PLAIN, 20);
    }

    // TURN AND PHASE UI
    // Draw Turn counter and phases
    public void drawTurns() {
        // Draw the turn counter
        g2.setColor(new Color(0, 0, 0, 150));      // Semi-transparent black
        g2.fillRoundRect(turnRectX, turnRectY, turnRectWidth, turnRectHeight, 20, 20);

        g2.setColor(Color.WHITE);
        g2.drawString("Turn " + gp.TurnM.getTurnCounter(), 53 * gp.getTileSize(), 2 * gp.getTileSize());

        // Set the phase text and rectangle colors and draw them
        if (gp.TurnM.getPlayerPhase()) {
            // Draw the blue rectangle for the Player Phase
            g2.setColor(new Color(0, 0, 255, 150)); // Semi-transparent blue
            g2.fillRoundRect(phaseRectX, 2 * gp.getTileSize() - 40, phaseRectWidth, phaseRectHeight, 25, 25);

            // Draw the Player Phase text
            g2.setColor(Color.WHITE);
            g2.drawString("Player Phase", phaseRectX + 20, 2 * gp.getTileSize());

        } else {
            // Draw the red rectangle for the Enemy Phase
            g2.setColor(new Color(255, 0, 0, 150)); // Semi-transparent red
            g2.fillRoundRect(phaseRectX, 2 * gp.getTileSize() - 40, phaseRectWidth, phaseRectHeight, 25, 25);

            // Draw the Enemy Phase text
            g2.setColor(Color.WHITE);
            g2.drawString("Enemy Phase", phaseRectX + 20, 2 * gp.getTileSize());
        }
    }

    // LOG UI
    // Draw Log Window
    public void drawLogScreen() {
        int x = 52 * 16;
        int y = 37 * 16;
        int width = 32 * 16;
        int height = 15 * 16;
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRoundRect(x, y, width, height, 25, 25);
    }

    // BEACONS OF LIGHT UI
    public void drawBeaconOfLightTurns() {
        int x = 52 * 16;
        int y = 3 * 16;
        int width = 32 * 16;
        int height = 2 * 16 - 5;

        // if (Beacon of Light turns > 0 && numberofBeaconsUsed < 3)  {
        //    g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        //  }
        //  else if (Beacon of Light turns == 0 && numberofBeaconsUsed < 3) {
        g2.setColor(new Color(236, 210, 19, 200)); // Semi-transparent yellow
        // }
        //   else if (numberofBeaconsUsed = 3) {
        //   g2.setColor(new Color(0, 0, 255, 150)); // Semi-transparent blue
        // }
        g2.fillRoundRect(x, y, width, height, 20, 20);

        g2.setFont(arial_20);
        g2.setColor(Color.WHITE);
        // if (Beacon of Light turns > 0 && numberofBeaconsUsed < 3){
      //  g2.drawString(Beacon of Light turns + " until Beacon of Light can be used", 53 * gp.getTileSize(), 4 * gp.getTileSize() + 5);
        //  }
        //  else if (Beacon of Light turns == 0 && numberofBeaconsUsed < 3) {
        g2.drawString("Beacon of Light can be used", 53 * gp.getTileSize(), 4 * gp.getTileSize() + 5);
        // }
     //   else if (numberofBeaconsUsed = 3) {
      //  g2.drawString("Defeat the boss and exit the floor", 53 * gp.getTileSize(), 4 * gp.getTileSize() + 5);
        //    }
    }

    // CONTROLS SCREEN UI
    public void drawControls() {
        // Title "Controls" at the top
        g2.drawString("Controls:", 50, 4 * 16);

        // Control instructions text
        String[] controlsText = {
                "Up, Down, Left, Right: Move Cursor and Player Unit",
                "A: Select Player Unit",
                "W: End Player Unit's Turn",
                "Z: Cancel",
                "Q: Toggle between Unit's stats and Unit's inventory",
                "Shift: Switch between Player Units",
                "E: End Player Phase",
                "P: View Controls Screen"
        };

        // Draw each control instruction line
        int lineHeight = 40; // Spacing between lines
        for (int i = 0; i < controlsText.length; i++) {
            g2.drawString(controlsText[i], 50, 4 * 16 + (i + 2) * lineHeight);
        }

        // "Press Enter to continue" at the bottom of the screen
        g2.drawString("Press Enter to continue", 30 * 16, 50 * 16);
    }

    // LIGHT UNIT UI

    // Draw Light Unit Window
    public void drawLightUnitScreen() {
        int x = 52 * 16;
        int y = 5 * 16;
        int width = 15 * 16 + 12;
        int height = 31 * 16 + 10;
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRoundRect(x, y, width, height, 25, 25);
    }

    // Draw Light Unit Portrait
    public void drawLightUnitPortrait(LightUnit player) {
        BufferedImage image = null;
        int x = 52 * 16;
        int y = 5 * 16;
        int width = 8 * gp.getTileSize();
        int height = 8 * gp.getTileSize();

        drawLightUnitScreen();
        // Draw a semi-transparent background
        g2.setColor(new Color(0, 0, 0, 150)); // RGBA, 150 is the transparency
        g2.fillRect(x, y, width, height);

        // Draw the portrait image
        image = player.getPortrait();
        g2.drawImage(image, x, y, null);

        // Draw a thin white line on the right and bottom sides
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2)); // thin line
        // Right side line
        g2.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
        // Bottom side line
        g2.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
    }

    // Draw Light Unit's stats
    public void drawLightUnitStats(LightUnit player) {
        // Coordinates for text next to the portrait
        int textX = 52 * 16 + 8 * gp.getTileSize() + 10; // Right side of the portrait
        int textY = 5 * 16 + 20; // Slightly below the top of the portrait
        int lineHeight = 20; // Spacing between each line of text
        int nextLine = 1;

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 17));
        g2.setColor(Color.WHITE);

        // Draw unit's name, class name, and level next to the portrait
        g2.drawString(player.getName(), textX, textY);
        g2.drawString(player.getClassName(), textX, textY + nextLine * lineHeight);
        nextLine++;
        g2.drawString(String.valueOf(player.getType()), textX, textY + nextLine * lineHeight);
        nextLine++;
        if (player.isArmored()) {
            g2.drawString("Armored", textX, textY + nextLine * lineHeight);
            nextLine++;
        }
        if (player.isMounted()) {
            g2.drawString("Mounted", textX , textY + nextLine * lineHeight);
            nextLine++;
        }
        g2.drawString("Level: " + player.getLevel(), textX, textY + nextLine * lineHeight);
        nextLine++;
        g2.drawString("Exp: " + player.getExp() + "/" + player.getMaxExp(), textX, textY + nextLine * lineHeight);

        // Coordinates for stats below the portrait
        int statsX = 52 * 16 + 5; // Align with the portrait's X position
        int statsY = 5 * 16 + 8 * gp.getTileSize() + 20; // Start drawing below the portrait

        // Draw the unit's stats below the portrait
        g2.drawString("HP: " + player.getHP() + "/" + player.getMaxHP(), statsX, statsY);
        g2.drawString("Strength: " + player.getStrength(), statsX, statsY + lineHeight);
        g2.drawString("Magic: " + player.getMagic(), statsX, statsY + 2 * lineHeight);
        g2.drawString("Skill: " + player.getSkill(), statsX, statsY + 3 * lineHeight);
        g2.drawString("Speed: " + player.getSpeed(), statsX, statsY + 4 * lineHeight);
        g2.drawString("Luck: " + player.getLuck(), statsX, statsY + 5 * lineHeight);
        g2.drawString("Defense: " + player.getDefense(), statsX, statsY + 6 * lineHeight);
        g2.drawString("Resistance: " + player.getResistance(), statsX, statsY + 7 * lineHeight);
        g2.drawString("Movement: " + player.getMovement(), statsX, statsY + 8 * lineHeight);
    }

    public void drawLightUnitDetails (LightUnit player) {
        int textX = 52 * 16 + 8 * gp.getTileSize() + 4; // Right side of the portrait
        int textY = 5 * 16 + 20; // Slightly below the top of the portrait
        int lineHeight = 20; // Spacing between each line of text

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 13));
        g2.setColor(Color.WHITE);

        // Draw unit's description line by line
        for (String line : player.getDescription()) {
            g2.drawString(line, textX, textY);
            textY += lineHeight;
        }

        /*
        // Draw unit's weapons and their stats/descriptions
        g2.drawString("Weapons:", textX, textY);
        textY += lineHeight;
        for (Weapon weapon : weapons) {
            g2.drawString(weapon.getName() + " (Damage: " + weapon.getDamage() + ", Range: " + weapon.getRange() + ")", textX, textY);
            textY += lineHeight;
            g2.drawString("  " + weapon.getDescription(), textX + 20, textY);
            textY += lineHeight;
        }

        // Draw unit's skills and their descriptions
        textY += lineHeight; // Add some space between weapons and skills
        g2.drawString("Skills:", textX, textY);
        textY += lineHeight;
        for (Skill skill : skills) {
            g2.drawString(skill.getName(), textX, textY);
            textY += lineHeight;
            g2.drawString("  " + skill.getDescription(), textX + 20, textY);
            textY += lineHeight;
        }

         */
    }

    // Method to draw the Light Unit's information
    public void drawLightUnitInfo() {
        if (gp.TurnM.getPlayerPhase()) {
            if (gp.selectedUnit == null) {
                for (LightUnit unit : gp.simLightUnits) {
                    if (gp.cursor.getCol() == unit.getCol() && gp.cursor.getRow() == unit.getRow()) {
                        drawLightUnitPortrait(unit);
                        if (gp.keyH.isQPressed()) {
                            drawLightUnitStats(unit);
                        } else {
                            drawLightUnitDetails(unit);
                        }
                    }
                }
            } else if (gp.selectedUnit != null) {
                drawLightUnitPortrait(gp.selectedUnit);
                if (gp.keyH.isQPressed()) {
                    drawLightUnitStats(gp.selectedUnit);
                } else {
                    drawLightUnitDetails(gp.selectedUnit);
                }
            }
        }
    }

    // CHAOS UNIT UI

    // Draw Chaos Unit Window
    public void drawChaosUnitScreen() {
        int x = 69 * 16 - 12;
        int y = 5 * 16;
        int width = 16 * 16 + 12;
        int height = 31 * 16 + 10;
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRoundRect(x, y, width, height, 25, 25);
    }

    // Draw Chaos Unit Portrait
    public void drawChaosUnitPortrait(ChaosUnit enemy) {
        BufferedImage image = null;
        int x = 69 * 16 - 12;
        int y = 5 * 16;
        int width = 8 * gp.getTileSize();
        int height = 8 * gp.getTileSize();

        drawChaosUnitScreen();
        // Draw a semi-transparent background
        g2.setColor(new Color(0, 0, 0, 150)); // RGBA, 150 is the transparency
        g2.fillRect(x, y, width, height);

        // Draw the portrait image
        image = enemy.getPortrait();
        g2.drawImage(image, x, y, null);

        // Draw a thin white line on the right and bottom sides
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2)); // thin line
        // Right side line
        g2.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
        // Bottom side line
        g2.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
    }

    // Draw Chaos Unit's stats
    public void drawChaosUnitStats(ChaosUnit enemy) {
        // Coordinates for text next to the portrait
        int textX = 69 * 16 + 7 * gp.getTileSize() + 9; // Right side of the portrait
        int textY = 5 * 16 + 20; // Slightly below the top of the portrait
        int lineHeight = 20; // Spacing between each line of text
        int nextLine = 1;

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 17));
        g2.setColor(Color.WHITE);

        // Draw unit's name, class name, and level next to the portrait
        g2.drawString(enemy.getName(), textX, textY);
        g2.drawString(enemy.getClassName(), textX, textY + nextLine * lineHeight);
        nextLine++;
        g2.drawString(String.valueOf(enemy.getType()), textX, textY + nextLine * lineHeight);
        nextLine++;
        if (enemy.isArmored()) {
            g2.drawString("Armored", textX, textY + nextLine * lineHeight);
            nextLine++;
        }
        if (enemy.isMounted()) {
            g2.drawString("Mounted", textX, textY + nextLine * lineHeight);
            nextLine++;
        }
        g2.drawString("Level: " + enemy.getLevel(), textX, textY + nextLine * lineHeight);

        // Coordinates for stats below the portrait
        int statsX = 69 * 16 - 5; // Align with the portrait's X position
        int statsY = 5 * 16 + 8 * gp.getTileSize() + 20; // Start drawing below the portrait

        // Draw the unit's stats below the portrait
        g2.drawString("HP: " + enemy.getHP() + "/" + enemy.getMaxHP(), statsX, statsY);
        g2.drawString("Strength: " + enemy.getStrength(), statsX, statsY + lineHeight);
        g2.drawString("Magic: " + enemy.getMagic(), statsX, statsY + 2 * lineHeight);
        g2.drawString("Skill: " + enemy.getSkill(), statsX, statsY + 3 * lineHeight);
        g2.drawString("Speed: " + enemy.getSpeed(), statsX, statsY + 4 * lineHeight);
        g2.drawString("Luck: " + enemy.getLuck(), statsX, statsY + 5 * lineHeight);
        g2.drawString("Defense: " + enemy.getDefense(), statsX, statsY + 6 * lineHeight);
        g2.drawString("Resistance: " + enemy.getResistance(), statsX, statsY + 7 * lineHeight);
        g2.drawString("Movement: " + enemy.getMovement(), statsX, statsY + 8 * lineHeight);
    }

        // Draw Chaos Unit's details
        public void drawChaosUnitDetails (ChaosUnit enemy) {
        int textX = 69 * 16 + 7 * gp.getTileSize() + 9; // Right side of the portrait
        int textY = 5 * 16 + 20; // Slightly below the top of the portrait
        int lineHeight = 20; // Spacing between each line of text

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 13));
        g2.setColor(Color.WHITE);

        // Draw unit's description line by line
        for (String line : enemy.getDescription()) {
            g2.drawString(line, textX, textY);
            textY += lineHeight;
        }

        /*
        // Draw unit's weapons and their stats/descriptions
        g2.drawString("Weapons:", textX, textY);
        textY += lineHeight;
        for (Weapon weapon : weapons) {
            g2.drawString(weapon.getName() + " (Damage: " + weapon.getDamage() + ", Range: " + weapon.getRange() + ")", textX, textY);
            textY += lineHeight;
            g2.drawString("  " + weapon.getDescription(), textX + 20, textY);
            textY += lineHeight;
        }

        // Draw unit's skills and their descriptions
        textY += lineHeight; // Add some space between weapons and skills
        g2.drawString("Skills:", textX, textY);
        textY += lineHeight;
        for (Skill skill : skills) {
            g2.drawString(skill.getName(), textX, textY);
            textY += lineHeight;
            g2.drawString("  " + skill.getDescription(), textX + 20, textY);
            textY += lineHeight;
        }

         */
    }

    // Method to draw the Light Unit's information
    public void drawChaosUnitInfo() {

        if (gp.TurnM.getPlayerPhase()) {
            if (gp.selectedUnit == null) {
                for (ChaosUnit enemy : gp.simChaosUnits) {
                    if (gp.cursor.getCol() == enemy.getCol() && gp.cursor.getRow() == enemy.getRow()) {
                        drawChaosUnitPortrait(enemy);
                        if (gp.keyH.isQPressed()) {
                            drawChaosUnitStats(enemy);
                        } else {
                            drawChaosUnitDetails(enemy);
                        }
                    }
                }
            }
        }
    }

    // BATTLE UI

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        int col, row;
        // Draw the map background as black rectangles
        for (row = 0; row < gp.getMaxMapRow(); row++) {
            for (col = 0; col < gp.getMaxMapCol(); col++) {
                g2.setColor(Color.BLACK);
                g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(), gp.getTileSize());
            }
        }

        g2.setFont(arial_30);
        g2.setColor(Color.WHITE);

        // CONTROLS
        if (gp.gameState == gp.controlsState) {
            drawControls();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawTurns();
            drawLogScreen();
            drawBeaconOfLightTurns();
            drawLightUnitInfo();
            drawChaosUnitInfo();
        }
    }
}
