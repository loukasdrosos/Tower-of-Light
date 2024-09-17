package Spells;

public class Physic extends HealingSpell{
    public Physic() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Physic";
        heal = 20;
        range = 5;
    }
}
