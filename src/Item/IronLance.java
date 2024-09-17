package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class IronLance extends MainHand{
    public IronLance() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Iron_Lance.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Iron Lance";
        might = 6;
        randomizeMight();
        hit = 85;
        randomizeHit();
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
