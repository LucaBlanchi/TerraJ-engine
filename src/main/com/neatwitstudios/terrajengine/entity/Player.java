package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.MapManager;
import com.neatwitstudios.terrajengine.CollisionsChecker;
import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.KeyHandler;

import java.awt.*;

public class Player {

    private int x = 0;
    private int y = Block.SIZE + 1;

    private static final int SPEED = Block.SIZE * 7/64;
    private static final int JUMP_SPEED = Block.SIZE * 13/32;
    private static final int GRAVITY = Block.SIZE / 32;
    private static final int MAX_FALL_SPEED = Block.SIZE * 3/32;

    private final Rectangle solidBounds = new Rectangle(
            Block.SIZE / 6,
            0,
            Block.SIZE * 2/3,
            Block.SIZE * 9/10
    );

    private int spriteCounter = 0;
    private int spriteNum = 0;

    private int xDiff = 0;
    private int yDiff = 0;

    private final KeyHandler keyHandler;

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    public void update(MapManager mapManager) {
        updatePositionAndSpeed(mapManager);
        updateSprite();
    }

    private void updatePositionAndSpeed(MapManager mapManager) {
        if (keyHandler.isUpPressed() && CollisionsChecker.isCollidingDown(this, mapManager)) {
            this.yDiff = JUMP_SPEED;
        }
        yDiff = Math.max(yDiff - GRAVITY, -MAX_FALL_SPEED);
        y += CollisionsChecker.getAdjustedYDiffAvoidCollisionResettingOnHeadBump(this, mapManager);

        xDiff = SPEED * (keyHandler.isRightPressed() ? 1 : 0) - SPEED * (keyHandler.isLeftPressed() ? 1 : 0);
        x += CollisionsChecker.getAdjustedXDiffAvoidCollision(this, mapManager);
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

    public Rectangle getSolidBounds() {
        return solidBounds;
    }

    public int getXDiff() {
        return xDiff;
    }

    public int getYDiff() {
        return yDiff;
    }

    public void setYDiff(int yDiff) {
        this.yDiff = yDiff;
    }
}
