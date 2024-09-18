package org.neatwitentertainment.terrajengine;

import org.neatwitentertainment.terrajengine.entity.Player;
import org.neatwitentertainment.terrajengine.map.MapManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 768;
    public static final int HEIGHT = 432;
    public static final int TILE_SIZE = 48;
    public static final int SCREEN_CENTER_X = WIDTH / 2 - TILE_SIZE / 2;
    public static final int SCREEN_CENTER_Y = HEIGHT / 2 - TILE_SIZE / 2;

    private static final int MAX_FPS = 60;

    private final transient KeyHandler keyHandler = new KeyHandler();
    private final transient Player player = new Player(keyHandler);
    private final transient MapManager mapManager = new MapManager();
    private final transient CollisionsChecker collisionsChecker = new CollisionsChecker();

    private transient Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while (gameThread != null) {

            long currentTime = System.nanoTime();

            update();
            repaint();

            long elapsedTime = System.nanoTime() - currentTime;
            sleepToCapFps(elapsedTime);
        }
    }

    private void sleepToCapFps(long elapsedTime) {
        long sleepTime = 1000 / MAX_FPS - elapsedTime / 1000000;
        if (sleepTime < 0) {
            sleepTime = 0;
        }
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        player.update(mapManager, collisionsChecker);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        mapManager.draw(g2d, player.getX(), player.getY());
        player.draw(g2d);
        g2d.dispose();
    }
}
