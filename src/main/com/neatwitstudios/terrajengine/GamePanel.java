package com.neatwitstudios.terrajengine;

import com.neatwitstudios.terrajengine.entity.AttackManager;
import com.neatwitstudios.terrajengine.entity.EnemyManager;
import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.items.ItemsManager;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.BlockCollisionsChecker;
import com.neatwitstudios.terrajengine.map.BlockMapManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static final int BLOCKS_PER_ROW = 32;
    public static final int TILE_SIZE = WIDTH / BLOCKS_PER_ROW;
    public static final int SCREEN_CENTER_X = WIDTH / 2 - TILE_SIZE / 2;
    public static final int SCREEN_CENTER_Y = HEIGHT / 2 - TILE_SIZE / 2;

    private static final int MAX_FPS = 60;

    private static final boolean START_WITH_MUSIC = false;

    private final KeyHandler keyHandler = new KeyHandler();
    private AttackManager attackManager = new AttackManager();
    private BlockMapManager blockMapManager = new BlockMapManager(0);
    private BlockCollisionsChecker blockCollisionsChecker = new BlockCollisionsChecker(blockMapManager);
    private Player player = new Player(keyHandler, attackManager, blockCollisionsChecker, 0);
    private EnemyManager enemyManager = new EnemyManager(player, attackManager, blockCollisionsChecker, 0);
    private ItemsManager itemsManager = new ItemsManager(this, 0);

    private final transient Camera camera = new Camera(Block.SIZE * BLOCKS_PER_ROW, Block.SIZE * BLOCKS_PER_ROW * HEIGHT / WIDTH);

    private transient Thread gameThread;

    private double scaleFactorX = 1.0;
    private double scaleFactorY = 1.0;

    private int loadedLevel = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });

        loadLevel(0);

        if (START_WITH_MUSIC) {
            SoundManager.playMusic(0);
        }
    }

    private void handleResize() {
        int newWidth = this.getWidth();
        int newHeight = this.getHeight();

        scaleFactorX = (double) newWidth / WIDTH;
        scaleFactorY = (double) newHeight / HEIGHT;
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

    private void loadLevel(int level) {
        attackManager = new AttackManager();
        blockMapManager = new BlockMapManager(level);
        blockCollisionsChecker = new BlockCollisionsChecker(blockMapManager);
        itemsManager = new ItemsManager(this, level);
        player = new Player(keyHandler, attackManager, blockCollisionsChecker, level);
        enemyManager = new EnemyManager(player, attackManager, blockCollisionsChecker, level);

        camera.setCenterX(player.getX());
        camera.setCenterY(player.getY());

        loadedLevel = level;
    }

    public void loadNextLevel() {
        loadedLevel++;
        loadLevel(loadedLevel);
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
        enemyManager.updateEnemies(blockMapManager);
        player.update(blockMapManager);
        itemsManager.checkCollisions(player);
        if (player.getHealth() <= 0) {
            loadLevel(0);
        }
        if (keyHandler.isRPressed()) {
            loadLevel(loadedLevel);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.scale(scaleFactorX, scaleFactorY);

        camera.setCenterX(player.getX());
        camera.setCenterY(player.getY());
        blockMapManager.draw(g2d, camera);
        enemyManager.drawEnemies(g2d, camera);
        itemsManager.drawItems(g2d, camera);
        player.draw(g2d, camera);

        g2d.dispose();
    }
}
