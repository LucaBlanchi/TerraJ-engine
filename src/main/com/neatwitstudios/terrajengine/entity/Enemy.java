package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.Camera;
import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.BlockCollisionsChecker;
import com.neatwitstudios.terrajengine.map.BlockMapManager;

import java.awt.*;

public class Enemy extends Entity {

    private static final int SPEED = Block.SIZE * 3/64;
    private static final int GRAVITY = Block.SIZE / 64;
    private static final int MAX_FALL_SPEED = Block.SIZE * 3/32;

    private int spriteNum = 0;

    private final Player player;

    public Enemy(int x, int y, Player player) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.solidBounds = new Rectangle(
                Block.SIZE / 6,
                0,
                Block.SIZE * 2/3,
                Block.SIZE * 9/10
        );
    }

    @Override
    public void update(BlockMapManager blockMapManager) {
        updatePositionAndSpeed(blockMapManager);
        updateSprite();
    }

    private void updatePositionAndSpeed(BlockMapManager blockMapManager) {
        if (isPlayerInRange()) {
            if (player.getX() < x) {
                xSpeed = -SPEED;
            } else {
                xSpeed = SPEED;
            }
            x += BlockCollisionsChecker.getAdjustedXDiffAvoidCollision(this, blockMapManager);
        }

        boolean isStandingOnGround = BlockCollisionsChecker.isStandingOnGround(this, blockMapManager);
        if (isStandingOnGround) {
            ySpeed = 0;
        } else {
            ySpeed = Math.max(ySpeed - GRAVITY, -MAX_FALL_SPEED);
        }
        y += BlockCollisionsChecker.getAdjustedYDiffAvoidCollisionResettingOnHeadBump(this, blockMapManager);
    }

    private boolean isPlayerInRange() {
        return Math.abs(player.getX() - x) < Block.SIZE * 5 && Math.abs(player.getX() - x) > Block.SIZE / 10;
    }

    private void updateSprite() {
        if (x < player.getX()) {
            spriteNum = 1;
        } else {
            spriteNum = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        int enemyScreenX = GamePanel.SCREEN_CENTER_X - (camera.getCenterX() - x) * GamePanel.TILE_SIZE / Block.SIZE;
        int enemyScreenY = GamePanel.SCREEN_CENTER_Y + (camera.getCenterY() - y) * GamePanel.TILE_SIZE / Block.SIZE;

        g2d.drawImage(
                SpritesManager.getSprites("enemy")[spriteNum],
                enemyScreenX,
                enemyScreenY,
                GamePanel.TILE_SIZE,
                GamePanel.TILE_SIZE,
                null
        );
    }
}
