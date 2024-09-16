package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SilverLance extends MainHand{
    public SilverLance() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Silver_Lance.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Silver Lance";
        description = "An exceptional lance of rare metals";
        might = 13;
        randomizeMight();
        hit = 75;
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
