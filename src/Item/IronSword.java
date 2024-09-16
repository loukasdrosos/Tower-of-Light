package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class IronSword extends MainHand{
    public IronSword() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Iron_Sword.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Iron Sword";
        description = "A standard sword made of iron";
        might = 5;
        randomizeMight();
        hit = 95;
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
