package Item;

import Entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Wyrmslayer extends OffHand{
    public Wyrmslayer() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Wyrmslayer.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Wyrmslayer";
        description = "Effective: Dragons. A sword made to penetrate dragon skin";
        might = 8;
        randomizeMight();
        hit = 80;
        randomizeHit();
        crit = 0;
        range = 1;
        speed = 0;
        defense = 0;
        resistance = 0;
        vision = 0;
        removable = true;
        effectiveRace = Entity.UnitRace.Dragon;
        effectiveType = null;
    }

}
