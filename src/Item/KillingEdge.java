package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KillingEdge extends MainHand{
    public KillingEdge() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Killing_Edge.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Killing Edge";
        might = 8;
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
