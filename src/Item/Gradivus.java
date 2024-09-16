package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Gradivus extends MainHand{
    public Gradivus() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Gradivus.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Gradivus";
        description = "A lance only for the best warriors";
        might = 17;
        randomizeMight();
        hit = 75;
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
