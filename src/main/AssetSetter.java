package main;

import Entity.ChaosUnit;
import Entity.Entity;
import Entity.LightUnit;
import Item.*;

import java.util.ArrayList;

public class AssetSetter {

    GamePanel gp;
    KeyHandler keyH;

    public AssetSetter(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    // Initialize player units and set their starting positions
    public void setLightUnits() {
        gp.simLightUnits.add(new LightUnit(gp, keyH, 30, 14));
        gp.simLightUnits.add(new LightUnit(gp, keyH, 31, 14));
    }

    // Initialize enemy units and set their starting positions
    public void setChaosUnits() {
        gp.simChaosUnits.add(new ChaosUnit(gp, 32, 15));
        gp.simChaosUnits.add(new ChaosUnit(gp, 32, 16));
    }

    // Copy units from one list to another
    public <T extends Entity> void copysetUnits(ArrayList<T> source, ArrayList<T> target) {
        target.clear();
        target.addAll(source);
    }

    public void setCursor() {
        int startCursorCol = gp.simLightUnits.get(0).getCol();
        int startCursorRow = gp.simLightUnits.get(0).getRow();
        gp.cursor.setStartingPosition(startCursorCol, startCursorRow);
    }
}

