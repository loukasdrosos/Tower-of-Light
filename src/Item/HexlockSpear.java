package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class HexlockSpear extends OffHand{
    public HexlockSpear() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Hexlock_Spear.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Hexlock Spear";
        description = "Resistance +8. A spear destined to pierce a mage's heart";
        might = 3;
        randomizeMight();
        hit = 80;
        randomizeHit();
        crit = 0;
        range = 1;
        speed = 0;
        defense = 0;
        resistance = +8;
        vision = 0;
        removable = true;
        effectiveRace = null;
        effectiveType = null;
    }
}
