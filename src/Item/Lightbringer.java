package Item;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Lightbringer extends MainHand {

    GamePanel gp;

    public Lightbringer(GamePanel gp) {
        this.gp = gp;
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
        description = "Gets stronger with Beacons of Light";
        might = 9;
        hit = 85;
        crit = 0;
        range = 1;
        speed = 0;
        defense = 0;
        resistance = 0;
        vision = 0;
        removable = false;
        effectiveRace = null;
        effectiveType = null;
    }


    @Override
    public void update() {
        might = 9 + 3 * gp.TurnM.getActiveBeacons();

        if (gp.TurnM.getActiveBeacons() == 0) {
            crit = 0;
        }

        if (gp.TurnM.getActiveBeacons() > 0 && gp.TurnM.getActiveBeacons() < 3) {
            crit = 5;
        }

        if (gp.TurnM.getActiveBeacons() < 3) {
            range = 1;
            hit = 85;
        }

        if (gp.TurnM.getActiveBeacons() == 3) {
            range = 3;
            crit = 10;
            hit = 90;
        }
    }

}
