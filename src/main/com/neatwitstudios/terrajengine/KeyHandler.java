package com.neatwitstudios.terrajengine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean isKPressed;
    private boolean isRPressed;

    private boolean is0Pressed;
    private boolean is1Pressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_K) {
            isKPressed = true;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            Main.exitFullScreen();
        }
        if (code == KeyEvent.VK_F11) {
            Main.toggleFullScreen();
        }

        if (code == KeyEvent.VK_M) {
            SoundManager.toggleMusic(0);
        }

        if (code == KeyEvent.VK_R) {
            isRPressed = true;
        }

        if (code == KeyEvent.VK_0) {
            is0Pressed = true;
        }
        if (code == KeyEvent.VK_1) {
            is1Pressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_K) {
            isKPressed = false;
        }
        if (code == KeyEvent.VK_R) {
            isRPressed = false;
        }

        if (code == KeyEvent.VK_0) {
            is0Pressed = false;
        }
        if (code == KeyEvent.VK_1) {
            is1Pressed = false;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isKPressed() {
        return isKPressed;
    }

    public boolean isRPressed() {
        return isRPressed;
    }

    public boolean is0Pressed() {
        return is0Pressed;
    }

    public boolean is1Pressed() {
        return is1Pressed;
    }
}
