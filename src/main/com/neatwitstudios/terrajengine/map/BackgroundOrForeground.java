package com.neatwitstudios.terrajengine.map;

import java.awt.image.BufferedImage;

public class BackgroundOrForeground {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final BufferedImage image;
    private final int distance;

    public BackgroundOrForeground(int x, int y, int width, int height, BufferedImage image, int distance) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.distance = distance;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getDistance() {
        return distance;
    }
}
