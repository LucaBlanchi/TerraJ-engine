package com.neatwitstudios.terrajengine.map;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.camera.Camera;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BackgroundAndForegroundManager {

    private final List<BackgroundOrForeground> background = new ArrayList<>();
    private final List<BackgroundOrForeground> foreground = new ArrayList<>();

    public BackgroundAndForegroundManager() {}

    public void loadBgAndFg(int mapId) {
        background.clear();
        foreground.clear();
        try {
            switch (mapId) {
                case 0 -> {
                    background.add(new BackgroundOrForeground(
                            -10 * Block.SIZE,
                            20 * Block.SIZE,
                            Block.SIZE * 60,
                            Block.SIZE * 40,
                            ImageIO.read(BackgroundOrForeground.class.getResourceAsStream("/static/backgrounds/clouds.png")),
                            20)
                    );
                    background.add(new BackgroundOrForeground(
                            10 * Block.SIZE,
                            15 * Block.SIZE,
                            Block.SIZE * 3,
                            Block.SIZE * 3,
                            ImageIO.read(BackgroundOrForeground.class.getResourceAsStream("/static/backgrounds/balloon.png")),
                            5)
                    );
                }
                case 1 -> {
                    background.add(new BackgroundOrForeground(
                            -10 * Block.SIZE,
                            20 * Block.SIZE,
                            Block.SIZE * 60,
                            Block.SIZE * 40,
                            ImageIO.read(BackgroundOrForeground.class.getResourceAsStream("/static/backgrounds/mountains.png")),
                            10)
                    );
                    foreground.add(new BackgroundOrForeground(
                            8 * Block.SIZE,
                            3 * Block.SIZE,
                            Block.SIZE * 3,
                            Block.SIZE * 3,
                            ImageIO.read(BackgroundOrForeground.class.getResourceAsStream("/static/backgrounds/fossil.png")),
                            0)
                    );
                }
                default -> {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawBackground(Graphics2D g2d, Camera camera) {
        drawList(g2d, camera, background);
    }

    public void drawForeground(Graphics2D g2d, Camera camera) {
        drawList(g2d, camera, foreground);
    }

    private void drawList(Graphics2D g2d, Camera camera, List<BackgroundOrForeground> list) {
        for (BackgroundOrForeground bg : list) {
            g2d.drawImage(
                    bg.getImage(),
                    CoordConverter.getScreenX(bg.getX() - getXDisplacement(camera, bg), camera),
                    CoordConverter.getScreenY(bg.getY(), camera),
                    CoordConverter.getResizedLength(bg.getWidth(), camera),
                    CoordConverter.getResizedLength(bg.getHeight(), camera),
                    null
            );
        }
    }

    private int getXDisplacement(Camera camera, BackgroundOrForeground bg) {
        if (bg.getDistance() == 0) {
            return 0;
        }
        return camera.getCenterX() / bg.getDistance();
    }
}
