package Spells;

public class Aureola extends AttackSpell{
    public Aureola() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Aureola";
        might = 10;
        hit = 80;
        crit = 5;
        range = 3;
        description = "The most powerful of light magic";
    }

}
