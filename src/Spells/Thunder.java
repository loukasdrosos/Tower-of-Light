package Spells;

public class Thunder extends AttackSpell{
    public Thunder() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Thunder";
        might = 5;
        hit = 85;
        crit = 5;
        range = 3;
        description = "Magic used to attack with lightning";
    }
}
