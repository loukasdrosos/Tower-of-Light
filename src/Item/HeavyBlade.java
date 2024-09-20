package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class HeavyBlade extends OffHand{
    public HeavyBlade() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Heavy_Blade.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Heavy Blade";
        description = "Defense +4, Speed -3";
        might = 10;
        randomizeMight();
        hit = 75;
        randomizeHit();
        crit = 0;
        range = 1;
        speed = -3;
        defense = +3;
        resistance = 0;
        vision = 0;
        removable = true;
        effectiveRace = null;
        effectiveType = null;
    }
}
