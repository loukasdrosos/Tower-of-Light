package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SteelShield extends Trinket{
    public SteelShield() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Shields/Steel_Shield.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Steel Shield";
        description = "Def. +4, Speed -2. A shield made of steel";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = -2;
        defense = 4;
        resistance = 0;
        vision = 0;
    }
}
