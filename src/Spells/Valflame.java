package Spells;

public class Valflame extends AttackSpell{
    public Valflame() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Valflame";
        might = 10;
        hit = 80;
        crit = 5;
        range = 3;
    }
}
