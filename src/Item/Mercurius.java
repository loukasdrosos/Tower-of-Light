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
        description = "A sword made to inflict deadly wounds";
        might = 7;
        randomizeMight();
        hit = 90;
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
