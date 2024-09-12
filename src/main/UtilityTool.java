package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        // Creates a Graphics2D which can be used to draw into this BufferedImage
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    // Helper method to check if a list contains a specific tile
    public boolean containsTile(List<int[]> list, int col, int row) {
        for (int[] tile : list) {
            if (tile[0] == col && tile[1] == row) {
                return true;
            }
        }
        return false;
    }
}
