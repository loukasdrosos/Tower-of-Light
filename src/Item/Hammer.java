package Item;

import Entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Hammer extends MainHand{
    public Hammer() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Hammer.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Hammer";
        description = "Effective: Armored. A war hammer made to break heavy armor";
        might = 10;
        randomizeMight();
        hit = 60;
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
