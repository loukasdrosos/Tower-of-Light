package Spells;

public class Goetia extends AttackSpell{
    public Goetia() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Goetia";
        might = 13;
        hit = 80;
        crit = 10;
        range = 3;
        description = "The most powerful of dark magic";
    }
}
