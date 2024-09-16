package Spells;

public class Miasma extends AttackSpell{
    public Miasma() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Miasma";
        might = 6;
        hit = 85;
        crit = 5;
        range = 3;
        description = "Common dark magic";
    }
}
