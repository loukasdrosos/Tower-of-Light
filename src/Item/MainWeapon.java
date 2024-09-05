package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class MainWeapon extends Item{

    public MainWeapon() {
        name = "MainWeapon";
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Lightbringer.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
