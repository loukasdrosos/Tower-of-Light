package Item;

public class Potion extends Item{
    protected int heal;
    protected int uses;

    public int getHeal() { return heal; } // Get the potion's healing value

    public int getUses() { return uses; } // Get the potion's uses

    public void usePotion() {
        if (uses > 0) {
            uses--;
        }
    }
}
