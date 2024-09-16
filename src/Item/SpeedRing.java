package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SpeedRing extends Trinket{
    public SpeedRing() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Ring/Ring.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Speed Ring";
        description = "Speed +5. A ring that increases speed";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = +5;
        defense = 0;
        resistance = 0;
        vision = 0;
    }
}
