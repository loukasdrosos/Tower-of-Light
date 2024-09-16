package Spells;

public class Fenrir extends AttackSpell{
    public Fenrir() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Fenrir";
        might = 8;
        hit = 70;
        crit = 0;
        range = 6;
        description = "Dark magic with wide range";
    }
}
