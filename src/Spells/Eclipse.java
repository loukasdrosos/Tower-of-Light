package Spells;

public class Eclipse extends AttackSpell{
    public Eclipse() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Eclipse";
        might = 10;
        hit = 80;
        crit = 5;
        range = 3;
    }
}
