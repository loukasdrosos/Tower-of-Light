package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    // Flags to track whether specific keys are currently pressed
    private boolean UpPressed, DownPressed, LeftPressed, RightPressed;
    private boolean APressed, ZPressed, WPressed, EPressed, ShiftPressed;
    private boolean ENTERPressed = false;
    private boolean QPressed = true;

    // DEBUG
    private boolean checkDrawTime = false;

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

        if (gp.gameState == gp.titleState && !ENTERPressed) {
            if (code == KeyEvent.VK_ENTER) {
                gp.stopMusic();
                gp.playSE(3);
                gp.gameState = gp.controlsState;  // Transition to controlsState
                ENTERPressed = true;
            }
        }

        if (gp.gameState == gp.controlsState && !ENTERPressed) {
            if (code == KeyEvent.VK_ENTER) {
                gp.playSE(6);
                gp.gameState = gp.playState;  // Transition to playState
                ENTERPressed = true;
            }
        }

        if (gp.gameState == gp.playState) {
            ENTERPressed = false;
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
            if (code == KeyEvent.VK_P) {
                gp.playSE(6);
                gp.gameState = gp.controlsState;
            }
            if (code == KeyEvent.VK_Q) {
                QPressed = !QPressed; // Toggle between showing stats and details
            }
        }

        // DEBUG
        if (code == KeyEvent.VK_ALT) {
            if (!checkDrawTime) {
                checkDrawTime = true;
            } else if (checkDrawTime) {
                checkDrawTime = false;
            }
        }
    }

    @Override
    public void keyReleased (KeyEvent e){
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState || gp.gameState == gp.controlsState) {
            if (code == KeyEvent.VK_ENTER) {
                ENTERPressed = false;
            }
        }

        if (gp.gameState == gp.playState) {
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
    }

    // GETTERS && SETTERS when needed

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

    public boolean isQPressed() {
        return QPressed;
    }

    public void setQPressed(boolean x) {
        QPressed = x;
    }

    public boolean getCheckDrawTime() {
        return checkDrawTime;
    }
}

