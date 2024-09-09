package main;

import Entity.*;
import Item.*;

import java.util.ArrayList;

public class AssetSetter {

    GamePanel gp;
    KeyHandler keyH;
    Lightbringer Lightbringer = new Lightbringer();

    public AssetSetter(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    // Initialize player units and set their starting positions
    public void setLightUnits() {
        gp.simLightUnits.add(new Prince(gp, keyH, 30, 14, Lightbringer));
        gp.simLightUnits.add(new Prince(gp, keyH, 31, 14, Lightbringer));
    }

    // Initialize enemy units and set their starting positions
    public void setChaosUnits() {
        gp.simChaosUnits.add(new HeraldOfChaos(gp, false, true, 32, 15, Lightbringer));
        gp.simChaosUnits.add(new HeraldOfChaos(gp, false, true, 32, 13, Lightbringer));
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

