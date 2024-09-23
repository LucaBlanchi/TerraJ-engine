package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.map.MapManager;
import com.neatwitstudios.terrajengine.CollisionsChecker;
import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.KeyHandler;

import java.awt.*;

public class Player {

    private int x;
    private int y;
    private final int speed;
    private final Rectangle solidBounds;

    private int spriteCounter = 0;
    private int spriteNum = 0;

    private static final int GRAVITY = 10 * GamePanel.TILE_SIZE / 120;
    private int ySpeed = 0;

    private final KeyHandler keyHandler;

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        x = 0;
        y = GamePanel.TILE_SIZE * 16 - 1;
        speed = 10 * GamePanel.TILE_SIZE / 120;
        solidBounds = new Rectangle(
                GamePanel.TILE_SIZE / 6,
                GamePanel.TILE_SIZE / 10,
                GamePanel.TILE_SIZE * 2/3,
                GamePanel.TILE_SIZE * 9 / 10
        );
    }

    public void update(MapManager mapManager) {
        updatePositionAndSpeed(mapManager);
        updateSprite();
    }

    private void updatePositionAndSpeed(MapManager mapManager) {
        if (CollisionsChecker.isCollidingUp(this, mapManager)) {
            ySpeed = 0;
        }
        int yDiff = this.ySpeed - GRAVITY;
        if (yDiff < 0 && !CollisionsChecker.isCollidingDown(this, mapManager)) {
            y -= yDiff;
        }
        if (yDiff > 0 && !CollisionsChecker.isCollidingUp(this, mapManager)) {
            y -= yDiff;
        }
        if (this.ySpeed > 0) {
            this.ySpeed -= 1;
        } else {
            this.ySpeed = 0;
        }

        if (keyHandler.isUpPressed()
                && !CollisionsChecker.isCollidingUp(this, mapManager)
                && CollisionsChecker.isCollidingDown(this, mapManager)) {
                this.ySpeed = 35 * GamePanel.TILE_SIZE / 120;
        }
        if (keyHandler.isLeftPressed() && !CollisionsChecker.isCollidingLeft(this, mapManager)) {
            x -= speed;
        }
        if (keyHandler.isRightPressed() && !CollisionsChecker.isCollidingRight(this, mapManager)) {
            x += speed;
        }
    }

    private void updateSprite() {
        if (!keyHandler.isUpPressed() && !keyHandler.isLeftPressed() && !keyHandler.isRightPressed()) {
            spriteCounter = 12;
            return;
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum + 1) % 2;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(
                SpritesManager.getSprites("player")[spriteNum],
                GamePanel.SCREEN_CENTER_X,
                GamePanel.SCREEN_CENTER_Y,
                GamePanel.TILE_SIZE,
                GamePanel.TILE_SIZE,
                null
        );
    }

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
