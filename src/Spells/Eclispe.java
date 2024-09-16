package Spells;

public class Eclispe extends AttackSpell{
    public Eclispe() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Eclipse";
        might = 10;
        hit = 80;
        crit = 5;
        range = 4;
        description = "Dark magic used by Chaos Dragons";
    }
}
