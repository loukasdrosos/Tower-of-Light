package Tile;

import Entity.*;
import Item.*;
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
    public int mapTileNum[][][]; // 3D array to store the level and tile numbers for each level
    public Tile[] tile; // Array to hold different types of tiles
    private List<Item>[][] tileItems; // 2D array to hold item lists for each tile
    private static ArrayList<ChaosUnit> selectedEnemies = new ArrayList<>(); // List of selected enemy units
    private boolean aKeyPressed = false; // Flag to track if the A key was pressed in the last frame
    private boolean itemWindowOpen = false; // Flag to track if the item window is open
    public boolean[][] visibleTiles;  // 2D array to hold visibility of tiles
    public boolean[][] BeaconOfLightTiles; // 2D array that holds Beacon of Light tiles

    public TileManager(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        Max_Col = gp.getMaxMapCol(); // Get maximum columns from the game panel
        Max_Row = gp.getMaxMapRow(); // Get maximum rows from the game panel
        tile = new Tile[24]; // Initialize the tile array with 14 different types of tiles
        mapTileNum = new int[gp.getMaxMap()][Max_Col][Max_Row]; // Initialize the map tile number array
        tileItems = new ArrayList[Max_Col][Max_Row]; // Initialize the item lists for each tile
        visibleTiles = new boolean[Max_Col][Max_Row];  // Initialize the visibility array
        BeaconOfLightTiles = new boolean[Max_Col][Max_Row];  // Initialize the beacon pf light array

        // Initialize the item lists for each tile
        for (int col = 0; col < Max_Col; col++) {
            for (int row = 0; row < Max_Row; row++) {
                tileItems[col][row] = new ArrayList<>();
            }
        }

        loadImage(); // Load tile images
        loadMap("/Maps/Map_1.txt", 0); // Load the first map
        loadMap("/Maps/Map_2.txt", 1); // Load the second map
        loadMap("/Maps/Map_3.txt", 2); // Load the third map
        loadMap("/Maps/Map_4.txt", 3); // Load the fourth map
        loadMap("/Maps/Map_5.txt", 4); // Load the fifth map
        loadMap("/Maps/Map_6.txt", 5); // Load the sixth map
        loadMap("/Maps/Map_7.txt", 6); // Load the seventh map
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
    public void loadMap(String filePath, int map) {
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
                    mapTileNum[map][col][row] = num; // Store the tile number in the array
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

    public void loadStairs() {
        mapTileNum[gp.getCurrentMap()][50][1] = 19;
        gp.ui.addLogMessage("Alm: The stairs to the next floor appeared");
    }

    public void resetItems() {
        for (int col= 0; col < Max_Col; col++) {
            for (int row = 0; row < Max_Row; row++) {
                tileItems[col][row].clear();
            }
        }
    }

    // Method to add visible tiles from a unit, marking them as visible in the array
    public void addVisibleTilesFromLightunit(LightUnit lightUnit) {
        List<int[]> lightunitVisibleTiles = lightUnit.getVisibleTiles();

        // Mark each tile as visible
        for (int[] tile : lightunitVisibleTiles) {
            int col = tile[0];
            int row = tile[1];

            // Ensure the tile is within map boundaries
            if (gp.cChecker.isWithinMap(col, row) && !visibleTiles[col][row]) {
                visibleTiles[col][row] = true;  // Mark the tile as visible
            }
        }
    }

    public void addBeaconOfLightTile(LightUnit lightUnit) {
        int col = lightUnit.getCol();
        int row = lightUnit.getRow();
        BeaconOfLightTiles[col][row] = true;
    }

    public void addVisibleTilesFromBOL() {
        for (int col = 0; col < Max_Col; col++) {
            for (int row = 0; row < Max_Row; row++) {
                if (BeaconOfLightTiles[col][row]) {
                    visibleTiles[col][row] = true;

                    // Add all tiles with 1 Euclidean distance from Beacon of Light Tile
                    int[][] neighborTiles = {
                            {col - 1, row},     // Left
                            {col + 1, row},     // Right
                            {col, row - 1},     // Up
                            {col, row + 1},     // Down
                            {col - 1, row - 1}, // Upper-left diagonal
                            {col + 1, row - 1}, // Upper-right diagonal
                            {col - 1, row + 1}, // Lower-left diagonal
                            {col + 1, row + 1}  // Lower-right diagonal
                    };

                    // Add valid neighboring tiles
                    for (int[] neighbor : neighborTiles) {
                        int neighborCol = neighbor[0];
                        int neighborRow = neighbor[1];

                        if (gp.cChecker.isWithinMap(neighborCol, neighborRow) && !visibleTiles[neighborCol][neighborRow]) {
                            visibleTiles[neighborCol][neighborRow] = true; // Mark the tile as visible
                        }
                    }
                }
            }
        }
    }

    // Method to reset Beacon Of Light of all tiles
    public void resetBeaconOfLightTiles() {
        for (int col= 0; col < Max_Col; col++) {
            for (int row = 0; row < Max_Row; row++) {
                BeaconOfLightTiles[col][row] = false;
            }
        }
    }

    public void findAllVisibleTiles() {
        resetAllVisibleTiles();

        addVisibleTilesFromBOL();

        for (LightUnit lightUnit : gp.LightUnits) {
            addVisibleTilesFromLightunit(lightUnit);
        }
    }

    // Method to reset visibility of all tiles
    public void resetAllVisibleTiles() {
        for (int col= 0; col < Max_Col; col++) {
            for (int row = 0; row < Max_Row; row++) {
                visibleTiles[col][row] = false;
            }
        }
    }

    // Method to draw all visible tiles
    public void drawBeaconOfLightTiles(Graphics2D g2) {
        for (int col= 0; col < Max_Col; col++) {
            for (int row = 0; row < Max_Row; row++) {
                if (BeaconOfLightTiles[col][row]) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
                    g2.setColor(Color.YELLOW);
                    g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(), gp.getTileSize());
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
            }
        }
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
        if (gp.selectedUnit != null && !gp.selectedUnit.getWait() && gp.selectedUnit.getIsMoving()) {
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

    // Method to highlight ally players within selected player's healing range
    public void drawPlayersInRange(Graphics2D g2) {
        if (gp.selectedUnit != null && !gp.selectedUnit.getWait() && gp.selectedUnit.getIsHealing()) {
            List<int[]> playersInRange = new ArrayList<>(); // To store the tiles with enemies
            playersInRange = gp.selectedUnit.getTilesWithPlayersInRange();

            for (int[] move : playersInRange) {
                int targetCol = move[0];  // Column position of an enemy in range
                int targetRow = move[1];  // Row position of an enemy in range
                drawHealingTile(g2, targetCol, targetRow);
            }
        }
    }

    // Method to draw tiles in range of an attack
    public void drawHealingTile(Graphics2D g2, int col, int row) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
        g2.setColor(Color.GREEN);  // Set movement color to blue
        g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(), gp.getTileSize());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    // Method to highlight enemies within player's range
    public void drawEnemiesInRange(Graphics2D g2) {
        if (gp.selectedUnit != null && !gp.selectedUnit.getWait() && gp.selectedUnit.getIsAttacking()) {
            List<int[]> enemiesInRange = new ArrayList<>(); // To store the tiles with enemies
            enemiesInRange = gp.selectedUnit.getTilesWithEnemiesInRange();

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
        // Remove any enemies that are no longer in gp.ChaosUnits from selectedEnemies
        selectedEnemies.removeIf(enemy -> !gp.ChaosUnits.contains(enemy));

       if (gp.TurnM.getPlayerPhase() == true) {
           if (!itemWindowOpen) {
               if (gp.selectedUnit == null && keyH.isAPressed() && !aKeyPressed) {
                   aKeyPressed = true;  // Mark the key as pressed

                   // Check if the cursor's position matches the position of any enemy unit (ChaosUnit)
                   if (gp.selectedUnit == null) {

                       for (ChaosUnit enemy : gp.ChaosUnits) {
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
    }

    public void clearSelectedEnemies() {
        selectedEnemies.clear();
    }

    // Method to add an item to a specific tile at (col, row), max inventory size per tile is 20
    public void addItems(Item item, int col, int row) {
        if (tileItems[col][row].size() < 20) {
            tileItems[col][row].add(item);
            gp.ui.addLogMessage(item.getName() + " was dropped");
        }
    }

    public void removeItem(int itemIndex, int col, int row) {
        tileItems[col][row].remove(itemIndex);
        if (tileItems[col][row].isEmpty()) {
            itemWindowOpen = false;
        }
    }

    public void clearItems() {
        for (int col = 0; col < Max_Col; col++) {
            for (int row = 0; row < Max_Row; row++) {
                tileItems[col][row].clear();
            }
        }
    }

    public void switchItem(int itemIndex, Item item, int col, int row) {
        tileItems[col][row].set(itemIndex, item);
    }

    // Method to get the first item on a specific tile at (col, row)
    public Item getFirstItemOnTile(int col, int row) {
        if (!tileItems[col][row].isEmpty()) {
            return tileItems[col][row].getFirst(); // Return the first item
        }
        return null;
    }

    // Method to check if a tile at (col, row) has any items
    public boolean hasItemsOnTile(int col, int row) {
        if (tileItems[col][row].isEmpty()) {
            return false;
        }
        return true;
    }

    public List<Item> getTileItems(int col, int row) {
        return tileItems[col][row];  // Return the list of items at the tile
    }

    // Method to draw the map grid and tiles
    public void drawMap(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        // Draw the map background as black rectangles
        for (row = 0; row < gp.getMaxMapRow(); row++) {
            for (col = 0; col < gp.getMaxMapCol(); col++) {
                g2.setColor(Color.BLACK);
                g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(), gp.getTileSize());
            }
        }

        col = 0;
        row = 0;

        // Draw the map tiles based on the mapTileNum array
        while (col < Max_Col && row < Max_Row) {
            int tileNum = mapTileNum[gp.getCurrentMap()][col][row];
            Tile currentTile = tile[tileNum];
            g2.drawImage(currentTile.image, x, y, null); // Draw the tile

            // Check if the tile at this position has items, if so, draw the first item
            if (hasItemsOnTile(col, row)) {
                Item firstItem = getFirstItemOnTile(col, row);
                if (firstItem != null) {
                    g2.drawImage(firstItem.getImage(), x, y, gp.getTileSize() - 1, gp.getTileSize() - 1, null);
                }
            }

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
        drawBeaconOfLightTiles(g2);
        EnemySelection(); // Handle enemy selection logic

        // Draw enemy movement tiles
        if (selectedEnemies != null) {
            getEnemyAttackRange(g2, selectedEnemies);
        }

        // Draw player movement tiles if a unit is selected
        getPlayerMovement(g2);
        // Draw tiles with enemies in range
        drawEnemiesInRange(g2);
        // Draw tiles with ally players in range
        drawPlayersInRange(g2);
    }

    public boolean isItemWindowOpen() {
        return itemWindowOpen;
    }

    public void setItemWindowOpen(boolean x) {
        itemWindowOpen = x;
    }
}
