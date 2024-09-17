package Spells;

public class Goetia extends AttackSpell{
    public Goetia() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Goetia";
        might = 15;
        hit = 80;
        crit = 10;
        range = 3;
    }
}
