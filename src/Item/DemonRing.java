package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DemonRing extends Trinket{
    public DemonRing() {
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
        name = "Demon Ring";
        description = "Str. +5, Skill +5, Def. -5, Res. -5. A ring with demon powers";
        strength = 5;
        magic = 0;
        skill = 5;
        speed = 0;
        defense = -5;
        resistance = -5;
        vision = 0;
    }
}
