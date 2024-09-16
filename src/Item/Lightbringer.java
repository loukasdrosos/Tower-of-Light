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
        description = "A sword that gets stronger with each Beacon of Light used";
        might = 9;
        hit = 85;
        crit = 0;
        range = 1;
        speed = 0;
        defense = 0;
        resistance = 0;
        vision = 1;
        removable = false;
        effectiveRace = null;
        effectiveType = null;
    }

    /*
    @Override
    public void update() {
            might = 9 + 3 * number of Beacon of Lights;

        if (number of beacon of lights > 0 && number of beacon of lights < 3) {
            crit = 5;
        }

        if (number of beacon of lights < 3) {
            range = 1;
        }

        if (number of beacon of lights == 3) {
            range = 3;
            crit = 10;
            hit = 90;
        }
    }

     */

}
