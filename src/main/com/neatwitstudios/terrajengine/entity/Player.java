package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.*;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.BlockMapManager;
import com.neatwitstudios.terrajengine.map.BlockCollisionsChecker;

import java.awt.*;
import java.util.List;

public class Player extends Entity {

    private static final int SPEED = Block.SIZE * 7/64;
    private static final int JUMP_SPEED = Block.SIZE * 5 / 16;
    private static final int GRAVITY = Block.SIZE / 64;
    private static final int MAX_FALL_SPEED = Block.SIZE * 3/32;

    private int spriteCounter = 0;
    private int spriteNum = 0;

    private boolean isFacingRight = true;
    private boolean isAttacking = false;
    private int attackCounter = 0;
    private boolean endAttackSprite = false;

    private final KeyHandler keyHandler;
    private final AttackManager attackManager;

    public Player(KeyHandler keyHandler, AttackManager attackManager, int level) {
        initializePlayerByLevel(level);
        xSpeed = 0;
        ySpeed = 0;
        solidBounds = new Rectangle(
                Block.SIZE / 6,
                0,
                Block.SIZE * 2/3,
                Block.SIZE * 9/10
        );
        this.keyHandler = keyHandler;
        this.attackManager = attackManager;
    }

    private void initializePlayerByLevel(int level) {
        health = 100;
        switch (level) {
            case 0 -> {
                x = 0;
                y = Block.SIZE + 1;
            }
            case 1 -> {
                x = Block.SIZE * 3;
                y = Block.SIZE * 8 + 1;
            }
            default -> {
            }
        }
    }

    @Override
    public void update(BlockMapManager blockMapManager) {
        attackManager.damageEntities(List.of(this));
        handleAttacking();
        updatePositionAndSpeed(blockMapManager);
        updateSprite();
    }

    private void handleAttacking() {
        if (keyHandler.isKPressed()) {
            isAttacking = true;
            if (attackCounter == 0) {
                Attack attack = new Attack(
                        new Rectangle(
                                isFacingRight
                                        ? x + solidBounds.width / 2 + Block.SIZE / 2
                                        : x + solidBounds.width / 2 - Block.SIZE / 2,
                                y,
                                Block.SIZE / 2,
                                Block.SIZE
                        ),
                        40,
                        12,
                        this
                );
                attackManager.submitAttack(attack);
                SoundManager.playSE(2);
            }
        }
        if (isAttacking && ++attackCounter > 24) {
            isAttacking = false;
            attackCounter = 0;
            endAttackSprite = true;
        }
    }

    private void updatePositionAndSpeed(BlockMapManager blockMapManager) {
        boolean isJumping = false;
        boolean isStandingOnGround = BlockCollisionsChecker.isStandingOnGround(this, blockMapManager);
        if (keyHandler.isUpPressed() && isStandingOnGround) {
            this.ySpeed = JUMP_SPEED;
            isJumping = true;
        }
        if (!isJumping && isStandingOnGround) {
            ySpeed = 0;
        } else {
            ySpeed = Math.max(ySpeed - GRAVITY, -MAX_FALL_SPEED);
        }
        int newYSpeed = BlockCollisionsChecker.getAdjustedYDiffAvoidCollisionResettingOnHeadBump(this, blockMapManager);
        y += newYSpeed;
        if (isJumping && newYSpeed != 0) {
            SoundManager.playSE(1);
        }

        xSpeed = SPEED * (keyHandler.isRightPressed() ? 1 : 0) - SPEED * (keyHandler.isLeftPressed() ? 1 : 0);
        if (xSpeed > 0) {
            isFacingRight = true;
        } else if (xSpeed < 0) {
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

    @Override
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
}
