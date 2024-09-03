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
    private final int tileSize = 16;    // Size of each tile in pixels (16x16)
    private final int maxMapCol = 52;   // Number of columns in the map
    private final int maxMapRow = 52;   // Number of rows in the map
    private final int maxScreenCol = 82;  // Number of columns displayed on the screen
    private final int maxScreenRow = 52;  // Number of rows displayed on the screen
    private final int screenWidth = tileSize * maxScreenCol;  // Width of the screen in pixels (1312 pixels)
    private final int screenHeight = tileSize * maxScreenRow; // Height of the screen in pixels (832 pixels)
    private final int FPS = 60;  // Game runs at 60 frames per second (FPS)

    //UNITS
    public static ArrayList<LightUnit> LightUnits = new ArrayList<>();  // List to store player units (Light Units) (permanent)
    public static ArrayList<LightUnit> simLightUnits = new ArrayList<>(); // List to store active player units (Light Units) (simulation)
    public LightUnit selectedUnit = null; // Reference to the currently selected unit

    public static ArrayList<ChaosUnit> ChaosUnits = new ArrayList<>(); // List to store enemy units (Chaos Units) (permanent)
    public static ArrayList<ChaosUnit> simChaosUnits = new ArrayList<>();  // List to store active enemy units (Chaos Units) (simulation)

    // Initialize units and set their starting positions
    public void setUnits() {
        // Add player units to the simulation list
        simLightUnits.add(new LightUnit(this, keyH, 30, 14));
        simLightUnits.add(new LightUnit(this, keyH, 30, 15));
        simLightUnits.add(new LightUnit(this, keyH, 30, 16));

        // Add enemy units to the simulation list
        simChaosUnits.add(new ChaosUnit(this, keyH, 30, 13));
        simChaosUnits.add(new ChaosUnit(this, keyH, 30, 17));
        simChaosUnits.add(new ChaosUnit(this, keyH, 30, 18));
    }

    // Copy units from one list to another
    public <T extends Entity> void copysetUnits(ArrayList<T> source, ArrayList<T> target) {
        target.clear();
        target.addAll(source);
    }

    // Key handler and managers for the game
    KeyHandler keyH = new KeyHandler(this);
    TileManager tileM = new TileManager (this, keyH);
    public Cursor cursor = new Cursor(this, keyH);
    UnitSelector UnitSel =  new UnitSelector(this, keyH);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public TurnManager TurnM = new TurnManager(this);

    // Setup the game panel (initializes the game screen)
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight)); // Set size of the GamePanel
        this.setBackground(Color.GRAY);  // Set the background color of the screen to gray
        this.setDoubleBuffered(true);   // Enable double buffering for smoother rendering
        this.addKeyListener(keyH);      // Add the key listener to handle keyboard input
        this.setFocusable(true);        // Ensure the panel can receive keyboard input

        setUnits();   // Initialize units and set their starting positions
        int startCursorCol = simLightUnits.get(0).getCol();
        int startCursorRow = simLightUnits.get(0).getRow();
        cursor.setStartingPosition(startCursorCol, startCursorRow);


        copysetUnits(simLightUnits, LightUnits);
        copysetUnits(simChaosUnits, ChaosUnits);
    }

    // Game Launcher: Starts the game thread
    public void launchGame() {
        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start();                // Start the game thread
    }

    @Override
    public void run() {
        // Game Loop using the Delta/Accumulator method
        double drawInterval = 1000000000/FPS; // Interval between frames in nanoseconds
        double delta = 0;  // Accumulator for timing
        long lastTime = System.nanoTime(); // Get the current time in nanoseconds
        long currentTime;

        /* The game loop continues running as long as the gameThread is not null.
        The loop checks the current time and accumulates the time passed since the last frame.
        When enough time has passed to match the draw interval (i.e., 1/60th of a second), the game updates and repaints the screen. */

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update(); // Update the game state
                repaint(); // Repaint the screen with the updated state
                delta--; // Decrease delta by 1 to prepare for the next frame
            }
        }
    }

    // Update game information: called every frame
    public void update() {
        // Update each Light Unit in the simulation
        for (Entity lightunit : simLightUnits) {
            lightunit.update();
        }

        // Update each Chaos Unit in the simulation
        for (Entity chaosunit : simChaosUnits) {
            chaosunit.update();
        }

        TurnM.manageTurns();  // Manage turns between players and enemies
        cursor.update(); // Update the cursor
        UnitSel.update(); // Update unit selector
    }

    // Draw the screen with the updated information: called every frame
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); // Draw the game map tiles

        // Draw each Light Unit in the simulation
        for (Entity lightunit : simLightUnits) {
            lightunit.draw(g2);
        }

        // Draw each Chaos Unit in the simulation
        for (Entity chaosunit : simChaosUnits) {
            chaosunit.draw(g2);
        }

        cursor.draw(g2);  // Draw the cursor

        g2.dispose(); // Dispose this graphics content
    }

    //GETTERS

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxMapRow() {
        return maxMapRow;
    }

    public int getMaxMapCol() {
        return maxMapCol;
    }
}
