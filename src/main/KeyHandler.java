package main;

import Entity.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {

    public boolean CursorUpPressed, CursorDownPressed, CursorLeftPressed, CursorRightPressed;
    public boolean UnitUpPressed;
    public boolean UnitDownPressed;
    public boolean UnitLeftPressed;
    public boolean UnitRightPressed;

    private ArrayList<LightUnit> units; // List of all players
    private Cursor cursor; // Reference to the Cursor object
    private LightUnit selectedUnit; // Reference to the currently selected player

    // Constructor to initialize KeyHandler with references to players and cursor
    public KeyHandler(ArrayList<LightUnit> units, Cursor cursor) {
        this.units = units;
        this.cursor = cursor;
        this.selectedUnit = null;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Returns the keyCode associated with the key in this event

        if (code == KeyEvent.VK_UP) {
            CursorUpPressed = true;
            if (selectedUnit != null && selectedUnit.getSelected() == true) {
                UnitUpPressed = true;
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            CursorDownPressed = true;
            if (selectedUnit != null && selectedUnit.getSelected() == true) {
                UnitDownPressed = true;
            }
        }
        if (code == KeyEvent.VK_LEFT) {
            CursorLeftPressed = true;
            if (selectedUnit != null && selectedUnit.getSelected() == true) {
                UnitLeftPressed = true;
            }
        }
        if (code == KeyEvent.VK_RIGHT) {
            CursorRightPressed = true;
            if (selectedUnit != null && selectedUnit.getSelected() == true) {
                UnitRightPressed = true;
            }
        }
        if (code == KeyEvent.VK_A) {
            // Check if the cursor's position matches the position of any player
            for (LightUnit unit : units) {
                if (cursor.getX() == unit.getX() && cursor.getY() == unit.getY()) {
                    unit.setSelected(true);
                    selectedUnit = unit; // Set the selected player
                    break; // Exit loop once a match is found
                }
            }
        }
        if (code == KeyEvent.VK_Z) {
            if (selectedUnit != null && selectedUnit.getSelected() == true) {
                selectedUnit.setSelected(false);
                selectedUnit = null; // Deselect the player
            }
        }
    }

    @Override
    public void keyReleased (KeyEvent e){
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            CursorUpPressed = false;
            if (selectedUnit != null && selectedUnit.getSelected() == true) {
                UnitUpPressed = false;
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            CursorDownPressed = false;
            if (selectedUnit != null && selectedUnit.getSelected() == true) {
                UnitDownPressed = false;
            }
        }
        if (code == KeyEvent.VK_LEFT) {
            CursorLeftPressed = false;
            if (selectedUnit != null && selectedUnit.getSelected() == true) {
                UnitLeftPressed = false;
            }
        }
        if (code == KeyEvent.VK_RIGHT) {
            CursorRightPressed = false;
            if (selectedUnit != null && selectedUnit.getSelected() == true) {
                UnitRightPressed = false;
            }
        }
    }

    public boolean UnitSelected() {
        if (selectedUnit != null && selectedUnit.getSelected() == true) {
            return true;
        }
        else {
            return false;
        }
    }
}

