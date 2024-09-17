package Spells;

public class Aureola extends AttackSpell{
    public Aureola() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Aureola";
        might = 11;
        hit = 90;
        crit = 5;
        range = 3;
    }

}
