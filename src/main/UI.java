package main;

import Entity.ChaosUnit;
import Entity.Entity;
import Entity.LightUnit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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

    private List<String> logMessages = new ArrayList<>(); // List to hold all log messages
    private int scrollPosition = 0; // Current scroll position
    private static final int maxVisibleMessages = 11; // Max number of visible messages in Game Log
    private static final int messageHeight = 20; // Height of each message line for Game Log

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial_20 = new Font("Arial", Font.PLAIN, 20);
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
                "W: End player unit's turn",
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
            if (gp.selectedUnit.getEffSpeed() >= enemy.getEffSpeed() + 5) {
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
                g2.drawString("Attack: " + enemyAttack, enemyX, textY + 5);
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
            if (player.getUnitType() == Entity.UnitType.Mounted) {
                g2.drawString("Mounted", textX, textY + nextLine * lineHeight);
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

            g2.drawString("Movement: " + player.getMovement(), statsX2, statsY);

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
    }

    public void drawLightUnitCombatStats(LightUnit player) {
        // Checks if player is null because if i unselect player i get error
        if (player != null) {
            // Coordinates for combat stats text
            int lineHeight = 20; // Spacing between each line of text
            int textX = 52 * 16 + 8 * gp.getTileSize() + 10;

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
        }
    }


    // Method to draw the Light Unit's information
    public void drawLightUnitInfo() {
        if (gp.TurnM.getPlayerPhase()) {
            if (gp.selectedUnit == null) {
                LightUnit player = gp.cChecker.getPlayerOnTile(gp.cursor.getCol(), gp.cursor.getRow());
                if (player != null) {
                    drawLightUnitPortrait(player);
                    if (gp.keyH.isQPressed()) {
                        drawLightUnitStats(player);
                        drawLightUnitCombatStats(player);
                    } else {
                        drawLightUnitDetails(player);
                    }
                }
            } else if (gp.selectedUnit != null) {
                drawLightUnitPortrait(gp.selectedUnit);
                if (gp.keyH.isQPressed()) {
                    drawLightUnitStats(gp.selectedUnit);
                    drawLightUnitCombatStats(gp.selectedUnit);
                } else {
                    drawLightUnitDetails(gp.selectedUnit);
                }
                if (gp. selectedUnit != null && gp.selectedUnit.getIsSelected() && !gp.selectedUnit.getIsMoving() && gp.selectedUnit.getIsAttacking()) {
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
        g2.drawString(String.valueOf(enemy.getRace()), textX, textY + nextLine * lineHeight);
        nextLine++;
        if (enemy.getUnitType() == Entity.UnitType.Armored) {
            g2.drawString("Armored", textX, textY + nextLine * lineHeight);
            nextLine++;
        }
        if (enemy.getUnitType() == Entity.UnitType.Mounted) {
            g2.drawString("Mounted", textX, textY + nextLine * lineHeight);
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
            ChaosUnit enemy = gp.cChecker.getEnemyOnTile(gp.cursor.getCol(), gp.cursor.getRow());
            if (enemy != null) {
                drawChaosUnitPortrait(enemy);
                if (gp.keyH.isQPressed()) {
                    drawChaosUnitStats(enemy);
                    drawChaosUnitCombatStats(enemy);
                } else {
                    drawChaosUnitDetails(enemy);
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
