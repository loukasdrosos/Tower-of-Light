package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Elixir extends Potion{
    public Elixir() {
        name = "Elixir";
        heal = 30;
        uses = 3;
        description = "Restores 30 HP to the user";
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Potions/Elixir.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
