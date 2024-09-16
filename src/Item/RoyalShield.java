package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class RoyalShield extends Trinket{
    public RoyalShield() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Shields/Royal_Shield.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Royal Shield";
        description = "Def. +3, Res. +3, Speed -3. A shield made for the royal lineage";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = -3;
        defense = 3;
        resistance = 3;
        vision = 0;
    }
}
