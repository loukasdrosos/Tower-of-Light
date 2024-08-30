package Tile;

import Entity.ChaosUnit;
import main.GamePanel;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    private final int Max_Col;
    private final int Max_Row;
    public int mapTileNum[][];
    public Tile[] tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;
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
    public void drawMovementTile(Graphics2D g2, int col, int row) {
        int tileNum = mapTileNum[col][row];
        if (tile[tileNum].collision == false) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            g2.setColor(Color.BLUE);
            for (ChaosUnit enemy : gp.simChaosUnits) {
                if (col == enemy.getPreCol() && row == enemy.getPreRow()) {
                    g2.setColor(Color.RED);
                }
                g2.fillRect(col * gp.getTileSize(), row * gp.getTileSize(), gp.getTileSize(),gp.getTileSize());
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    // Load tiles for available movement of selected unit
    public void drawPlayerMovement(Graphics2D g2, int col, int row) {
            drawMovementTile(g2, col, row);
            drawMovementTile(g2,col + 1, row);
            drawMovementTile(g2,col - 1, row);
            drawMovementTile(g2, col, row + 1);
            drawMovementTile(g2, col, row - 1);
    }

    public void draw (Graphics2D g2) {

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
        if (gp.selectedUnit != null) {
            drawPlayerMovement(g2, gp.selectedUnit.getPreCol(), gp.selectedUnit.getPreRow());
        }
    }
}
