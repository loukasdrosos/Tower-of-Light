package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class VeninClaws extends MainHand{
    public VeninClaws() {
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
        name = "Venin Claws";
        description = "Powerful claws of a Fallen";
        might = 9;
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
