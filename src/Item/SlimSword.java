package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SlimSword extends OffHand{
    public SlimSword() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Slim_Sword.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Slim Sword";
        description = "Speed +5, Defense -3. A light, unusually accurate sword";
        might = 5;
        randomizeMight();
        hit = 100;
        crit = 0;
        range = 1;
        speed = +5;
        defense = -3;
        resistance = 0;
        vision = 0;
        removable = true;
        effectiveRace = null;
        effectiveType = null;
    }
}
