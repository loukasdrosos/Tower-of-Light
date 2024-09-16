package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class MageRing extends Trinket{
    public MageRing() {
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
        name = "Mage Ring";
        description = "Magic +5. A ring that increases magic";
        strength = 0;
        magic = 5;
        skill = 0;
        speed = 0;
        defense = 0;
        resistance = 0;
        vision = 0;
    }
}
