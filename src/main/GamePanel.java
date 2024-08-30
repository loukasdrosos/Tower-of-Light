package main;

import Entity.*;
import Tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    // Screen Settings
    private final int tileSize = 16;    // 16x16 tile
    private final int maxMapCol = 52;
    private final int maxMapRow = 52;
    private final int maxScreenCol = 82;
    private final int maxScreenRow = 52;
    private final int screenWidth = tileSize * maxScreenCol;  // 1312 pixels
    private final int screenHeight = tileSize * maxScreenRow; // 832 pixels
    private final int FPS = 60;  // Game runs in 60 fps

    //UNITS
    public static ArrayList<LightUnit> LightUnits = new ArrayList<>();
    public static ArrayList<LightUnit> simLightUnits = new ArrayList<>();
    public LightUnit selectedUnit = null;

    public static ArrayList<ChaosUnit> ChaosUnits = new ArrayList<>();
    public static ArrayList<ChaosUnit> simChaosUnits = new ArrayList<>();

    public void setUnits() {
        simLightUnits.add(new LightUnit(this, keyH, 2, 48));
        simLightUnits.add(new LightUnit(this, keyH, 5, 50));
        simChaosUnits.add(new ChaosUnit(this, keyH, 10, 1));
        simChaosUnits.add(new ChaosUnit(this, keyH, 20, 10));
    }

//    private void copysetUnits (ArrayList<Entity> source, ArrayList<Entity> target) {
//        target.clear();
//        for (int i = 0; i < source.size(); i++) {
//            target.add(source.get(i));
//        }
//    }

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Cursor cursor = new Cursor(this, keyH);
    Thread gameThread;
    //  public CollisionChecker cChecker = new CollisionChecker(this);

    // Game screen
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight)); // Set size of GamePanel
        this.setBackground(Color.GRAY);  // Set screen color
        this.setDoubleBuffered(true);   // Draws all components in an off-screen buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);

        setUnits();
        int startCursorCol = simLightUnits.get(0).getCol();
        int startCursorRow = simLightUnits.get(0).getRow();
        cursor.setStartingPosition(startCursorCol, startCursorRow);

//        copysetUnits(LightUnits, copyLightUnits);
//        copysetUnits(ChaosUnits, copyChaosUnits);
    }

    // Game Launcher
    public void launchGame() {
        gameThread = new Thread(this); // Thread creation
        gameThread.start();                 // Thread initialization
    }

    @Override
    public void run() {
        // Game Loop using the Delta/Accumulator method
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime(); //Returns in nanoseconds the current value of the running JVM's time source
        long currentTime;

        /* Checks current time, then at every loop we add the pastime divided by draw interval to delta
        and when delta reaches the drawInterval the screen updates and is repainted, then delta is reset */
        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    //Update Game information
    public void update() {
        for (Entity lightunit : simLightUnits) {
            lightunit.update();
        }

        for (Entity chaosunit : simChaosUnits) {
            chaosunit.update();
        }

        cursor.update();
    }

    // Draw the screen with the updated information
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (Entity lightunit : simLightUnits) {
            lightunit.draw(g2);
        }

        for (Entity chaosunit : simChaosUnits) {
            chaosunit.draw(g2);
        }

        cursor.draw(g2);

        if (keyH.isAPressed() == true) {
            // Check if the cursor's position matches the position of any player unit (LightUnit)
            if (selectedUnit == null) {
                for (LightUnit unit : simLightUnits) {
                    if (cursor.getCol() == unit.getCol() && cursor.getRow() == unit.getRow()) {
                        selectedUnit = unit; // Select player unit
                        selectedUnit.setIsSelected(true); //Activate the selected player unit
                        break; // Exit loop once a match is found
                    }
                }
            }
        }
        if (keyH.isZPressed() == true) {
            if (selectedUnit != null) {
                selectedUnit.setIsSelected(false); // Deselect the player
                selectedUnit = null; //Can choose new player
            }
        }

        g2.dispose(); // Dispose this graphics content
    }

    //GETTERS

    // tileSize getter
    public int getTileSize() {
        return tileSize;
    }
    // maxMapRow getter
    public int getMaxMapRow() {
        return maxMapRow;
    }

    // maxMapCol getter
    public int getMaxMapCol() {
        return maxMapCol;
    }
}
