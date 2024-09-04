package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class MainWeapon extends Item{

    public MainWeapon() {
        name = "MainWeapon";
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Weapons/Lightbringer.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
