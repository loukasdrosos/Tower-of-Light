package main;

import Entity.*;
import Item.*;
import Spells.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UI {

    GamePanel gp;
    KeyHandler keyH;
    Font arial_30;
    Font arial_20;
    Graphics2D g2;
    BufferedImage attackSpellImage;
    BufferedImage healingSpellImage;
    BufferedImage finale;
    BufferedImage creditsImage;

    // Variables to control the phase dipslay
    int phaseRectWidth = 300; // Width of the phase rectangle
    int phaseRectHeight = 50; // Height of the phase rectangle
    int phaseRectX = 69 * 16; // Starting X position of the rectangle

    // Draw the turn counter rectangle (smaller rectangle)
    int turnRectX = 52 * 16;     // Position X
    int turnRectY = 2 * 16 - 40; // Position Y
    int turnRectWidth = 150;   // Width of the rectangle
    int turnRectHeight = 50;   // Height of the rectangle

    private List<String> logMessages = new ArrayList<>(); // List to hold all log messages
    private int scrollPosition = 0; // Current scroll position
    private static final int maxVisibleMessages = 11; // Max number of visible messages in Game Log
    private static final int messageHeight = 20; // Height of each message line for Game Log

    // Variables that control item cursor movement
    private boolean vKeyPressed = false; // Flag to track if the V key was pressed in the last frame
    private int slotCol = 0;
    private int slotRow = 0;
    private boolean moving = false; // Indicates if the cursor is currently moving
    private int moveDelayCounter = 0; // Counts frames to manage movement delay
    private int moveDelayThreshold = 5; // Threshold to control the speed of cursor movement

    public UI(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        try {
            attackSpellImage = ImageIO.read(new File("res/Spells/Attack_Spell.png"));
            healingSpellImage = ImageIO.read(new File("res/Spells/Healing_Spell.png"));
            finale = ImageIO.read(new File("res/FinaleImage/Finale.png"));
            creditsImage = ImageIO.read(new File("res/CreditsScreen/CreditsScreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // INTRO STATE UI

    public void drawIntro() {
        // Intro text
        g2.setFont(arial_20);
        String[] introText = {
                "200 years ago, the Chaos God Grima and his Chaos army tried to take ",
                "over the world. In the most fatal times, however, the Hero King Marth ",
                "of the kingdom of Valentia and his companions used all of their power",
                "to resist the Chaos army's invasion and win against them. ",
                "The Hero King used his sword Lightbringer to unleash the last power",
                "of Light left in the world to seal Grima, because Grima was too powerful",
                "to be killed. To remind everyone of these heroic actions the Tower of Light",
                "was built, which also holds enough Light power to help the next heroes defeat",
                "the Chaos army if another invasion ever happens. Peace lasted for 200 years and",
                "no one thought dark times would ever come again. However...",
        };

        // Draw each intro line
        int lineHeight = 40; // Spacing between lines
        for (int i = 0; i < introText.length; i++) {
            g2.drawString(introText[i], 50, 2 * 16 + (i + 2) * lineHeight);
        }

        // "Press Enter to continue" at the bottom of the screen
        g2.setFont(arial_30);
        g2.drawString("Press Enter to continue", 30 * 16, 50 * 16);

    }

    // INTRO STATE 2 UI

    public void drawIntro2() {
        // Intro text
        g2.setFont(arial_20);
        String[] introText = {
                "Alm: We are at the top of the Tower of Light, we can't hold them back",
                "Rudolf: Young prince, we are the last hope of this world!",
                "Alm: I know, everyone outside the Tower is fighting for survival",
                "Rudolf: Unfortunately sire your parents and the princess...your wife Celica...",
                "Alm: The reports said they are missing but lets keep our hopes up",
                "Boey: Hey Alm, use that sword you have, it defeated the Chaos army the last time",
                "Alm: I don't know how to master its true potential",
                "Shade: I heard from a priest that you need to use Beacons of Light to unleash",
                "Lightbringer's full power",
                "Rudolf: Grima is at the ground floor of the Tower, use that sword to defeat him Alm!",
                "Alm: But i am not strong enough",
                "Rudolf: From the time you take your first breath, you become eligible to die.",
                "You also become eligible to find your greatness and become the one warrior.",
                "Remember, we don’t rise to the level of our expectations, we fall to the level of",
                "our training. Use everything you learned so far and you will win.",
                "Alm: Alright, lets do this!"
        };

        // Draw each intro line
        int lineHeight = 40; // Spacing between lines
        for (int i = 0; i < introText.length; i++) {
            g2.drawString(introText[i], 50, 2 * 16 + (i + 2) * lineHeight);
        }

        // "Press Enter to continue" at the bottom of the screen
        g2.setFont(arial_30);
        g2.drawString("Press Enter to continue", 30 * 16, 50 * 16);

    }

    // CREDITS STATE
    public void drawCredits() {
        g2.drawImage(creditsImage, 0, 0, 52 * gp.getTileSize(), 52 * gp.getTileSize(), null);

        int rectX = 52 * 16;    // Starting X position of the rectangle
        int rectY = 0;          // Starting Y position of the rectangle
        int rectWidth = 32 * 16;
        int rectHeight = 52 * 16;
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 25, 25);

        // Set text color and font
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));

        // Determine the line height based on the font metrics
        FontMetrics metrics = g2.getFontMetrics();
        int lineHeight = metrics.getHeight();

        // Start drawing the text inside the rectangle, one line below the other
        int startX = rectX + 20; // Padding from the left side of the rectangle
        int startY = rectY + 40; // Padding from the top of the rectangle

        // List of text to draw
        String[] creditsText = {
                "",
                "",
                "",
                "THANKS FOR PLAYING",
                "",
                "",
                "Program: Loukas Drosos",
                "",
                "Music/Art: Nintendo & Intelligent Systems",
                "",
                "All music and art in this game are used",
                "for educational purposes only"
        };

        // Loop through each line and draw it, increasing Y position for each line
        for (String text : creditsText) {
            g2.drawString(text, startX, startY);
            startY += lineHeight + 10; // Increase Y for the next line (10 is the spacing between lines)
        }
    }

    // FINALE

    public void drawFinale() {
        if (gp.gameState == gp.playState) {
            g2.drawImage(creditsImage, 0, 0, 52 * gp.getTileSize(), 52 * gp.getTileSize(), null);
        }
    }

    // CONTROLS STATE UI

    public void drawControls() {
        // Title "Controls" at the top
        g2.drawString("Controls:", 50, 4 * 16);

        // Control instructions text
        g2.setFont(arial_20);
        String[] controlsText = {
                "↑, ↓, ←, →: Move cursor and selected player unit",
                "A: Select player unit, display enemy attack range",
                "X: Attack",
                "C: Change equipped weapon",
                "D: Use potion",
                "S: Healing Spell",
                "B: Activate Beacon of Light",
                "V: View items on the map",
                "W: End player unit's turn",
                "SPACE: If Alm is on stairs, go to the next floor",
                "Z: Cancel action",
                "Q: Toggle between unit's stats and unit's inventory",
                "Shift: Switch between player units",
                "E: End player phase",
                "1, 2: Log screen up and down",
                "P: View controls screen"
        };

        // Draw each control instruction line
        int lineHeight = 40; // Spacing between lines
        for (int i = 0; i < controlsText.length; i++) {
            g2.drawString(controlsText[i], 50, 3 * 16 + (i + 2) * lineHeight);
        }

        // "Press Enter to continue" at the bottom of the screen
        g2.setFont(arial_30);
        g2.drawString("Press Enter to continue", 30 * 16, 50 * 16);
    }

    // PLAYSTATE UI

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

    // GAME LOG UI

    // Draw Game Log Window
    public void drawLogScreen() {
        int x = 52 * 16;
        int y = 37 * 16;
        int width = 32 * 16;
        int height = 15 * 16;
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRoundRect(x, y, width, height, 25, 25);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));

        int textY = y + messageHeight; // Initial Y position for text

        // Render the visible messages
        for (int i = scrollPosition; i < scrollPosition + maxVisibleMessages && i < logMessages.size(); i++) {
            g2.drawString(logMessages.get(i), x + 10, textY);
            textY += messageHeight;
        }

        // Draw scrollbar
        drawScrollbar(g2, x + width - 20, y, height);
    }

    // Method to add a message to the log
    public void addLogMessage(String message) {
        logMessages.add(message);
        scrollPosition = Math.max(0, logMessages.size() - maxVisibleMessages); // Auto-scroll to the bottom
    }

    // Method to scroll the log window
    public void scrollLog(int direction) {
        scrollPosition += direction;
        scrollPosition = Math.max(0, Math.min(scrollPosition, logMessages.size() - maxVisibleMessages));
    }

    public void clearLog() {
        // Clear the log messages list
        logMessages.clear();
    }

    // Draw a simple scrollbar
    private void drawScrollbar(Graphics2D g2, int x, int y, int height) {
        int totalMessages = logMessages.size();
        int visibleMessages = Math.min(maxVisibleMessages, totalMessages);
        if (totalMessages <= visibleMessages) {
            return;
        }

        int scrollbarHeight = (int) ((double) visibleMessages / totalMessages * height);
        int scrollbarY = y + (int) ((double) scrollPosition / totalMessages * height);

        g2.setColor(Color.GRAY);
        g2.fillRect(x, scrollbarY, 10, scrollbarHeight);
    }

    // BEACONS OF LIGHT UI

    public void drawBeaconOfLightTurns() {
        int x = 52 * 16;
        int y = 3 * 16;
        int width = 32 * 16;
        int height = 2 * 16 - 5;

        if (gp.TurnM.getBeaconCooldownTimer() > 0 && gp.TurnM.getActiveBeacons() < 3)  {
            g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        }
        else if (gp.TurnM.getBeaconCooldownTimer() == 0 && gp.TurnM.getActiveBeacons() < 3) {
             g2.setColor(new Color(236, 210, 19, 200)); // Semi-transparent yellow
        }
        else if (gp.TurnM.getActiveBeacons() == 3) {
           g2.setColor(new Color(0, 0, 255, 150)); // Semi-transparent blue
        }
        g2.fillRoundRect(x, y, width, height, 20, 20);

        g2.setFont(arial_20);
        g2.setColor(Color.WHITE);
        if (gp.TurnM.getBeaconCooldownTimer() > 0 && gp.TurnM.getActiveBeacons() < 3) {
             g2.drawString(gp.TurnM.getBeaconCooldownTimer() + " turns until Beacon of Light can be used", 53 * gp.getTileSize(), 4 * gp.getTileSize() + 5);
        }
        else if (gp.TurnM.getBeaconCooldownTimer() == 0 && gp.TurnM.getActiveBeacons() < 3) {
            g2.drawString("Beacon of Light can be used", 53 * gp.getTileSize(), 4 * gp.getTileSize() + 5);
        }
        else if (gp.TurnM.getActiveBeacons() == 3) {
            if (gp.getCurrentMap() < 6) {
                g2.drawString("Defeat the boss and exit the floor", 53 * gp.getTileSize(), 4 * gp.getTileSize() + 5);
            } else {
                g2.drawString("Defeat the boss", 53 * gp.getTileSize(), 4 * gp.getTileSize() + 5);
            }
        }
    }

    // COMBAT FORECAST UI

    public void drawCombatForecast() {
        int x = 52 * 16;
        int y = 37 * 16;
        int width = 32 * 16;
        int height = 15 * 16;
        int padding = 20;
        int lineHeight = 30;
        boolean inRange = false; // Boolean that tracks if selected unit is in range of selected enemy unit

        // Draw the background rectangle
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(x, y, width, height, 25, 25);

        // Set the color and font for the text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));

        // Get enemy to attack
        ChaosUnit enemy = gp.cChecker.getEnemyOnTile(gp.cursor.getCol(), gp.cursor.getRow());

        // Calculate positions for player and enemy stats
        int playerX = x + padding + 50;
        int enemyX = x + width / 2 + padding + 50;
        int textY = y + padding;
        String playerWeaponName = null;
        int playerAttack = 0;
        int playerHitRate = 0;
        int playerCrit = 0;
        String enemyWeaponName = null;
        int enemyAttack = 0;
        int enemyHitRate = 0;
        int enemyCrit = 0;

        if (enemy != null && gp.selectedUnit != null) {
            UtilityTool uTool = new UtilityTool();
            List<int[]> enemyRange = enemy.calculateStaticAttackRange();

            // Check if player unit is in enemy unit's range
            inRange = uTool.containsTile(enemyRange, gp.selectedUnit.getCol(), gp.selectedUnit.getRow());

            // Calculate and draw player's stats on the left side
            if (gp.selectedUnit.getAttackType() == Entity.AttackType.Physical) {
                playerWeaponName = gp.selectedUnit.equippedWeapon.getName();
                playerAttack = gp.selectedUnit.getMight() - enemy.getEffDefense();
                if (gp.selectedUnit.equippedWeapon.getEffectiveRace() == enemy.getRace() ) {
                    playerAttack = 3 * gp.selectedUnit.equippedWeapon.getMight() + gp.selectedUnit.getEffStrength() - enemy.getEffDefense();
                }
                if (gp.selectedUnit.equippedWeapon.getEffectiveType() == enemy.getUnitType() ) {
                    playerAttack = 3 * gp.selectedUnit.equippedWeapon.getMight() + gp.selectedUnit.getEffStrength() - enemy.getEffDefense();
                }
            } else if (gp.selectedUnit.getAttackType() == Entity.AttackType.Magical) {
                playerWeaponName = gp.selectedUnit.attackSpell.getName();
                playerAttack = gp.selectedUnit.getMight() - enemy.getEffResistance();
            }
            if (playerAttack < 0) {
                playerAttack = 0;
            }

            playerHitRate = gp.selectedUnit.getHitRate() - enemy.getEvade();
            if (playerHitRate > 100) {
                playerHitRate = 100;
            } else if (playerHitRate < 0) {
                playerHitRate = 0;
            }

            playerCrit = gp.selectedUnit.getCritical() - enemy.getLuck();
            if (playerCrit > 100) {
                playerCrit = 100;
            } else if (playerCrit < 0) {
                playerCrit = 0;
            }

            g2.drawString(gp.selectedUnit.getName(), playerX, textY + 5);
            textY += lineHeight;
            g2.drawString(playerWeaponName, playerX, textY + 5);
            textY += lineHeight;
            if (gp.selectedUnit.getEffSpeed() - enemy.getEffSpeed() >=  5) {
                g2.drawString("Attack: " + playerAttack + " x 2", playerX, textY + 5);
            } else {
                g2.drawString("Attack: " + playerAttack, playerX, textY + 5);
            }
            textY += lineHeight;
            g2.drawString("Crit: " + playerCrit + "%", playerX, textY + 5);
            textY += lineHeight;
            g2.drawString("Hit: " + playerHitRate + "%", playerX, textY + 5);

            // Calculate and draw enemy's stats on the right side
            if (enemy.getAttackType() == Entity.AttackType.Physical) {
                enemyWeaponName = enemy.equippedWeapon.getName();
                enemyAttack = enemy.getMight() - gp.selectedUnit.getEffDefense();
                if (enemy.equippedWeapon.getEffectiveRace() == gp.selectedUnit.getRace() ) {
                    enemyAttack = 3 * enemy.equippedWeapon.getMight() + enemy.getEffStrength() - gp.selectedUnit.getEffDefense();
                }
                if (enemy.equippedWeapon.getEffectiveType() == gp.selectedUnit.getUnitType() ) {
                    enemyAttack = 3 * enemy.equippedWeapon.getMight() + enemy.getEffStrength() - gp.selectedUnit.getEffDefense();
                }
            } else if (enemy.getAttackType() == Entity.AttackType.Magical) {
                enemyWeaponName = enemy.attackSpell.getName();
                enemyAttack = enemy.getMight() - gp.selectedUnit.getEffResistance();
            }
            if (enemyAttack < 0) {
                enemyAttack = 0;
            }

            enemyHitRate = enemy.getHitRate() - gp.selectedUnit.getEvade();
            if (enemyHitRate > 100) {
                enemyHitRate = 100;
            } else if (enemyHitRate < 0) {
                enemyHitRate = 0;
            }

            enemyCrit = enemy.getCritical() - gp.selectedUnit.getLuck();
            if (enemyCrit > 100) {
                enemyCrit = 100;
            } else if (enemyCrit < 0) {
                enemyCrit = 0;
            }

            textY = y + padding; // Reset textY for the enemy's stats
            if (enemy.getName() != null) {
                g2.drawString(enemy.getName(), enemyX, textY + 5);
            } else if (enemy.getName() == null) {
                g2.drawString(enemy.getClassName(), enemyX, textY + 5);
            }
            textY += lineHeight;
            g2.drawString(enemyWeaponName, enemyX, textY + 5);
            textY += lineHeight;
            if (inRange) {
                if ( enemy.getEffSpeed() - gp.selectedUnit.getEffSpeed() >=  5) {
                    g2.drawString("Attack: " + enemyAttack + " x 2", enemyX, textY + 5);
                } else {
                    g2.drawString("Attack: " + enemyAttack, enemyX, textY + 5);
                }
                textY += lineHeight;
                g2.drawString("Crit: " + enemyCrit + "%", enemyX, textY + 5);
                textY += lineHeight;
                g2.drawString("Hit: " + enemyHitRate + "%", enemyX, textY + 5);
            } else if (!inRange) {
                g2.drawString("Attack: --", enemyX, textY + 5);
                textY += lineHeight;
                g2.drawString("Crit: --", enemyX, textY + 5);
                textY += lineHeight;
                g2.drawString("Hit: --", enemyX, textY + 5);
            }

            // Draw horizontal lines to separate each stat (skip the last line)
            for (int i = 1; i <= 5; i++) { // 5 lines for separating stats
                int lineY = y + padding + i * lineHeight - lineHeight / 2;
                g2.drawLine(x + padding, lineY, x + width - padding, lineY);
            }

            // Draw vertical line between player and enemy stats
            int verticalLineX = x + width / 2;
            g2.drawLine(verticalLineX, y + padding - 10, verticalLineX, y + height - padding - 70);

            // Draw control instructions
            int bottomTextY = y + height - padding;
            g2.drawString("Press Z to cancel", x + padding, bottomTextY);
            bottomTextY -= 20;
            g2.drawString("Press ← or → to switch enemies", x + padding, bottomTextY);
            bottomTextY -= 20;
            g2.drawString("Press SPACE to attack", x + padding, bottomTextY);
        }
    }

    // Healing Foecast
    public void drawHealingForecast() {
        int x = 52 * 16;
        int y = 37 * 16;
        int width = 32 * 16;
        int height = 15 * 16;

        // Draw the background rectangle
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(x, y, width, height, 25, 25);

        // Set the color and font for the text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));

        // Get the FontMetrics to calculate string widths
        FontMetrics fm = g2.getFontMetrics();

        // Define the control instructions
        String[] instructions = {
                "Press Z to cancel",
                "Press ← or → to switch ally",
                "Press SPACE to heal"
        };

        // Draw the control instructions centered
        int bottomTextY = y + height/2 ;
        for (String text : instructions) {
            int textWidth = fm.stringWidth(text);  // Get the width of the text
            int textX = x + (width - textWidth) / 2;  // Center the text horizontally
            g2.drawString(text, textX, bottomTextY);
            bottomTextY -= 20;  // Move up for the next line
        }
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
        // Checks if player is null because if i unselect player i get error
        if (player != null) {
            // Coordinates for text next to the portrait
            int textX = 52 * 16 + 8 * gp.getTileSize() + 10; // Right side of the portrait
            int textY = 5 * 16 + 20; // Slightly below the top of the portrait
            int lineHeight = 20; // Spacing between each line of text
            int nextLine = 1;

            // Set font for text
            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            g2.setColor(Color.WHITE);

            // Draw unit's name, class name, and level next to the portrait
            g2.drawString(player.getName(), textX, textY);
            g2.drawString(player.getClassName(), textX, textY + nextLine * lineHeight);
            nextLine++;
            g2.drawString(String.valueOf(player.getRace()), textX, textY + nextLine * lineHeight);
            nextLine++;
            if (player.getUnitType() == Entity.UnitType.Armored) {
                g2.drawString("Armored", textX, textY + nextLine * lineHeight);
                nextLine++;
            }
            if (player.getUnitType() == Entity.UnitType.Cavalry) {
                g2.drawString("Cavalry", textX, textY + nextLine * lineHeight);
                nextLine++;
            }
            g2.drawString("Level: " + player.getLevel(), textX, textY + nextLine * lineHeight);
            nextLine++;
            if (player.getLevel() < player.getMaxLevel()) {
                g2.drawString("Exp: " + player.getExp() + " / " + player.getMaxExp(), textX, textY + nextLine * lineHeight);
            }
            else {
                g2.drawString("--", textX, textY + nextLine * lineHeight);
            }

            // Set font for text
            g2.setFont(new Font("Arial", Font.PLAIN, 15));

            // Coordinates for stats below the portrait
            int statsX = 52 * 16 + 5; // Align with the portrait's X position
            int statsX2 = textX + 10; // Right side stats
            int statsY = 5 * 16 + 8 * gp.getTileSize() + 20; // Start drawing below the portrait

            // Draw the unit's stats below the portrait
            g2.drawString("HP: " + player.getHP() + " / " + player.getMaxHP(), statsX, statsY);

            g2.drawString("Movement: " + player.getMovementInitial(), statsX2, statsY);

            if (player.getBonusStrength() == 0){
                g2.drawString("Strength: " + player.getStrength(), statsX, statsY + lineHeight);
            }
            else if (player.getBonusStrength() > 0){
                g2.drawString("Strength: " + player.getStrength() + " + " + player.getBonusStrength(), statsX, statsY + lineHeight);
            }
            else if (player.getBonusStrength() < 0){
                g2.drawString("Strength: " + player.getStrength() + " - " + Math.abs(player.getBonusStrength()), statsX, statsY + lineHeight);
            }

            if (player.getBonusSkill() == 0){
                g2.drawString("Skill: " + player.getSkill(), statsX2, statsY + lineHeight);
            }
            else if (player.getBonusSkill() > 0){
                g2.drawString("Skill: " + player.getSkill() + " + " + player.getBonusSkill(), statsX2, statsY + lineHeight);
            }
            else if (player.getBonusSkill() < 0){
                g2.drawString("Skill: " + player.getSkill() + " - " + Math.abs(player.getBonusSkill()), statsX2, statsY + lineHeight);
            }

            if (player.getBonusMagic() == 0){
                g2.drawString("Magic: " + player.getMagic(), statsX, statsY + 2 * lineHeight);
            }
            else if (player.getBonusMagic() > 0){
                g2.drawString("Magic: " + player.getMagic() + " + " + player.getBonusMagic(), statsX, statsY + 2 * lineHeight);
            }
            else if (player.getBonusMagic() < 0){
                g2.drawString("Magic: " + player.getMagic() + " - " + Math.abs(player.getBonusMagic()), statsX, statsY + 2 * lineHeight);
            }

            if (player.getBonusSpeed() == 0){
                g2.drawString("Speed: " + player.getSpeed(), statsX2, statsY + 2 * lineHeight);
            }
            else if (player.getBonusSpeed() > 0){
                g2.drawString("Speed: " + player.getSpeed() + " + " + player.getBonusSpeed(), statsX2, statsY + 2 * lineHeight);
            }
            else if (player.getBonusSpeed() < 0){
                g2.drawString("Speed: " + player.getSpeed() + " - " + Math.abs(player.getBonusSpeed()), statsX2, statsY + 2 * lineHeight);
            }

            if (player.getBonusDefense() == 0){
                g2.drawString("Defense: " + player.getDefense(), statsX, statsY + 3 * lineHeight);
            }
            else if (player.getBonusDefense() > 0){
                g2.drawString("Defense: " + player.getDefense() + " + " + player.getBonusDefense(), statsX, statsY + 3 * lineHeight);
            }
            else if (player.getBonusDefense() < 0){
                g2.drawString("Defense: " + player.getDefense() + " - " + Math.abs(player.getBonusDefense()), statsX, statsY + 3 * lineHeight);
            }

            g2.drawString("Luck: " + player.getLuck(), statsX2, statsY + 3 * lineHeight);

            if (player.getBonusResistance() == 0){
                g2.drawString("Resistance: " + player.getResistance(), statsX, statsY + 4 * lineHeight);
            }
            else if (player.getBonusResistance() > 0){
                g2.drawString("Resistance: " + player.getResistance() + " + " + player.getBonusResistance(), statsX, statsY + 4 * lineHeight);
            }
            else if (player.getBonusResistance() < 0){
                g2.drawString("Resistance: " + player.getResistance() + " - " + Math.abs(player.getBonusResistance()), statsX, statsY + 4 * lineHeight);
            }

            if (player.getBonusVision() == 0){
                g2.drawString("Vision: " + player.getVision(), statsX2, statsY + 4 * lineHeight);
            }
            else if (player.getBonusVision() > 0){
                g2.drawString("Vision: " + player.getVision() + " + " + player.getBonusVision(), statsX2, statsY + 4 * lineHeight );
            }
            else if (player.getBonusVision() < 0){
                g2.drawString("Vision: " + player.getVision() + " - " + Math.abs(player.getBonusVision()), statsX2, statsY + 4 * lineHeight );
            }
        }
    }

    public void drawLightUnitDetails (LightUnit player) {
        // Checks if player is null because if i unselect player i get error
        if (player != null) {
            int textX = 52 * 16 + 8 * gp.getTileSize() + 4; // Right side of the portrait
            int textY = 5 * 16 + 20; // Slightly below the top of the portrait
            int lineHeight = 20; // Spacing between each line of text
            int nextLine = 1;

            // Coordinates for stats below the portrait
            int statsX = 52 * 16 + 5; // Align with the portrait's X position
            int statsY = 5 * 16 + 8 * gp.getTileSize() + 20; // Start drawing below the portrait

            // Set font for text
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.setColor(Color.WHITE);

            // Draw unit's description line by line
            for (String line : player.getDescription()) {
                g2.drawString(line, textX, textY);
                textY += lineHeight;
            }

            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            lineHeight = 30;

            // Draw Inventory if Unit is Physical
            if (player.getAttackType() == Entity.AttackType.Physical) {
                Weapon mainhand = player.mainHand;
                Weapon offhand = player.offHand;

                g2.drawString("Mainhand: ", statsX, statsY + nextLine * lineHeight);

                // Check if the mainhand weapon is null
                if (mainhand != null) {
                    g2.drawImage(mainhand.getImage(), statsX + 75, statsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawString(mainhand.getName(), statsX + 95, statsY + nextLine * lineHeight);
                    nextLine++;
                    g2.setFont(new Font("Arial", Font.PLAIN, 14));
                    g2.drawString("Might: " + mainhand.getMight() + "  Hit: " + mainhand.getHit() + "  Crit: " + mainhand.getCrit() + "  Range: " + mainhand.getRange(), statsX, statsY + nextLine * lineHeight);
                    if (mainhand.getDescription() != null) {
                        nextLine++;
                        g2.drawString(mainhand.getDescription(), statsX, statsY + nextLine * lineHeight);
                    }
                }

                g2.setFont(new Font("Arial", Font.PLAIN, 16));
                nextLine++;
                g2.drawString("Offhand: ", statsX, statsY + nextLine * lineHeight);

                // Check if the offhand weapon is null
                if (offhand != null) {
                    g2.drawImage(offhand.getImage(), statsX + 60, statsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawString(offhand.getName(), statsX + 80, statsY + nextLine * lineHeight);
                    nextLine++;
                    g2.setFont(new Font("Arial", Font.PLAIN, 14));
                    g2.drawString("Might: " + offhand.getMight() + "  Hit: " + offhand.getHit() + "  Crit: " + offhand.getCrit() + "  Range: " + offhand.getRange(), statsX, statsY + nextLine * lineHeight);
                    if (offhand.getDescription() != null) {
                        nextLine++;
                        g2.drawString(offhand.getDescription(), statsX, statsY + nextLine * lineHeight);
                    }
                }
            }

            // Draw Spells if Unit is Magical
            if (player.getAttackType() == Entity.AttackType.Magical) {
                // Drawing unit's spells
                AttackSpell attackSpell = player.attackSpell;
                HealingSpell healingSpell = player.healingSpell;

                g2.setFont(new Font("Arial", Font.PLAIN, 16));
                g2.drawString("Attack Spell: ", statsX, statsY + nextLine * lineHeight);

                // Check if the attack spell is null
                if (attackSpell != null) {
                    g2.drawImage(attackSpellImage, statsX + 85, statsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawString(attackSpell.getName(), statsX + 105, statsY + nextLine * lineHeight);
                    nextLine++;
                    g2.setFont(new Font("Arial", Font.PLAIN, 14));
                    g2.drawString("Might: " + attackSpell.getMight() + "  Hit: " + attackSpell.getHit() + "  Crit: " + attackSpell.getCrit() + "  Range: " + attackSpell.getRange(), statsX, statsY + nextLine * lineHeight);
                }

                nextLine++;
                g2.setFont(new Font("Arial", Font.PLAIN, 16));
                g2.drawString("Healing Spell: ", statsX, statsY + nextLine * lineHeight);

                // Check if the healing spell is null
                if (healingSpell != null) {
                    g2.drawImage(healingSpellImage, statsX + 95, statsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawString(healingSpell.getName(), statsX + 115, statsY + nextLine * lineHeight);
                    nextLine++;
                    g2.setFont(new Font("Arial", Font.PLAIN, 14));
                    g2.drawString("Heal: " + healingSpell.getHeal()  + "   Range: " + healingSpell.getRange(), statsX, statsY + nextLine * lineHeight);
                }
            }

            Trinket trinket = player.trinket;
            Potion potion = player.potion;

            nextLine++;
            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            g2.drawString("Trinket: ", statsX, statsY + nextLine * lineHeight);

            // Check if the healing spell is null
            if (trinket != null) {
                g2.drawImage(trinket.getImage(), statsX + 50, statsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawString(trinket.getName(), statsX + 70, statsY + nextLine * lineHeight);
                nextLine++;
                g2.setFont(new Font("Arial", Font.PLAIN, 14));
                if (trinket.getDescription() != null) {
                    g2.drawString(trinket.getDescription(), statsX, statsY + nextLine * lineHeight);
                }
            }

            nextLine++;
            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            g2.drawString("Potion: ", statsX, statsY + nextLine * lineHeight);

            // Check if the healing spell is null
            if (potion != null) {
                g2.drawImage(potion.getImage(), statsX + 50, statsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawString(potion.getName(), statsX + 70, statsY + nextLine * lineHeight);
                nextLine++;
                g2.setFont(new Font("Arial", Font.PLAIN, 14));
                if (potion.getDescription() != null) {
                    g2.drawString(potion.getDescription(), statsX, statsY + nextLine * lineHeight);
                }
            }
        }
    }

    public void drawLightUnitCombatStats(LightUnit player) {
        // Checks if player is null because if i unselect player i get error
        if (player != null) {
            // Coordinates for combat stats text
            int lineHeight = 20; // Spacing between each line of text
            int textX = 52 * 16 + 8 * gp.getTileSize() + 10;
            int nextLine = 1;

            // Set font for text
            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            g2.setColor(Color.WHITE);

            // Coordinates for combat stats below the portrait
            int combatStatsX = 52 * 16 + 5; // Align with the portrait's X position
            int combatStatsY = 5 * 16 + 8 * gp.getTileSize() + 140; // Start drawing below the portrait

            // Draw the unit's combat stats below the portrait
            g2.drawString("Might: " + player.getMight(), combatStatsX, combatStatsY);
            g2.drawString("Crit Rate: " + player.getCritical(), textX, combatStatsY);
            g2.drawString("Hit Rate: " + player.getHitRate(), combatStatsX, combatStatsY + lineHeight);
            g2.drawString("Evade: " + player.getEvade(), textX, combatStatsY + lineHeight);

            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            lineHeight = 30;

            // Draw Inventory if Unit is Physical
            if (player.getAttackType() == Entity.AttackType.Physical) {
                // Start rendering weapon and item details
                nextLine++;

                // Drawing equipped weapon
                Weapon equippedWeapon = player.equippedWeapon;
                Weapon mainhand = player.mainHand;
                Weapon offhand = player.offHand;

                if (equippedWeapon != null) {
                    g2.drawString("Equipped: ", combatStatsX, combatStatsY + nextLine * lineHeight);
                    g2.drawImage(equippedWeapon.getImage(), combatStatsX + 75, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawString(equippedWeapon.getName(), combatStatsX + 95, combatStatsY + nextLine * lineHeight);
                }

                // Check if the equipped weapon is the mainhand and there is an offhand weapon
                if (equippedWeapon == mainhand && offhand != null) {
                    nextLine++;
                    g2.drawImage(offhand.getImage(), combatStatsX, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawString(offhand.getName(), combatStatsX + 20, combatStatsY + nextLine * lineHeight);
                }
                // Check if the equipped weapon is the offhand and there is a mainhand weapon
                else if (equippedWeapon == offhand && mainhand != null) {
                    nextLine++;
                    g2.drawImage(mainhand.getImage(), combatStatsX, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawString(mainhand.getName(), combatStatsX + 20, combatStatsY + nextLine * lineHeight);
                }
            }

            // Draw Spells if Unit is Magical
            if (player.getAttackType() == Entity.AttackType.Magical) {
                // Start rendering weapon and item details
                nextLine++;

                // Drawing unit's spells
                AttackSpell attackSpell = player.attackSpell;
                HealingSpell healingSpell = player.healingSpell;

                if (attackSpell != null) {
                    g2.drawImage(attackSpellImage, combatStatsX, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawString(attackSpell.getName(), combatStatsX + 20, combatStatsY + nextLine * lineHeight);
                }

                if (healingSpell != null) {
                    nextLine++;
                    g2.drawImage(healingSpellImage, combatStatsX, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                    g2.drawString(healingSpell.getName(), combatStatsX + 20, combatStatsY + nextLine * lineHeight);
                }
            }

            if (player.getBeaconOfLight()){
                nextLine++;
                g2.drawImage(healingSpellImage, combatStatsX, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawString("Beacon Of Light", combatStatsX + 20, combatStatsY + nextLine * lineHeight);
            }

            Trinket trinket = player.trinket;
            Potion potion = player.potion;

            if (trinket != null) {
                nextLine++;
                g2.drawImage(trinket.getImage(), combatStatsX, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawString(trinket.getName(), combatStatsX + 20, combatStatsY + nextLine * lineHeight);
            }

            if (potion != null) {
                nextLine++;
                g2.drawImage(potion.getImage(), combatStatsX, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawString(potion.getName() + "         Uses: " + potion.getUses(), combatStatsX + 20, combatStatsY + nextLine * lineHeight);
            }
        }
    }


    // Method to draw the Light Unit's information
    public void drawLightUnitInfo() {
        if (gp.TurnM.getPlayerPhase()) {
            if (gp.selectedUnit == null || (gp.selectedUnit != null && !gp.selectedUnit.getIsMoving() && gp.selectedUnit.getIsHealing())) {
                LightUnit player = gp.cChecker.getPlayerOnTile(gp.cursor.getCol(), gp.cursor.getRow());
                if (player != null) {
                    drawLightUnitPortrait(player);
                    if (keyH.isQPressed()) {
                        drawLightUnitStats(player);
                        drawLightUnitCombatStats(player);
                    } else {
                        drawLightUnitDetails(player);
                    }
                    if (gp. selectedUnit != null && !gp.selectedUnit.getIsMoving() && gp.selectedUnit.getIsHealing()) {
                        drawHealingForecast();
                    }
                }
            } else if (gp.selectedUnit != null) {
                drawLightUnitPortrait(gp.selectedUnit);
                if (keyH.isQPressed()) {
                    drawLightUnitStats(gp.selectedUnit);
                    drawLightUnitCombatStats(gp.selectedUnit);
                } else {
                    drawLightUnitDetails(gp.selectedUnit);
                }
                if (gp. selectedUnit != null && !gp.selectedUnit.getIsMoving() && gp.selectedUnit.getIsAttacking()) {
                    drawCombatForecast();
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
        int nextLine = 0;

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.setColor(Color.WHITE);

        // Draw unit's name, class name, and level next to the portrait
        if (enemy.getName() != null) {
            g2.drawString(enemy.getName(), textX, textY + nextLine * lineHeight);
            nextLine++;
        }
        g2.drawString(enemy.getClassName(), textX, textY + nextLine * lineHeight);
        nextLine++;
        if (enemy.isBoss()) {
            g2.drawString("Boss", textX, textY + nextLine * lineHeight);
            nextLine++;
        }
        g2.drawString(String.valueOf(enemy.getRace()), textX, textY + nextLine * lineHeight);
        nextLine++;
        if (enemy.getUnitType() == Entity.UnitType.Armored) {
            g2.drawString("Armored", textX, textY + nextLine * lineHeight);
            nextLine++;
        }
        if (enemy.getUnitType() == Entity.UnitType.Cavalry) {
            g2.drawString("Cavalry", textX, textY + nextLine * lineHeight);
            nextLine++;
        }
        g2.drawString("Level: " + enemy.getLevel(), textX, textY + nextLine * lineHeight);

        // Coordinates for stats below the portrait
        int statsX = 69 * 16 - 5; // Align with the portrait's X position
        int statsX2 = textX + 10; // Right side stats
        int statsY = 5 * 16 + 8 * gp.getTileSize() + 20; // Start drawing below the portrait

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 15));

        // Draw the unit's stats below the portrait
        g2.drawString("HP: " + enemy.getHP() + " / " + enemy.getMaxHP(), statsX, statsY);

        g2.drawString("Movement: " + enemy.getMovement(), statsX2, statsY);

        if (enemy.getBonusStrength() == 0){
            g2.drawString("Strength: " + enemy.getStrength(), statsX, statsY + lineHeight);
        }
        else if (enemy.getBonusStrength() > 0){
            g2.drawString("Strength: " + enemy.getStrength() + " + " + enemy.getBonusStrength(), statsX, statsY + lineHeight);
        }
        else if (enemy.getBonusStrength() < 0){
            g2.drawString("Strength: " + enemy.getStrength() + " - " + Math.abs(enemy.getBonusStrength()), statsX, statsY + lineHeight);
        }

        if (enemy.getBonusSkill() == 0){
            g2.drawString("Skill: " + enemy.getSkill(), statsX2, statsY + lineHeight);
        }
        else if (enemy.getBonusSkill() > 0){
            g2.drawString("Skill: " + enemy.getSkill() + " + " + enemy.getBonusSkill(), statsX2, statsY + lineHeight);
        }
        else if (enemy.getBonusSkill() < 0){
            g2.drawString("Skill: " + enemy.getSkill() + " - " + Math.abs(enemy.getBonusSkill()), statsX2, statsY + lineHeight);
        }

        if (enemy.getBonusMagic() == 0){
            g2.drawString("Magic: " + enemy.getMagic(), statsX, statsY + 2 * lineHeight);
        }
        else if (enemy.getBonusMagic() > 0){
            g2.drawString("Magic: " + enemy.getMagic() + " + " + enemy.getBonusMagic(), statsX, statsY + 2 * lineHeight);
        }
        else if (enemy.getBonusMagic() < 0){
            g2.drawString("Magic: " + enemy.getMagic() + " - " + Math.abs(enemy.getBonusMagic()), statsX, statsY + 2 * lineHeight);
        }

        if (enemy.getBonusSpeed() == 0){
            g2.drawString("Speed: " + enemy.getSpeed(), statsX2, statsY + 2 * lineHeight);
        }
        else if (enemy.getBonusSpeed() > 0){
            g2.drawString("Speed: " + enemy.getSpeed() + " + " + enemy.getBonusSpeed(), statsX2, statsY + 2 * lineHeight);
        }
        else if (enemy.getBonusSpeed() < 0){
            g2.drawString("Speed: " + enemy.getSpeed() + " - " + Math.abs(enemy.getBonusSpeed()), statsX2, statsY + 2 * lineHeight);
        }

        if (enemy.getBonusDefense() == 0){
            g2.drawString("Defense: " + enemy.getDefense(), statsX, statsY + 3 * lineHeight);
        }
        else if (enemy.getBonusDefense() > 0){
            g2.drawString("Defense: " + enemy.getDefense() + " + " + enemy.getBonusDefense(), statsX, statsY + 3 * lineHeight);
        }
        else if (enemy.getBonusDefense() < 0){
            g2.drawString("Defense: " + enemy.getDefense() + " - " + Math.abs(enemy.getBonusDefense()), statsX, statsY + 3 * lineHeight);
        }

        g2.drawString("Luck: " + enemy.getLuck(), statsX2, statsY + 3 * lineHeight);

        if (enemy.getBonusResistance() == 0){
            g2.drawString("Resistance: " + enemy.getResistance(), statsX, statsY + 4 * lineHeight);
        }
        else if (enemy.getBonusResistance() > 0){
            g2.drawString("Resistance: " + enemy.getResistance() + " + " + enemy.getBonusResistance(), statsX, statsY + 4 * lineHeight);
        }
        else if (enemy.getBonusResistance() < 0){
            g2.drawString("Resistance: " + enemy.getResistance() + " - " + Math.abs(enemy.getBonusResistance()), statsX, statsY + 4 * lineHeight);
        }
    }

    public void drawChaosUnitCombatStats(ChaosUnit enemy) {
        // Coordinates for combat stats text
        int lineHeight = 20; // Spacing between each line of text
        int textX = 69 * 16 + 7 * gp.getTileSize() + 14;
        int nextLine = 1;

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.setColor(Color.WHITE);

        // Coordinates for combat stats below the portrait
        int combatStatsX = 69 * 16 - 5; // Align with the portrait's X position
        int combatStatsY = 5 * 16 + 8 * gp.getTileSize() + 140; // Start drawing below the portrait

        // Draw the unit's combat stats below the portrait
        g2.drawString("Might: " + enemy.getMight(), combatStatsX, combatStatsY);
        g2.drawString("Crit Rate: " + enemy.getCritical(), textX, combatStatsY);
        g2.drawString("Hit Rate: " + enemy.getHitRate(), combatStatsX, combatStatsY + lineHeight);
        g2.drawString("Evade: " + enemy.getEvade(), textX, combatStatsY + lineHeight);

        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        lineHeight = 30;

        // Draw Inventory if Unit is Physical
        if (enemy.getAttackType() == Entity.AttackType.Physical) {
            // Start rendering weapon and item details
            nextLine++;

            // Drawing equipped weapon
            Weapon equippedWeapon = enemy.equippedWeapon;

            if (equippedWeapon != null) {
                g2.drawString("Equipped: ", combatStatsX, combatStatsY + nextLine * lineHeight);
                g2.drawImage(equippedWeapon.getImage(), combatStatsX + 75, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawString(equippedWeapon.getName(), combatStatsX + 95, combatStatsY + nextLine * lineHeight);
            }
        }

        // Draw Spells if Unit is Magical
        if (enemy.getAttackType() == Entity.AttackType.Magical) {
            // Start rendering weapon and item details
            nextLine++;

            // Drawing unit's spells
            AttackSpell attackSpell = enemy.attackSpell;

            if (attackSpell != null) {
                g2.drawImage(attackSpellImage, combatStatsX, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawString(attackSpell.getName(), combatStatsX + 20, combatStatsY + nextLine * lineHeight);
            }
        }

        Trinket trinket = enemy.trinket;

        if (trinket != null) {
            nextLine++;
            g2.drawImage(trinket.getImage(), combatStatsX, combatStatsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
            g2.drawString(trinket.getName(), combatStatsX + 20, combatStatsY + nextLine * lineHeight);
        }
    }

    // Draw Chaos Unit's details
    public void drawChaosUnitDetails (ChaosUnit enemy) {
        int textX = 69 * 16 + 7 * gp.getTileSize() + 9; // Right side of the portrait
        int textY = 5 * 16 + 20; // Slightly below the top of the portrait
        int lineHeight = 20; // Spacing between each line of text
        int nextLine = 1;

        // Coordinates for stats below the portrait
        int statsX = 69 * 16 - 5; // Align with the portrait's X position
        int statsY = 5 * 16 + 8 * gp.getTileSize() + 20; // Start drawing below the portrait

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 13));
        g2.setColor(Color.WHITE);

        // Draw unit's description line by line
        for (String line : enemy.getDescription()) {
            g2.drawString(line, textX, textY);
            textY += lineHeight;
        }

        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        lineHeight = 30;

        // Draw Inventory if Unit is Physical
        if (enemy.getAttackType() == Entity.AttackType.Physical) {
            Weapon equipped  = enemy.equippedWeapon;

            g2.drawString("Equipped: ", statsX, statsY + nextLine * lineHeight);

            // Check if the mainhand weapon is null
            if (equipped != null) {
                g2.drawImage(equipped.getImage(), statsX + 75, statsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawString(equipped.getName(), statsX + 95, statsY + nextLine * lineHeight);
                nextLine++;
                g2.setFont(new Font("Arial", Font.PLAIN, 14));
                g2.drawString("Might: " + equipped.getMight() + "  Hit: " + equipped.getHit() + "  Crit: " + equipped.getCrit() + "  Range: " + equipped.getRange(), statsX, statsY + nextLine * lineHeight);
                nextLine++;
                if (equipped.getDescription() != null) {
                    g2.drawString(equipped.getDescription(), statsX, statsY + nextLine * lineHeight);
                }
            }
        }

        // Draw Spells if Unit is Magical
        if (enemy.getAttackType() == Entity.AttackType.Magical) {
            // Drawing unit's spells
            AttackSpell attackSpell = enemy.attackSpell;

            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            g2.drawString("Attack Spell: ", statsX, statsY + nextLine * lineHeight);

            // Check if the attack spell is null
            if (attackSpell != null) {
                g2.drawImage(attackSpellImage, statsX + 85, statsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
                g2.drawString(attackSpell.getName(), statsX + 105, statsY + nextLine * lineHeight);
                nextLine++;
                g2.setFont(new Font("Arial", Font.PLAIN, 14));
                g2.drawString("Might: " + attackSpell.getMight() + "  Hit: " + attackSpell.getHit() + "  Crit: " + attackSpell.getCrit() + "  Range: " + attackSpell.getRange(), statsX, statsY + nextLine * lineHeight);
            }
        }

        Trinket trinket = enemy.trinket;

        nextLine++;
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("Trinket: ", statsX, statsY + nextLine * lineHeight);

        // Check if the healing spell is null
        if (trinket != null) {
            g2.drawImage(trinket.getImage(), statsX + 50, statsY + nextLine * lineHeight - 13, gp.getTileSize(), gp.getTileSize(), null);
            g2.drawString(trinket.getName(), statsX + 70, statsY + nextLine * lineHeight);
            nextLine++;
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            if (trinket.getDescription() != null) {
                g2.drawString(trinket.getDescription(), statsX, statsY + nextLine * lineHeight);
            }
        }
    }


    // Method to draw the Light Unit's information
    public void drawChaosUnitInfo() {
        if (gp.TurnM.getPlayerPhase()) {
            ChaosUnit enemy = gp.cChecker.getEnemyOnTile(gp.cursor.getCol(), gp.cursor.getRow());
            if (enemy != null) {
                drawChaosUnitPortrait(enemy);
                if (keyH.isQPressed()) {
                    drawChaosUnitStats(enemy);
                    drawChaosUnitCombatStats(enemy);
                } else {
                    drawChaosUnitDetails(enemy);
                }
            }
        }
    }

    // ITEMS UI

    public void drawTileItems() {
        if (gp.TurnM.getPlayerPhase()) {
            int frameX = gp.getTileSize() * 16;
            int frameY = gp.getTileSize() * 13;
            int frameWidth = gp.getTileSize() * 22;
            int frameHeight = gp.getTileSize() * 18;

            // Item Slot
            final int slotXstart = frameX + 20;
            final int slotYstart = frameY + 20;
            int slotX = slotXstart;
            int slotY = slotYstart;

            // Cursor
            int cursorX = slotXstart + (gp.getTileSize() * slotCol);
            int cursorY = slotYstart + (gp.getTileSize() * slotRow);
            int cursorWidth = 3 * gp.getTileSize();
            int cursorHeight = 3 * gp.getTileSize();

            // Items list
            List<Item> tileItems = getItemsAtTile();

            // Only toggle the window when the V key is first pressed, not held down
            if (keyH.isVPressed() && !vKeyPressed) {
                vKeyPressed = true;  // Mark the V key as pressed
                gp.playSE(6);
                if (!tileItems.isEmpty()) {
                    gp.tileM.setItemWindowOpen(!gp.tileM.isItemWindowOpen()); // Toggle the item window state
                } else {
                    addLogMessage("No items on this tile");
                }
            }

            // Reset the flag when the V key is released
            if (!keyH.isVPressed()) {
                vKeyPressed = false;
            }

            // Draw the window if it is open
            if (gp.tileM.isItemWindowOpen()) {

                drawSubWindow(g2, frameX, frameY, frameWidth, frameHeight);
                // Draw Cursor
                g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(cursorX, cursorY, cursorWidth, cursorHeight);

                // Draw Tile's Items
                for (int i = 0; i < tileItems.size(); i++) {
                    g2.drawImage(tileItems.get(i).getImage(), slotX, slotY, cursorWidth, cursorHeight, null);
                    slotX += 4 * gp.getTileSize();
                    if (i == 4 || i == 9 || i == 14) {
                        slotX = slotXstart;
                        slotY += 4 * gp.getTileSize();
                    }
                }

                // Draw description window
                int dFrameX = frameX;
                int dFrameY = frameY + frameHeight + 10;
                int dFrameWidth = frameWidth;
                int dFrameHeight = 16 * gp.getTileSize();

                // Draw item description
                int textX = dFrameX + 20;
                int textY = dFrameY + 2 * gp.getTileSize();
                int nextLine = 0;
                g2.setFont(arial_20);
                drawSubWindow(g2, dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                Item selectedItem = getSelectedItem();
                if (selectedItem  != null) {
                    if (selectedItem  instanceof MainHand) {
                        g2.drawString("Mainhand: " + selectedItem.getName(), textX, textY);
                        nextLine++;
                    }
                    else if (selectedItem instanceof OffHand) {
                        g2.drawString("Offhand: " + selectedItem.getName(), textX, textY);
                        nextLine++;
                    }
                    else if (selectedItem instanceof Trinket) {
                        g2.drawString("Trinket: " + selectedItem.getName(), textX, textY);
                        nextLine++;
                    }
                    else if (selectedItem instanceof Potion) {
                        g2.drawString("Potion: " + selectedItem.getName(), textX, textY);
                        nextLine++;
                    }
                    if (selectedItem instanceof Weapon) {
                        Weapon weapon = (Weapon) selectedItem;
                        g2.drawString("Might: " + weapon.getMight() + "  Hit: " + weapon.getHit() + "  Crit: " + weapon.getCrit() + "  Range: " + weapon.getRange(), textX, textY + nextLine * 2 * gp.getTileSize());
                        nextLine++;
                    }
                    if (selectedItem.getDescription() != null) {
                        g2.drawString(selectedItem.getDescription(), textX, textY + nextLine * 2 * gp.getTileSize());
                    }
                }

                nextLine = 4;
                g2.drawString("Press ↑, ↓, ←, → to switch items", textX, textY + nextLine * 2 * gp.getTileSize());
                nextLine++;
                if (gp.selectedUnit != null) {
                    g2.drawString("Press SPACE to take item", textX, textY + nextLine * 2 * gp.getTileSize());
                    nextLine++;
                }
                g2.drawString("Press V to close item window", textX, textY + nextLine * 2 * gp.getTileSize());

                // Increment the delay counter
                moveDelayCounter++;

                // Only move the cursor when the delay counter reaches the threshold
                if (moveDelayCounter >= moveDelayThreshold) {
                    // Determine if any cursor movement key is pressed
                    if (keyH.isUpPressed() && !moving) {
                        if (slotRow != 0) {
                            slotRow -= 4;
                            gp.playSE(0);
                            moving = true;
                        }
                    } else if (keyH.isDownPressed() && !moving) {
                        if (slotRow != 12) {
                            slotRow += 4;
                            gp.playSE(0);
                            moving = true;
                        }
                    } else if (keyH.isLeftPressed() && !moving) {
                        if (slotCol != 0) {
                            slotCol -= 4;
                            gp.playSE(0);
                            moving = true;
                        }
                    } else if (keyH.isRightPressed() && !moving) {
                        if (slotCol != 16) {
                            slotCol += 4;
                            gp.playSE(0);
                            moving = true;
                        }
                    }

                    // Reset the moving flag if the key is released or tile movement is complete
                    if (!keyH.isUpPressed() || !keyH.isDownPressed() || !keyH.isLeftPressed() || !keyH.isRightPressed()) {
                        moving = false;
                    }

                    // Reset the delay counter after moving
                    moveDelayCounter = 0;
                }
            } else {
                slotCol = 0;
                slotRow = 0;
            }
        } else {
            gp.tileM.setItemWindowOpen(false);
        }
    }

    public int getItemOnSlot() {
        int itemIndex = slotCol/4 + (slotRow * 5)/4;
        return itemIndex;
    }

    public Item getSelectedItem() {
        // Get the index of the selected item using slotCol and slotRow
        int itemIndex = getItemOnSlot();

        // Fetch the list of items at the current tile
        List<Item> tileItems = getItemsAtTile();

        // Return the selected item if the index is valid
        if (itemIndex >= 0 && itemIndex < tileItems.size()) {
            return tileItems.get(itemIndex);
        } else {
            return null; // Return null if no valid item is selected
        }
    }

    // Method to get items from the tile where the cursor is located
    public List<Item> getItemsAtTile() {
        // Get cursor position (assuming you have a way to get the cursor position, col and row)
        int col = gp.cursor.getCol();
        int row = gp.cursor.getRow();

        // Use the TileManager's method to get items from the specified tile
        return gp.tileM.getTileItems(col, row);
    }

    public void drawSubWindow(Graphics2D g2, int x, int y, int width, int height){
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }


    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_30);
        g2.setColor(Color.WHITE);

        // INTRO
        if (gp.gameState == gp.introState) {
            drawIntro();
        }

        // INTRO 2
        if (gp.gameState == gp.introState2) {
            drawIntro2();
        }

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
            drawTileItems();
        }

        // CREDITS
        if (gp.gameState == gp.creditsState) {
            drawCredits();
        }
    }
}
