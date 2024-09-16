package Item;

import Entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Armorslayer extends OffHand{
    public Armorslayer() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Armorslayer.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Armorslayer";
        description = "Effective: Armored. A sword made to penetrate heavy armor";
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
        effectiveRace = null;
        effectiveType = Entity.UnitType.Armored;
    }
}
