package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class IronShield extends Trinket{
    public IronShield() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Shields/Iron_Shield.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Iron Shield";
        description = "Def. +2, Speed -1. A common shield made of iron";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = -1;
        defense = 2;
        resistance = 0;
        vision = 0;
    }
}
