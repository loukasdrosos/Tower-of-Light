package Spells;

public class Heal extends HealingSpell{
    public Heal() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Heal";
        heal = 10;
        range = 1;
        expEarned = 10;
    }
}
