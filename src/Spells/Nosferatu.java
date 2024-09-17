package Spells;

public class Nosferatu extends AttackSpell{
    public Nosferatu() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Nosferatu";
        might = 9;
        hit = 80;
        crit = 5;
        range = 3;
    }
}
