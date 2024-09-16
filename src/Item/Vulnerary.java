package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Vulnerary extends Potion{
    public Vulnerary() {
        name = "Vulnerary";
        heal = 10;
        uses = 3;
        description = "Restores 10 HP to the user";
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Potions/Vulnerary.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
