package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SteelAxe extends MainHand{
    public SteelAxe() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Steel_Axe.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Steel Axe";
        might = 11;
        randomizeMight();
        hit = 70;
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
