package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SilverSword extends MainHand{
    public SilverSword() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Silver_Sword.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Silver Sword";
        description = "An exceptional sword of rare metals";
        might = 11;
        randomizeMight();
        hit = 85;
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
