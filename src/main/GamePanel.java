package main;

import AI.PathFinder;
import Entity.*;
import Tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    // Screen Settings
    private final int tileSize = 16;    // Size of each tile in pixels (16x16)
    private final int maxMapCol = 52;   // Number of columns in the map
    private final int maxMapRow = 52;   // Number of rows in the map
    private final int maxScreenCol = 84;  // Number of columns displayed on the screen
    private final int maxScreenRow = 52;  // Number of rows displayed on the screen
    private final int screenWidth = tileSize * maxScreenCol;  // Width of the screen in pixels (1344 pixels)
    private final int screenHeight = tileSize * maxScreenRow; // Height of the screen in pixels (832 pixels)
    private final int FPS = 60;  // Game runs at 60 frames per second (FPS)

    // Level selection
    private final int maxMap = 7;
    private int currentMap = 0;

    // Title Screen
    private BufferedImage titleScreenImage, gameOverScreenImage;

    // UNITS
    public static ArrayList<LightUnit> LightUnits = new ArrayList<>(); // List to store active player units (Light Units)
    public LightUnit selectedUnit = null; // Reference to the currently selected unit

    public static ArrayList<ChaosUnit> ChaosUnits = new ArrayList<>();  // List to store active enemy units (Chaos Units)

    // Key handler and managers for the game
    public KeyHandler keyH = new KeyHandler(this);
    public TileManager tileM = new TileManager (this, keyH);
    public Cursor cursor = new Cursor(this, keyH);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public TurnManager TurnM = new TurnManager(this, keyH);
    public UI ui = new UI(this, keyH);
    public AssetSetter aSetter = new AssetSetter(this, keyH);
    public BattleSimulator battleSim = new BattleSimulator(this);
    public PathFinder pFinder = new PathFinder(this);

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int introState = 1;
    public final int introState2 = 2;
    public final int controlsState = 3;
    public final int playState = 4;
    public final int gameOverState = 5;
    public final int creditsState = 6;

    // SOUND
    Sound sound = new Sound();

    // Setup the game panel (initializes the game screen)
    public GamePanel() {
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight)); // Set size of the GamePanel
        this.setBackground(Color.GRAY);  // Set the background color of the screen to gray
        this.setDoubleBuffered(true);   // Enable double buffering for smoother rendering
        this.addKeyListener(keyH);      // Add the key listener to handle keyboard input
        this.setFocusable(true);        // Ensure the panel can receive keyboard input

        // Load titleScreen image
        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage, titleScreen not loading properly");
        }
    }

    // Game Launcher: Starts the game thread
    public void launchGame() {
        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start();                // Start the game thread
    }

    // Method to set up the game
    public void setupGame() {
        aSetter.setLightUnits();
        aSetter.setChaosUnits();
        aSetter.setCursor();
        ui.addLogMessage("Press P to view controls");

        playSE(9);
        gameState = titleState;
    }

    // Reset game if player defeated
    public void resetGame() {
        selectedUnit = null;
        resetCurrentMap();
        tileM.clearSelectedEnemies();
        ui.clearLog();
        TurnM.resetBeaconCooldownTimer();
        TurnM.resetBeaconsofLight();
        TurnM.resetTurnCounter();
        TurnM.resetBoolean();
        LightUnits.clear();
        aSetter.setLightUnits();
        aSetter.setChaosUnits();
        aSetter.setCursor();
        tileM.resetBeaconOfLightTiles();
        tileM.resetItems();
        playMusic(2);
        gameState = playState;
        ui.addLogMessage("Press P to view controls");
    }

    public BufferedImage setup (String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, screenWidth, screenHeight);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void loadImage() {
        titleScreenImage = setup("/TitleScreen/Title_Screen");
        gameOverScreenImage = setup("/GameOverScreen/GameOverScreen");
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

        if (gameState == playState) {

            // Update each Light Unit in the simulation
            for (Entity lightunit : LightUnits) {
                lightunit.update();
            }

            // Update each Chaos Unit in the simulation
            for (Entity chaosunit : ChaosUnits) {
                chaosunit.update();
            }

            TurnM.update();  // Manage turns between players and enemies
            cursor.update(); // Update the cursor
        }
    }

    // Draw the screen with the updated information: called every frame
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        BufferedImage image = null;

        int col, row;
        // Draw the map background as black rectangles
        for (row = 0; row < maxMapRow; row++) {
            for (col = 0; col <maxMapCol; col++) {
                g2.setColor(Color.BLACK);
                g2.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }

        // Title Screen
        if (gameState == titleState) {
            image = titleScreenImage;
            g2.drawImage(image, 0, 0,null);
        }
        else if (gameState == introState) {
            // UI
            ui.draw(g2);
        }
        else if (gameState == introState2) {
            // UI
            ui.draw(g2);
        }
        else if (gameState == controlsState) {
            // UI
            ui.draw(g2);
        }
        else if (gameState == creditsState) {
            // UI
            ui.draw(g2);
        }
        else if (gameState == playState) {

            // Draw the game map tiles
            tileM.draw(g2);

            // Draw each Light Unit in the simulation
            for (Entity lightunit : LightUnits) {
                lightunit.draw(g2);
            }

            // Draw each Chaos Unit in the simulation
            for (Entity chaosunit : ChaosUnits) {
                chaosunit.draw(g2);
            }

            // Draw the cursor
            cursor.draw(g2);

            // Draw battle damage in map
            battleSim.draw(g2);

            // UI
            ui.draw(g2);
        }
        else if (gameState == gameOverState) {
            image = gameOverScreenImage;
            g2.drawImage(image, 0, 0,null);
        }

        g2.dispose(); // Dispose this graphics content
    }

    public void playMusic(int i) {
        sound.setGameMusicFile(i);
        sound.playMusic();
        sound.loopMusic();
    }

    public void stopMusic() {
        sound.stopMusic();
    }

    public void playSE(int i) {
        sound.setSoundEffectFile(i);
        sound.playSE();
    }

    public void stopSE() {
        sound.stopSE();
    }

    //GETTERS

    public int getTileSize() { return tileSize; }

    public int getMaxMapRow() { return maxMapRow; }

    public int getMaxMapCol() { return maxMapCol; }

    public int getMaxMap() { return maxMap;  }

    public int getCurrentMap() {  return currentMap;  }

    public void setNextMap() { currentMap++; }

    public void resetCurrentMap() { currentMap = 0;}
}
