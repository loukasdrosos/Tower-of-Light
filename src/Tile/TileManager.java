package Tile;

import Entity.ChaosUnit;
import Entity.LightUnit;
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

    private final int Max_Col;
    private final int Max_Row;
    public int mapTileNum[][];
    public Tile[] tile;
    private ChaosUnit selectedEnemy = null;
    private static ArrayList<ChaosUnit> selectedEnemies = new ArrayList<>();
    private boolean aKeyPressed = false;

    public TileManager(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        Max_Col = gp.getMaxMapCol();
        Max_Row = gp.getMaxMapRow();
        tile = new Tile[14]; // Number of different tiles we use
        mapTileNum = new int[Max_Col][Max_Row];
        loadImage();
        loadMap("/Maps/Map_1.txt");
    }

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

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < Max_Col && row < Max_Row) {
                String line = br.readLine();
                while (col < Max_Col) {
                    String numbers[] = line.split(" "); // Split the string at a space
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == Max_Col) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch (Exception e) {  }
    }

    // Draw tiles that the player can move
    public void drawPlayerMovementTile(Graphics2D g2, int col, int row) {
        int tileNum = mapTileNum[col][row];

        // Only highlight tiles that are passable (no collision)
        if (tile[tileNum].collision == false) {
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

    // Load tiles for available movement of selected player unit
    public void drawPlayerMovement(Graphics2D g2, int col, int row) {
        // Get the movement range from the selected unit
        List<int[]> movementRange = gp.selectedUnit.getMovementRange();

        // Check each possible move
        for (int[] move : movementRange) {
            int targetCol = col + move[0];
            int targetRow = row + move[1];
            drawPlayerMovementTile(g2, targetCol, targetRow);
        }
    }

    // Draw tiles that the enemy can move
    public void drawEnemyMovementTile(Graphics2D g2, int col, int row) {
        int tileNum = mapTileNum[col][row];

        // Only highlight tiles that are passable (no collision)
        if (tile[tileNum].collision == false) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
            g2.setColor(Color.RED);  // Set movement color to blue

            // Draw the tile
            g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(),gp.getTileSize());

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));// Reset transparency
        }
    }

    // Load tiles for available movement of selected enemy unit
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

    public void EnemySelection() {
        if (keyH.isAPressed() == true && !aKeyPressed) {
            aKeyPressed = true;  // Mark the key as pressed

            // Check if the cursor's position matches the position of any enemy unit (ChaosUnit)
            if (gp.selectedUnit == null) {
                for (ChaosUnit enemy : gp.simChaosUnits) {
                    if (gp.cursor.getCol() == enemy.getCol() && gp.cursor.getRow() == enemy.getRow()) {
                        if (selectedEnemies.contains(enemy)) {
                            selectedEnemies.remove(enemy);
                        } else {
                            selectedEnemies.add(enemy);
                        }
                        break; // Exit loop once a match is found
                    }
                }
            }
        }
        else if (!keyH.isAPressed()) {
            aKeyPressed = false;  // Reset the flag when the key is released
        }
    }

    public void drawMap(Graphics2D g2) {
        int col, row;
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

    public void draw (Graphics2D g2) {

        drawMap(g2);

        EnemySelection();
        if(selectedEnemies != null) {
            drawEnemyMovement(g2, selectedEnemies);
        }

        if (gp.selectedUnit != null && gp.selectedUnit.getWait() == false && gp.selectedUnit.getIsMoving() == true) {
            drawPlayerMovement(g2, gp.selectedUnit.getPreCol(), gp.selectedUnit.getPreRow());
        }
    }
}
