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
        range = 1;
    }
}
