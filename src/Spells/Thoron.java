package Spells;

public class Thoron extends AttackSpell {

    public Thoron() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Thoron";
        might = 14;
        hit = 80;
        crit = 10;
        range = 4;
        description = "The most powerful of lightning magic";
    }
}
