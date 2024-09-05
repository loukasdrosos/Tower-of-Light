package Item;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Shield extends Item{
    public Shield() {
        name = "Shield";
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/Shields/Dracoshield.png")));
            uTool.scaleImage(image, tileSize, tileSize);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
