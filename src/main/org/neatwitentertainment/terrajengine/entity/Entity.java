package org.neatwitentertainment.terrajengine.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    protected int worldX;
    protected int worldY;
    protected int speed;

    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;

    protected String direction;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    protected Rectangle solidBounds;
    protected boolean isCollisionOn = false;

    public int getWorldX() {
        return worldX;
    }

    public String getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWorldY() {
        return worldY;
    }

    public Rectangle getSolidBounds() {
        return solidBounds;
    }

    public void setCollisionOn(boolean isCollisionOn) {
        this.isCollisionOn = isCollisionOn;
    }
}
