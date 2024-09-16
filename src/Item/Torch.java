package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Torch extends Trinket{
    public Torch() {
        setupStats();
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Torch/Torch.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Torch";
        description = "Visibility + 1";
        strength = 0;
        magic = 0;
        skill = 0;
        speed = 0;
        defense = 0;
        resistance = 0;
        vision = 1;
    }
}
