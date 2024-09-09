package Item;

public class Trinket extends Item{

    protected int defense; // Bonus defence the item offers
    protected int resistance; // Bonus resistance the item offer
    protected int speed; // Bonus speed the item offer

    public int getDefense() { return defense; } // Get trinket's defense bonus

    public int getResistance() { return resistance; } // Get trinket's resistance bonus

    public int getSpeed() { return speed; } // Get trinket's speed bonus

}
