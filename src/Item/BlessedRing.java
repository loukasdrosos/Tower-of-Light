package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BlessedRing extends Trinket{
    public BlessedRing() {
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
        name = "Blessed Ring";
        description = "Resistance +3";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = 0;
        defense = 0;
        resistance = 3;
        vision = 0;
    }
}
