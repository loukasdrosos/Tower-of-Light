package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    // Flags to track whether specific keys are currently pressed
    private boolean UpPressed, DownPressed, LeftPressed, RightPressed;
    private boolean APressed, ZPressed, WPressed, EPressed, ShiftPressed;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    // Not used in this implementation
    @Override
    public void keyTyped(KeyEvent e) {  }

    // Gets the key code of the pressed key
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Returns the keyCode associated with the key in this event

        // Set the corresponding flag to true when a key is pressed
        if (code == KeyEvent.VK_UP) {
            UpPressed = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            DownPressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            LeftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            RightPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            APressed = true;
        }
        if (code == KeyEvent.VK_Z) {
            ZPressed = true;
        }
        if (code == KeyEvent.VK_W) {
            WPressed = true;
        }
        if (code == KeyEvent.VK_E) {
            EPressed = true;
        }
        if (code == KeyEvent.VK_SHIFT) {
            ShiftPressed = true;
        }
    }

    @Override
    public void keyReleased (KeyEvent e){
        int code = e.getKeyCode();

        // Set the corresponding flag to false when a key is released
        if (code == KeyEvent.VK_UP) {
            UpPressed = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            DownPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            LeftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            RightPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            APressed = false;
        }
        if (code == KeyEvent.VK_Z) {
            ZPressed = false;
        }
        if (code == KeyEvent.VK_W) {
            WPressed = false;
        }
        if (code == KeyEvent.VK_E) {
            EPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            ShiftPressed = false;
        }
    }

    // GETTERS

    public boolean isUpPressed() {
        return UpPressed;
    }

    public boolean isDownPressed() {
        return DownPressed;
    }

    public boolean isLeftPressed() {
        return LeftPressed;
    }

    public boolean isRightPressed() {
        return RightPressed;
    }

    public boolean isAPressed() {
        return APressed;
    }

    public boolean isZPressed() {
        return ZPressed;
    }

    public boolean isWPressed() {
        return WPressed;
    }

    public boolean isEPressed() {
        return EPressed;
    }

    public boolean isShiftPressed() {
        return ShiftPressed;
    }
}

