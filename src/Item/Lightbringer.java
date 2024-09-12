package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Lightbringer extends MainHand {

    public Lightbringer() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Lightbringer.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Lightbringer";
        might = 10;
        hit = 100;
        crit = 5;
        range = 3;
        speed = 0;
        defence = 0;
        resistance = 0;
        vision = 1;
        removable = false;
        effectiveRace = null;
        effectiveType = null;
    }

    /*
    @Override
    public void update() {
            might = 10 + 2 * number of Beacon of Lights;

        if (number of beacon of lights < 3) {
            range = 1;
        }

        if (number of beacon of lights == 3) {
            range = 3;
        }
    }

     */

}
