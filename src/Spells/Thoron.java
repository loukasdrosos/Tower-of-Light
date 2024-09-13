package Spells;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Thoron extends AttackSpell {

    public Thoron() {
        setupStats();
    }

    // Method to set up the weapon's stats
    @Override
    public void setupStats() {
        name = "Thoron";
        might = 10;
        hit = 100;
        crit = 5;
        range = 3;
    }
}
