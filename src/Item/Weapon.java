package Item;

import Entity.Entity;

public class Weapon extends Item{
    protected int might;
    protected int hit;
    protected int crit;
    protected int range;
    protected int speed;
    protected int defence;
    protected int resistance;
    protected int vision;
    protected boolean removable;
    protected Entity.UnitRace effectiveRace;
    protected Entity.UnitType effectiveType;

    // Placeholder method for Lightbringer
    public void update() {}

    // Getters

    public int getMight() { return might; } // Get the weapon's might

    public int getHit() { return hit; } // Get the weapon's hit rate

    public int getCrit() { return crit; } // Get the weapon's crit chance

    public int getRange() { return range; } // Get the weapon's attack range

    public int getSpeed() { return speed; } // Get the weapon's speed bonus

    public int getDefense() { return defence; } // Get the weapon's defense bonus

    public int getResistance() { return resistance; } // Get the weapon's resistance bonus

    public int getVision() { return vision; } // Get the weapon's vision bonus

    protected boolean isRemovable() {return removable;} // Return if the weapon can be dropped or not

    public Entity.UnitRace getEffectiveRace() {return effectiveRace;} // Return the weapon's effectiveness against some unit race

    public Entity.UnitType getEffectiveType() {return effectiveType;} // Return the weapon's effectiveness against some unit type

}
