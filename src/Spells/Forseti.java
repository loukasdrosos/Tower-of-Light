package Spells;

public class Forseti extends AttackSpell{
    public Forseti() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Forseti";
        might = 12;
        hit = 85;
        crit = 15;
        range = 3;
        description = "The most powerful of wind magic";
    }
}
