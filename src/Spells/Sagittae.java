package Spells;

public class Sagittae extends AttackSpell{
    public Sagittae() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Sagittae";
        might = 8;
        hit = 80;
        crit = 10;
        range = 3;
    }
}
