package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SlimLance extends OffHand{
    public SlimLance() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Slim_Lance.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Slim Lance";
        description = "Speed +5, Defense -3";
        might = 7;
        randomizeMight();
        hit = 85;
        randomizeHit();
        crit = 0;
        range = 1;
        speed = +5;
        defense = -3;
        resistance = 0;
        vision = 0;
        removable = true;
        effectiveRace = null;
        effectiveType = null;
    }
}
