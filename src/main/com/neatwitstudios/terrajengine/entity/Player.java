package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.*;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.BlockMapManager;
import com.neatwitstudios.terrajengine.map.BlockCollisionsChecker;

import java.awt.*;

public class Player {

    private int x = 0;
    private int y = Block.SIZE + 1;

    private static final int SPEED = Block.SIZE * 7/64;
    private static final int JUMP_SPEED = Block.SIZE * 5 / 16;
    private static final int GRAVITY = Block.SIZE / 64;
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

    private boolean isFacingRight = true;
    private boolean isAttacking = false;
    private int attackCounter = 0;
    private boolean endAttackSprite = false;

    private final KeyHandler keyHandler;

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    public void update(BlockMapManager blockMapManager) {
        if (keyHandler.isKPressed()) {
            isAttacking = true;
            if (attackCounter == 0) {
                SoundManager.playSE(2);
            }
        }
        if (isAttacking && ++attackCounter > 24) {
            isAttacking = false;
            attackCounter = 0;
            endAttackSprite = true;
        }
        updatePositionAndSpeed(blockMapManager);
        updateSprite();
    }

    private void updatePositionAndSpeed(BlockMapManager blockMapManager) {
        boolean isJumping = false;
        boolean isStandingOnGround = BlockCollisionsChecker.isStandingOnGround(this, blockMapManager);
        if (keyHandler.isUpPressed() && isStandingOnGround) {
            this.yDiff = JUMP_SPEED;
            isJumping = true;
        }
        if (!isJumping && isStandingOnGround) {
            yDiff = 0;
        } else {
            yDiff = Math.max(yDiff - GRAVITY, -MAX_FALL_SPEED);
        }
        int newYDiff = BlockCollisionsChecker.getAdjustedYDiffAvoidCollisionResettingOnHeadBump(this, blockMapManager);
        y += newYDiff;
        if (isJumping && newYDiff != 0) {
            SoundManager.playSE(1);
        }

        xDiff = SPEED * (keyHandler.isRightPressed() ? 1 : 0) - SPEED * (keyHandler.isLeftPressed() ? 1 : 0);
        if (xDiff > 0) {
            isFacingRight = true;
        } else if (xDiff < 0) {
            isFacingRight = false;
        }
        x += BlockCollisionsChecker.getAdjustedXDiffAvoidCollision(this, blockMapManager);
    }

    private void updateSprite() {
        if (isAttacking) {
            spriteNum = isFacingRight ? 2 : 3;
            return;
        }
        if (endAttackSprite) {
            spriteNum = isFacingRight ? 0 : 1;
            endAttackSprite = false;
            return;
        }
        if (!keyHandler.isUpPressed() && !keyHandler.isLeftPressed() && !keyHandler.isRightPressed()) {
            spriteCounter = 12;
        } else if (++spriteCounter > 12) {
            spriteNum = (spriteNum + 1) % 2;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2d, Camera camera) {
        int playerScreenX = GamePanel.SCREEN_CENTER_X - (camera.getCenterX() - x) * GamePanel.TILE_SIZE / Block.SIZE;
        int playerScreenY = GamePanel.SCREEN_CENTER_Y + (camera.getCenterY() - y) * GamePanel.TILE_SIZE / Block.SIZE;

        g2d.drawImage(
                SpritesManager.getSprites("player")[spriteNum],
                playerScreenX,
                playerScreenY,
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
