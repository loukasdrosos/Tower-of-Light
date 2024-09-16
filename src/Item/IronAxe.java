package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class IronAxe extends MainHand{
    public IronAxe() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Iron_Axe.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Iron Axe";
        description = "A standard axe made of iron";
        might = 7;
        randomizeMight();
        hit = 75;
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
