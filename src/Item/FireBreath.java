package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class FireBreath extends MainHand{
    public FireBreath() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Fire_Breath.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Fire Breath";
        might = 12;
        hit = 0;
        crit = 0;
        range = 3;
        speed = 0;
        defense = 0;
        resistance = 0;
        vision = 0;
        removable = true;
        effectiveRace = null;
        effectiveType = null;
    }
}
