package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SilverShield extends Trinket{
    public SilverShield() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Shields/Silver_Shield.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Silver Shield";
        description = "Def. +7, Speed -3. An exceptional shield made of rare metals";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = -3;
        defense = 7;
        resistance = 0;
        vision = 0;
    }
}
