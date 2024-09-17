package Spells;

public class Expiration extends AttackSpell{
    public Expiration() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Expiration";
        might = 20;
        hit = 85;
        crit = 5;
        range = 3;
    }
}
