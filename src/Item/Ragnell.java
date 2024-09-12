package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Ragnell extends MainHand {

    public Ragnell() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Lightbringer.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Ragnell";
        might = 10;
        hit = 100;
        crit = 5;
        range = 2;
        speed = 0;
        defence = 0;
        resistance = 0;
        vision = 1;
        removable = false;
        effectiveRace = null;
        effectiveType = null;
    }
}
