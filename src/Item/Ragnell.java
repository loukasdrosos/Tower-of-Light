package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Ragnell extends MainHand {

    public Ragnell() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Ragnell.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Ragnell";
        description = "Ike only. A legendary sword able to attack at range";
        might = 13;
        hit = 75;
        crit = 0;
        range = 2;
        speed = 0;
        defence = 0;
        resistance = 0;
        vision = 0;
        removable = false;
        effectiveRace = null;
        effectiveType = null;
    }
}
