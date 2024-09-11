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
        strength = 10;
        magic = 0;
        skill = 0;
        speed = -20;
        defense = 2;
        resistance = 20;
        vision = 0;
    }
}
