package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class HexlockShield extends Trinket{
    public HexlockShield() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Shields/Hexlock_Shield.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Hexlock Shield";
        description = "Resistance +7, Speed -4";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = -4;
        defense = 0;
        resistance = 7;
        vision = 0;
    }
}
