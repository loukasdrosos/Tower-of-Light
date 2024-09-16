package Spells;

public class HolyBlessing extends HealingSpell{
    public HolyBlessing() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Holy Blessing";
        heal = 25;
        range = 8;
        description = "Healing magic granted by the God of Light";
    }
}
