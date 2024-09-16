package Spells;

public class Fire extends AttackSpell{

    public Fire() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Fire";
        might = 3;
        hit = 90;
        crit = 0;
        range = 2;
        description = "Common fire magic";
    }
}
