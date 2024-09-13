package Spells;

import Entity.Entity;

public class AttackSpell {

    protected String name;
    protected int might;
    protected int hit;
    protected int crit;
    protected int range;

    // Getters

    public int getMight() { return might; } // Get the weapon's might

    public int getHit() { return hit; } // Get the weapon's hit rate

    public int getCrit() { return crit; } // Get the weapon's crit chance

    public int getRange() { return range; } // Get the weapon's attack range

    public String getName() { return name; }

    // Placeholder method to set up the main weapon's stats
    public void setupStats() {   }
}
