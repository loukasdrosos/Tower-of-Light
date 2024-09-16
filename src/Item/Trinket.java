package Item;

public class Trinket extends Item{

    protected int strength; // Bonus strength the item offers
    protected int magic; // Bonus magic the item offers
    protected int skill; // Bonus skill the item offers
    protected int speed; // Bonus speed the item offers
    protected int defense; // Bonus defense the item offers
    protected int resistance; // Bonus resistance the item offers
    protected int vision; // Bonus vision the item offers

    public int getStrength() { return strength; } // Get trinket's strength bonus

    public int getMagic() { return magic; } // Get trinket's magic bonus

    public int getSkill() { return skill; } // Get trinket's skill bonus

    public int getSpeed() { return speed; } // Get trinket's speed bonus

    public int getDefense() { return defense; } // Get trinket's defense bonus

    public int getResistance() { return resistance; } // Get trinket's resistance bonus

    public int getVision() { return vision; } // Get trinket's Visibility bonus

}
