package Spells;

public class Seraphim extends AttackSpell{
    public Seraphim() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Seraphim";
        might = 6;
        hit = 80;
        crit = 0;
        range = 2;
        description = "Strong light magic";
    }
}
