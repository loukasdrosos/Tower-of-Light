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
        description = "Def. +10, Res. +10, Speed -10";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = -10;
        defense = 10;
        resistance = 10;
        vision = 0;
    }
}
