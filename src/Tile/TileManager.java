package Tile;

import Entity.ChaosUnit;
import main.GamePanel;
import main.KeyHandler;

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
    private ChaosUnit selectedEnemy = null; // Currently selected enemy unit
    private static ArrayList<ChaosUnit> selectedEnemies = new ArrayList<>(); // List of selected enemy units
    private boolean aKeyPressed = false; // Flag to track if the A key was pressed in the last frame

    public TileManager(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        Max_Col = gp.getMaxMapCol(); // Get maximum columns from the game panel
        Max_Row = gp.getMaxMapRow(); // Get maximum rows from the game panel
        tile = new Tile[14]; // Initialize the tile array with 14 different types of tiles
        mapTileNum = new int[Max_Col][Max_Row]; // Initialize the map tile number array
        loadImage(); // Load tile images
        loadMap("/Maps/Map_1.txt"); // Load the map from a file
    }

    // Method to load tile images
    public void loadImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Floor.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Floor_Stairs_Down.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Floor_Stairs_Left.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Floor_Stairs_Right.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Floor_Stairs_Up.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Left_Up_Corner.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Pit_Left.png"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Pit_Right.png"));
            tile[7].collision = true;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Right_Up_Corner.png"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Stairs_Level_Down.png"));

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Stairs_Level_Up.png"));

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Wall_Middle.png"));
            tile[11].collision = true;

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Wall_Left.png"));
            tile[12].collision = true;

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/MapTiles/Wall_Right.png"));
            tile[13].collision = true;
        }
        catch (IOException e) {
            e.printStackTrace();        }
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

    // Method to draw tiles that the player can move to
    public void drawPlayerMovementTile(Graphics2D g2, int col, int row) {
        int tileNum = mapTileNum[col][row];

        // Only highlight tiles that are passable (no collision)
        if (!tile[tileNum].collision) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
            g2.setColor(Color.BLUE);  // Set movement color to blue

            // Check if an enemy is on this tile and change color to red if true
            for (ChaosUnit enemy : gp.simChaosUnits) {
                if (col == enemy.getCol() && row == enemy.getRow()) {
                    g2.setColor(Color.RED);
                    break;
                }
            }
            // Draw the tile
            g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(),gp.getTileSize());
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));// Reset transparency
        }
    }

    // Method to load and draw tiles for the player's available movement range
    public void drawPlayerMovement(Graphics2D g2, int col, int row) {
        // Get the movement range from the selected unit
        List<int[]> movementRange = gp.selectedUnit.getMovementRange();

        // Check each possible move and draw the corresponding tile
        for (int[] move : movementRange) {
            int targetCol = col + move[0];
            int targetRow = row + move[1];
            drawPlayerMovementTile(g2, targetCol, targetRow);
        }
    }

    // Method to draw tiles that the enemy can move to
    public void drawEnemyMovementTile(Graphics2D g2, int col, int row) {
        int tileNum = mapTileNum[col][row];

        // Only highlight tiles that are passable (no collision)
        if (!tile[tileNum].collision) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
            g2.setColor(Color.RED);  // Set movement color to blue

            // Draw the tile
            g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(),gp.getTileSize());
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));// Reset transparency
        }
    }

    // Method to load and draw tiles for the selected enemy units' available movement range
    public void drawEnemyMovement(Graphics2D g2, ArrayList<ChaosUnit> selectedEnemies) {
        // Get the movement range from the selected unit
        for (ChaosUnit selectedEnemy : selectedEnemies) {
            List<int[]> movementRange = selectedEnemy.getMovementRange();

            // Check each possible move
            for (int[] move : movementRange) {
                int targetCol = selectedEnemy.getPreCol() + move[0];
                int targetRow = selectedEnemy.getPreRow() + move[1];
                drawEnemyMovementTile(g2, targetCol, targetRow);
            }
        }
    }

    // Method to handle enemy selection logic
    public void EnemySelection() {
       if (gp.TurnM.getPlayerPhase() == true) {
           if (gp.selectedUnit == null && keyH.isAPressed() && !aKeyPressed) {
               aKeyPressed = true;  // Mark the key as pressed

               // Check if the cursor's position matches the position of any enemy unit (ChaosUnit)
               if (gp.selectedUnit == null) {
                   for (ChaosUnit enemy : gp.simChaosUnits) {
                       if (gp.cursor.getCol() == enemy.getCol() && gp.cursor.getRow() == enemy.getRow()) {
                           if (selectedEnemies.contains(enemy)) {
                               selectedEnemies.remove(enemy); // Deselect the enemy if it's already selected
                           } else {
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
        int col, row;
        // Draw the map background as black rectangles
        for (row = 0; row < Max_Row; row++) {
            for (col = 0; col < Max_Col; col++) {
                g2.setColor(Color.BLACK);
                g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(), gp.getTileSize());
            }
        }

        col = 0;
        row = 0;
        int x = 0;
        int y = 0;
        // Draw the map tiles based on the mapTileNum array
        while (col < Max_Col && row < Max_Row) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.getTileSize() - 1, gp.getTileSize() - 1, null);
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
        if(selectedEnemies != null) {
            drawEnemyMovement(g2, selectedEnemies);
        }

        // Draw player movement tiles if a unit is selected
        if (gp.selectedUnit != null && !gp.selectedUnit.getWait()) {
            drawPlayerMovement(g2, gp.selectedUnit.getPreCol(), gp.selectedUnit.getPreRow());
        }
    }
}
