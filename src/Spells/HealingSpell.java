package Spells;

public class HealingSpell {

    protected String name;
    protected int heal;
    protected int range;
    protected String description;

    // Getters

    public int getHeal() { return heal; } // Get the spell's healing bonus

    public int getRange() { return range; } // Get the spell's healing range

    public String getName() { return name; } // Get the spell's name

    public String getDescription() { return description; } // Get the spell's description

    // Placeholder method to set up the main weapon's stats
    public void setupStats() {   }
}
