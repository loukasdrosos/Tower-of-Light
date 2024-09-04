package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Potion extends Item{
    public Potion() {
        name = "Potion";
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Potions/Elixir.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
