package main;

import Entity.*;
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
        gp.simLightUnits.add(new Prince(gp, keyH, "Alm", Entity.UnitRace.Human, 30, 14));
 //       gp.simLightUnits.add(new Sage(gp, keyH, "Boey", Entity.UnitRace.Elf, 31, 15));
        gp.simLightUnits.add(new Cleric(gp, keyH, "Shade", Entity.UnitRace.Elf, 31, 14));
//        gp.simLightUnits.add(new DarkMage(gp, keyH, "Iago", Entity.UnitRace.Orc, 29, 15));
//        gp.simLightUnits.add(new Knight(gp, keyH, "Rudolf", Entity.UnitRace.Elf, 28, 14));
//        gp.simLightUnits.add(new Knight(gp, keyH, "Berkut", Entity.UnitRace.Orc, 28, 12));
//        gp.simLightUnits.add(new Mage(gp, keyH, "Robin", Entity.UnitRace.Human, 27, 15));
//        gp.simLightUnits.add(new Paladin(gp, keyH, "Valbar", Entity.UnitRace.Tauren, 27, 10));
         gp.simLightUnits.add(new Warrior(gp, keyH, "Ike", Entity.UnitRace.Human, 29, 14));
 //         gp.simLightUnits.add(new Princess(gp, keyH, "Celica", Entity.UnitRace.Human, 27, 13));
    }

    // Initialize enemy units and set their starting positions
    public void setChaosUnits() {
        gp.simChaosUnits.add(new FallenHero(gp, true, 32, 16));
        gp.simChaosUnits.add(new FallenHero(gp, true, 33, 14));
        gp.simChaosUnits.add(new Titan(gp, true, false,34, 15));
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

