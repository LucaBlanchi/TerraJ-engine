package org.neatwitentertainment.terrajengine.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    protected int x;
    protected int y;
    protected int speed;
    protected Direction direction;

    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    protected Rectangle solidBounds;

    public int getX() {
        return x;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getY() {
        return y;
    }

    public Rectangle getSolidBounds() {
        return solidBounds;
    }
}
