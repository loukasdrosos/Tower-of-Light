package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Dracoshield extends Trinket{
    public Dracoshield() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Shields/Dracoshield.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Dracoshield";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = -2;
        defense = 2;
        resistance = 2;
        vision = 0;
    }
}
