package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Concoction extends Potion{
    public Concoction() {
        name = "Concoction";
        heal = 20;
        uses = 3;
        description = "Restores 20 HP to the user";
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Potions/Concoction.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
