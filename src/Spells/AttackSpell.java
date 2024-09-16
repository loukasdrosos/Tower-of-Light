package Spells;

public class AttackSpell {

    protected String name;
    protected int might;
    protected int hit;
    protected int crit;
    protected int range;
    protected String description;

    // Getters

    public int getMight() { return might; } // Get the spell's might

    public int getHit() { return hit; } // Get the spell's hit rate

    public int getCrit() { return crit; } // Get the spell's crit chance

    public int getRange() { return range; } // Get the spell's attack range

    public String getName() { return name; } // Get the weapon's name

    public String getDescription() { return description; } // Get the weapon's description

    // Placeholder method to set up the main weapon's stats
    public void setupStats() {   }
}
