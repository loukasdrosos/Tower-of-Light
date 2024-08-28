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
    private final int tileSize = 16;                   // 16x16 tile
    private final int maxScreenCol = 82;
    private final int maxScreenRow = 52;
    private final int screenWidth = tileSize * maxScreenCol;  // 1312 pixels
    private final int screenHeight = tileSize * maxScreenRow; // 832 pixels

    final int FPS = 60; // Game runs in 60 fps

    //UNITS
    public static ArrayList<LightUnit> LightUnits = new ArrayList<>();
    public static ArrayList<ChaosUnit> ChaosUnits = new ArrayList<>();

    public static ArrayList<LightUnit> copyLightUnits = new ArrayList<>();
    public static ArrayList<ChaosUnit> copyChaosUnits = new ArrayList<>();

    public void setUnits() {
        LightUnits.add(new LightUnit(this, keyH, tileSize, tileSize*50));
        LightUnits.add(new LightUnit(this, keyH, tileSize*5, tileSize*50));
        ChaosUnits.add(new ChaosUnit(this, keyH, tileSize*10, tileSize));
        ChaosUnits.add(new ChaosUnit(this, keyH, tileSize*20, tileSize*10));
    }

//    public void copysetUnits (ArrayList<Entity> source, ArrayList<Entity> target) {
//        target.clear();
//        for (int i = 0; i < source.size(); i++) {
//            target.add(source.get(i));
//        }
//    }

    TileManager tileM = new TileManager(this);
    Cursor cursor = new Cursor(this, tileSize, tileSize*50);
    KeyHandler keyH = new KeyHandler(LightUnits, cursor);
    Thread gameThread;
  //  public CollisionChecker cChecker = new CollisionChecker(this);

        // Game screen
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.getScreenWidth(), this.getScreenHeight())); // Set size of GamePanel
        this.setBackground(Color.GRAY);  // Set screen color
        this.setDoubleBuffered(true);   // Draws all components in an off-screen buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);
        cursor.setKeyHandler(keyH);// GamePanel can receive key input

        setUnits();
//        copysetUnits(LightUnits, copyLightUnits);
//        copysetUnits(ChaosUnits, copyChaosUnits);
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
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

        while(gameThread != null) {
        /*
        Checks current time, then at every loop we add the pastime divided by draw interval to delta
        and when delta reaches the drawInterval the screen updates and is repainted, then delta is reset
        */

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
        for (Entity lightunit : LightUnits) {
            lightunit.update();
        }

        for (Entity chaosunit : ChaosUnits) {
            chaosunit.update();
        }

        cursor.update();
    }

    // Draw the screen with the updated information
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (Entity l : LightUnits) {
            l.draw(g2);
        }

        for (Entity c : ChaosUnits) {
            c.draw(g2);
        }

        cursor.draw(g2);

        g2.dispose(); // Dispose this graphics content
    }
}
