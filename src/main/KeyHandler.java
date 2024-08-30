package main;

import Entity.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean CursorUpPressed, CursorDownPressed, CursorLeftPressed, CursorRightPressed;
    private boolean UnitUpPressed, UnitDownPressed, UnitLeftPressed, UnitRightPressed;
    private boolean APressed, ZPressed;

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
            APressed = true;
        }
        if (code == KeyEvent.VK_Z) {
            ZPressed = true;
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
        if (code == KeyEvent.VK_A) {
            APressed = false;
        }
        if (code == KeyEvent.VK_Z) {
            ZPressed = false;
        }
    }

    // GETTERS

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

    public boolean isAPressed() {
        return APressed;
    }

    public boolean isZPressed() {
        return ZPressed;
    }
}

