package Spells;

public class Flux extends AttackSpell{
    public Flux() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Flux";
        might = 3;
        hit = 90;
        crit = 0;
        range = 2;
    }
}
