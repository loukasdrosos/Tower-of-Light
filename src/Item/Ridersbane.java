package Item;

import Entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Ridersbane extends OffHand{
    public Ridersbane() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Ridersbane.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Ridersbane";
        description = "Effective: Mounted. A lance designed for foes on horseback";
        might = 9;
        randomizeMight();
        hit = 70;
        randomizeHit();
        crit = 0;
        range = 1;
        speed = 0;
        defense = 0;
        resistance = 0;
        vision = 0;
        removable = true;
        effectiveRace = null;
        effectiveType = Entity.UnitType.Mounted;
    }
}
