package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BlessedSword extends OffHand{
    public BlessedSword() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Blessed_Sword.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Blessed Sword";
        description = "Resistance +4. A sword blessed by the God of Light";
        might = 7;
        randomizeMight();
        hit = 85;
        randomizeHit();
        crit = 10;
        range = 1;
        speed = 0;
        defense = 0;
        resistance = +4;
        vision = 0;
        removable = true;
        effectiveRace = null;
        effectiveType = null;
    }
}
