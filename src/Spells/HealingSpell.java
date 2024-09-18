package Spells;

public class HealingSpell {

    protected String name;
    protected int heal;
    protected int range;
    protected int expEarned;

    // Getters

    public int getHeal() { return heal; } // Get the spell's healing bonus

    public int getRange() { return range; } // Get the spell's healing range

    public int getExpEarned() { return expEarned; } // Get the spell's exp bonus

    public String getName() { return name; } // Get the spell's name

    // Placeholder method to set up the main weapon's stats
    public void setupStats() {   }
}
