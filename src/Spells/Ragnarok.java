package Spells;

public class Ragnarok extends AttackSpell{
    public Ragnarok() {
        setupStats();
    }

    // Method to set up the spell's stats
    @Override
    public void setupStats() {
        name = "Ragnarok";
        might = 16;
        hit = 80;
        crit = 10;
        range = 3;
        description = "The most powerful of fire magic";
    }
}
