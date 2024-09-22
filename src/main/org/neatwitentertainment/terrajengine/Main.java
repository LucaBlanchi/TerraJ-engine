package org.neatwitentertainment.terrajengine;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("TerraJ Engine");

        ImageIcon icon = new ImageIcon(Main.class.getResource("/resources/static/icon.png"));
        window.setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setPreferredSize(new Dimension(1280, 720));

        window.pack();
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
