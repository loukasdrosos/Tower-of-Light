package Spells;

public class Fimbulvetr extends AttackSpell{
    public Fimbulvetr() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Fimbulvetr";
        might = 7;
        hit = 85;
        crit = 5;
        range = 3;
    }
}
