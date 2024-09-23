package com.neatwitstudios.terrajengine.entity;

import java.awt.*;

public class Entity {

    protected int x;
    protected int y;
    protected int speed;

    protected boolean isGoingUp;
    protected boolean isGoingLeft;
    protected boolean isGoingRight;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    protected Rectangle solidBounds;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle getSolidBounds() {
        return solidBounds;
    }
}
