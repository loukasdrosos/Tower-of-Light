package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Mercurius extends MainHand{
    public Mercurius() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Mercurius.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Mercurius";
        might = 15;
        randomizeMight();
        hit = 80;
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
