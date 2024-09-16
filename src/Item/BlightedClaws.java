package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BlightedClaws extends MainHand{
    public BlightedClaws() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Claws.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Blighted Claws";
        description = "Claws of a Fallen";
        might = 5;
        hit = 80;
        crit = 0;
        range = 1;
        speed = 0;
        defense = 0;
        resistance = 0;
        vision = 0;
        removable = true;
        effectiveRace = null;
        effectiveType = null;
    }
}
