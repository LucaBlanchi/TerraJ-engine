package org.neatwitentertainment.terrajengine;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("TerraJ Engine");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
