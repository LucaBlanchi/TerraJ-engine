package com.neatwitstudios.terrajengine;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static JFrame window;
    private static boolean isFullScreen = false;

    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("TerraJ Engine");

        ImageIcon icon = new ImageIcon(Main.class.getResource("/resources/static/icon.png"));
        window.setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setUndecorated(true);
        toggleFullScreen();

        window.setVisible(true);
        gamePanel.startGameThread();
    }

    public static void toggleFullScreen() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (!isFullScreen) {
            window.dispose();
            window.setUndecorated(true);
            gd.setFullScreenWindow(window);
        } else {
            gd.setFullScreenWindow(null);
            window.dispose();
            window.setUndecorated(false);
            window.setSize(1280, 720);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        }
        isFullScreen = !isFullScreen;
    }

    public static void exitFullScreen() {
        if (isFullScreen) {
            toggleFullScreen();
        }
    }
}
