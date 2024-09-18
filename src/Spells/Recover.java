package Spells;

public class Recover extends HealingSpell{
    public Recover() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Recover";
        heal = 15;
        range = 2;
        expEarned = 10;
    }
}
