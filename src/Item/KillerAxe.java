package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KillerAxe extends MainHand{
    public KillerAxe() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Killer_Axe.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Killer Axe";
        might = 12;
        randomizeMight();
        hit = 70;
        randomizeHit();
        crit = 30;
        range = 1;
        speed = 0;
        defense = 0;
        resistance = 30;
        vision = 0;
        removable = true;
        effectiveRace = null;
        effectiveType = null;
    }
}
