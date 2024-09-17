package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class EdgeOfChaos extends MainHand{
    public EdgeOfChaos() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Edge_of_Chaos.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Edge of Chaos";
        might = 18;
        hit = 90;
        crit = 10;
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
