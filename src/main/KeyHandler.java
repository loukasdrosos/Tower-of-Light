package main;

import Entity.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean CursorUpPressed, CursorDownPressed, CursorLeftPressed, CursorRightPressed;
    private boolean UnitUpPressed, UnitDownPressed, UnitLeftPressed, UnitRightPressed;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Returns the keyCode associated with the key in this event

        if (code == KeyEvent.VK_UP) {
            CursorUpPressed = true;
            if (gp.selectedUnit != null) {
                UnitUpPressed = true;
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            CursorDownPressed = true;
            if (gp.selectedUnit != null) {
                UnitDownPressed = true;
            }
        }
        if (code == KeyEvent.VK_LEFT) {
            CursorLeftPressed = true;
            if (gp.selectedUnit != null) {
                UnitLeftPressed = true;
            }
        }
        if (code == KeyEvent.VK_RIGHT) {
            CursorRightPressed = true;
            if (gp.selectedUnit != null) {
                UnitRightPressed = true;
            }
        }
        if (code == KeyEvent.VK_A) {
            // Check if the cursor's position matches the position of any player unit (LightUnit)
           if (gp.selectedUnit == null) {
                for (LightUnit unit : gp.simLightUnits) {
                    if (gp.cursor.getX() == unit.getX() && gp.cursor.getY() == unit.getY()) {
                        gp.selectedUnit = unit; // Select player unit
                        gp.selectedUnit.setIsSelected(true); //Activate the selected player unit
                        break; // Exit loop once a match is found
                    }
                }
           }
        }
        if (code == KeyEvent.VK_Z) {
            if (gp.selectedUnit != null) {
                gp.selectedUnit.setIsSelected(false);
                gp.selectedUnit = null; // Deselect the player
            }
        }
    }

    @Override
    public void keyReleased (KeyEvent e){
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            CursorUpPressed = false;
            if (gp.selectedUnit != null) {
                UnitUpPressed = false;
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            CursorDownPressed = false;
            if (gp.selectedUnit != null) {
                UnitDownPressed = false;
            }
        }
        if (code == KeyEvent.VK_LEFT) {
            CursorLeftPressed = false;
            if (gp.selectedUnit != null) {
                UnitLeftPressed = false;
            }
        }
        if (code == KeyEvent.VK_RIGHT) {
            CursorRightPressed = false;
            if (gp.selectedUnit != null) {
                UnitRightPressed = false;
            }
        }
    }

    // GETTERS && SETTERS

    public boolean isCursorUpPressed() {
        return CursorUpPressed;
    }

    public boolean isCursorDownPressed() {
        return CursorDownPressed;
    }

    public boolean isCursorLeftPressed() {
        return CursorLeftPressed;
    }

    public boolean isCursorRightPressed() {
        return CursorRightPressed;
    }

    public boolean isUnitUpPressed() {
        return UnitUpPressed;
    }

    public boolean isUnitDownPressed() {
        return UnitDownPressed;
    }

    public boolean isUnitLeftPressed() {
        return UnitLeftPressed;
    }

    public boolean isUnitRightPressed() {
        return UnitRightPressed;
    }
}

