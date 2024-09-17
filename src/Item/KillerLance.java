package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KillerLance extends MainHand{
    public KillerLance() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Killer_Lance.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Killer Lance";
        might = 10;
        randomizeMight();
        hit = 80;
        randomizeHit();
        crit = 30;
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
