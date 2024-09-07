package main;

import java.awt.*;

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

    // Draw Log Window
    public void drawLogScreen() {
        int x = 52 * 16;
        int y = 37 * 16;
        int width = 32 * 16;
        int height = 15 * 16;
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRoundRect(x, y, width, height, 25, 25);
    }

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
        }
    }
}
