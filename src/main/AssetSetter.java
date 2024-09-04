package main;

import Item.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setItem() {
        gp.items[0] = new MainWeapon();
        gp.items[0].setCol(28);
        gp.items[0].setRow(14);
        gp.items[0].updatePosition();

        gp.items[1] = new Potion();
        gp.items[1].setCol(30);
        gp.items[1].setRow(12);
        gp.items[1].updatePosition();

        gp.items[2] = new Shield();
        gp.items[2].setCol(29);
        gp.items[2].setRow(12);
        gp.items[2].updatePosition();
    }
}
