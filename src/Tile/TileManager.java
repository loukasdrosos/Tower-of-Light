package Tile;

import Entity.ChaosUnit;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TileManager {

    GamePanel gp;
    KeyHandler keyH;

    private final int Max_Col; // Maximum number of columns in the map
    private final int Max_Row; // Maximum number of rows in the map
    public int mapTileNum[][]; // 2D array to store the tile numbers of the map
    public Tile[] tile; // Array to hold different types of tiles
    private static ArrayList<ChaosUnit> selectedEnemies = new ArrayList<>(); // List of selected enemy units
    private boolean aKeyPressed = false; // Flag to track if the A key was pressed in the last frame

    public TileManager(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        Max_Col = gp.getMaxMapCol(); // Get maximum columns from the game panel
        Max_Row = gp.getMaxMapRow(); // Get maximum rows from the game panel
        tile = new Tile[24]; // Initialize the tile array with 14 different types of tiles
        mapTileNum = new int[Max_Col][Max_Row]; // Initialize the map tile number array
        loadImage(); // Load tile images
        loadMap("/Maps/Map_1.txt"); // Load the map from a file
    }

    // Method to load tile images
    public void loadImage() {
        // Placeholders
        setup(0, "Floor", false);
        setup(1, "Floor", false);
        setup(2, "Floor", false);
        setup(3, "Floor", false);
        setup(4, "Floor", false);
        setup(5, "Floor", false);
        setup(6, "Floor", false);
        setup(7, "Floor", false);
        setup(8, "Floor", false);
        setup(9, "Floor", false);

        // Actual Tiles
        setup(10, "Floor", false);
        setup(11, "Floor_Stairs_Down", false);
        setup(12, "Floor_Stairs_Left", false);
        setup(13, "Floor_Stairs_Right", false);
        setup(14, "Floor_Stairs_Up", false);
        setup(15, "Left_Up_Corner", true);
        setup(16, "Pit_Left", true);
        setup(17, "Pit_Right", true);
        setup(18, "Right_Up_Corner", true);
        setup(19, "Stairs_Level_Down", false);
        setup(20, "Stairs_Level_Up", false);
        setup(21, "Wall_Middle", true);
        setup(22, "Wall_Left", true);
        setup(23, "Wall_Right", true);
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/" + imageName +".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.getTileSize() - 1, gp.getTileSize() - 1);
            tile[index].collision = collision;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the map from a text file
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            // Read each line of the file to load the map
            while (col < Max_Col && row < Max_Row) {
                String line = br.readLine();
                while (col < Max_Col) {
                    String numbers[] = line.split(" "); // Split the string at a space
                    int num = Integer.parseInt(numbers[col]); // Convert the string to an integer
                    mapTileNum[col][row] = num; // Store the tile number in the array
                    col++;
                }
                if (col == Max_Col) {
                    col = 0; // Reset column index after reaching the end of a row
                    row++; // Move to the next row
                }
            }
            br.close(); // Close the buffered reader
        }
        catch (Exception e) {  }
    }

    // Method to draw tiles that the player can move to as blue
    public void drawPlayerMovement(Graphics2D g2, int col, int row) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
        g2.setColor(Color.BLUE);
        g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(), gp.getTileSize());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    // Method to load and draw tiles for the player's available movement range
    public void getPlayerMovement(Graphics2D g2) {
        // Draw player movement tiles if a unit is selected
        if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected() && !gp.selectedUnit.getWait() && gp.selectedUnit.getIsMoving()) {
            // Get the movement range from the selected unit
            List<int[]> movementTiles = gp.selectedUnit.calculateValidMovement();
            // Check each possible move and draw the corresponding tile
            for (int[] move : movementTiles) {
                int targetCol = move[0];  // Column position of the valid move
                int targetRow = move[1];  // Row position of the valid move
                drawPlayerMovement(g2, targetCol, targetRow);
            }
        }
    }

    // Method to highlight enemies within player's range
    public void drawEnemiesInRange(Graphics2D g2) {
        if (gp.selectedUnit != null && gp.selectedUnit.getIsSelected() && !gp.selectedUnit.getWait() && gp.selectedUnit.getIsAttacking()) {
            List<int[]> enemiesInRange = new ArrayList<>(); // To store the tiles with enemies
            enemiesInRange = gp.selectedUnit.getEnemiesWithinRange();

            for (int[] move : enemiesInRange) {
                int targetCol = move[0];  // Column position of an enemy in range
                int targetRow = move[1];  // Row position of an enemy in range
                drawAttackingTile(g2, targetCol, targetRow);
            }
        }
    }

    // Method to draw tiles in range of an attack
    public void drawAttackingTile(Graphics2D g2, int col, int row) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
        g2.setColor(Color.RED);  // Set movement color to blue
        g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(), gp.getTileSize());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    // Method to load and draw tiles for the selected enemy units' available movement range
    public void getEnemyAttackRange(Graphics2D g2, ArrayList<ChaosUnit> selectedEnemies) {
        // Get the movement range from the selected unit
        for (ChaosUnit selectedEnemy : selectedEnemies) {
            List<int[]> movementTiles = selectedEnemy.calculateAttackRange();
            // Check each possible move and draw the corresponding tile
            for (int[] move : movementTiles) {
                int targetCol = move[0];  // Column position of the valid move
                int targetRow = move[1];  // Row position of the valid move
                drawAttackingTile(g2, targetCol, targetRow);
            }
        }
    }

    // Method to handle which enemies to highlight their range
    public void EnemySelection() {
       if (gp.TurnM.getPlayerPhase() == true) {
           if (gp.selectedUnit == null && keyH.isAPressed() && !aKeyPressed) {
               aKeyPressed = true;  // Mark the key as pressed

               // Check if the cursor's position matches the position of any enemy unit (ChaosUnit)
               if (gp.selectedUnit == null) {
                   for (ChaosUnit enemy : gp.simChaosUnits) {
                       if (gp.cursor.getCol() == enemy.getCol() && gp.cursor.getRow() == enemy.getRow()) {
                           if (selectedEnemies.contains(enemy)) {
                               gp.playSE(6);
                               selectedEnemies.remove(enemy); // Deselect the enemy if it's already selected
                           } else {
                               gp.playSE(6);
                               selectedEnemies.add(enemy); // Select the enemy if it's not already selected
                           }
                           break; // Exit loop once a match is found
                       }
                   }
               }
           } else if (!keyH.isAPressed()) {
               aKeyPressed = false;  // Reset the flag when the key is released
           }
       }
    }

    // Method to draw the map grid and tiles
    public void drawMap(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        // Draw the map tiles based on the mapTileNum array
        while (col < Max_Col && row < Max_Row) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, null);
            col++;
            x += gp.getTileSize();
            if (col == Max_Col) {
                col = 0;
                x = 0;
                row++;
                y += gp.getTileSize();
            }
        }
    }

    // Main method to handle drawing of the map, and enemy and player movement ranges
    public void draw (Graphics2D g2) {

        drawMap(g2); // Draw the map
        EnemySelection(); // Handle enemy selection logic

        // Draw enemy movement tiles
        if (selectedEnemies != null) {
            getEnemyAttackRange(g2, selectedEnemies);
        }

        // Draw player movement tiles if a unit is selected
        getPlayerMovement(g2);
        // Draw tiles with enemies in range
        drawEnemiesInRange(g2);
    }

}
