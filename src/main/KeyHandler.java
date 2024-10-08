package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    // Flags to track whether specific keys are currently pressed
    private boolean UpPressed, DownPressed, LeftPressed, RightPressed;
    private boolean APressed, ZPressed, WPressed, EPressed, VPressed, BPressed, ShiftPressed, XPressed, DPressed, SPressed, CPressed, SpacePressed;
    private boolean ENTERPressed = false;
    private boolean QPressed = true;

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
                gp.stopSE();
                gp.playSE(3);
                gp.gameState = gp.introState;  // Transition to introState
                ENTERPressed = true;
            }
        }

        if (gp.gameState == gp.introState && !ENTERPressed) {
            if (code == KeyEvent.VK_ENTER) {
                gp.playSE(6);
                gp.gameState = gp.introState2;  // Transition to introState2
                ENTERPressed = true;
            }
        }

        if (gp.gameState == gp.introState2 && !ENTERPressed) {
            if (code == KeyEvent.VK_ENTER) {
                gp.playSE(6);
                gp.playMusic(2);
                gp.gameState = gp.playState;  // Transition to playState
                ENTERPressed = true;
            }
        }

        if (gp.gameState == gp.gameOverState) {
            if (code == KeyEvent.VK_R) {
                gp.stopMusic();
                gp.resetGame();
            } else if (code == KeyEvent.VK_Q) {
                System.exit(0);
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
            if (code == KeyEvent.VK_X) {
                XPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                DPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                SPressed = true;
            }
            if (code == KeyEvent.VK_B) {
                BPressed = true;
            }
            if (code == KeyEvent.VK_C) {
                CPressed = true;
            }
            if (code == KeyEvent.VK_V) {
                VPressed = true;
            }
            if (code == KeyEvent.VK_SHIFT) {
                ShiftPressed = true;
            }
            if (code == KeyEvent.VK_SPACE) {
                SpacePressed = true;
            }
            if (code == KeyEvent.VK_1) {
                gp.ui.scrollLog(-1);  // Scroll up Game Log
            }
            if (code == KeyEvent.VK_2) {
                gp.ui.scrollLog(1);  // Scroll down Game Log
            }
            if (code == KeyEvent.VK_P) {
                gp.playSE(6);
                gp.gameState = gp.controlsState;
            }
            if (code == KeyEvent.VK_Q) {
                QPressed = !QPressed; // Toggle between showing stats and details
            }
        }
    }

    @Override
    public void keyReleased (KeyEvent e){
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState || gp.gameState == gp.introState || gp.gameState == gp.introState2 || gp.gameState == gp.controlsState) {
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
            if (code == KeyEvent.VK_B) {
                BPressed = false;
            }
            if (code == KeyEvent.VK_X) {
                XPressed = false;
            }
            if (code == KeyEvent.VK_D) {
                DPressed = false;
            }
            if (code == KeyEvent.VK_S) {
                SPressed = false;
            }
            if (code == KeyEvent.VK_C) {
                CPressed = false;
            }
            if (code == KeyEvent.VK_V) {
                VPressed = false;
            }
            if (code == KeyEvent.VK_SPACE) {
                SpacePressed = false;
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

    public boolean isXPressed() {
        return XPressed;
    }

    public boolean isSpacePressed() { return SpacePressed; }

    public boolean isDPressed() {
        return DPressed;
    }

    public boolean isSPressed() {
        return SPressed;
    }

    public boolean isCPressed() {
        return CPressed;
    }

    public boolean isVPressed() {
        return VPressed;
    }

    public boolean isBPressed() {
        return BPressed;
    }
}

