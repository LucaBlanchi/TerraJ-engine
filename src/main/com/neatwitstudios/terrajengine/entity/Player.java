package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.*;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.CollisionsChecker;

import java.awt.*;
import java.util.List;

public class Player extends Entity {

    private static final int SPEED = Block.SIZE * 7/64;
    private static final int JUMP_SPEED = Block.SIZE * 5 / 16;
    private static final int GRAVITY = Block.SIZE / 64;
    private static final int MAX_FALL_SPEED = Block.SIZE * 3/32;

    private int spriteCounter;
    private int spriteNum;

    private boolean isFacingRight;
    private boolean isAttacking;
    private int attackCounter;
    private boolean endAttackSprite;

    private final KeyHandler keyHandler;
    private final AttackManager attackManager;
    private final CollisionsChecker collisionsChecker;

    public Player(KeyHandler keyHandler, AttackManager attackManager, CollisionsChecker collisionsChecker) {
        initializePlayerByMapId(0);
        solidBounds = new Rectangle(
                Block.SIZE / 6,
                0,
                Block.SIZE * 2/3,
                Block.SIZE * 9/10
        );

        this.keyHandler = keyHandler;
        this.attackManager = attackManager;
        this.collisionsChecker = collisionsChecker;
    }

    public void initializePlayerByMapId(int mapId) {
        health = 100;
        xSpeed = 0;
        ySpeed = 0;

        spriteCounter = 0;
        spriteNum = 0;
        isFacingRight = true;
        isAttacking = false;
        attackCounter = 0;
        endAttackSprite = false;

        switch (mapId) {
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
    public void update() {
        attackManager.damageEntities(List.of(this));
        handleAttacking();
        updatePositionAndSpeed();
        updateSprite();
    }

    private void handleAttacking() {
        if (keyHandler.isKPressed()) {
            isAttacking = true;
            if (attackCounter == 0) {
                Attack attack = new Attack(
                        new Rectangle(
                                isFacingRight
                                        ? x + solidBounds.width
                                        : x - solidBounds.width,
                                y + solidBounds.height / 4,
                                Block.SIZE / 2,
                                Block.SIZE / 2
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

    private void updatePositionAndSpeed() {
        boolean isJumping = false;
        boolean isStandingOnGround = collisionsChecker.isStandingOnGround(this);
        if (keyHandler.isUpPressed() && isStandingOnGround) {
            this.ySpeed = JUMP_SPEED;
            isJumping = true;
        }
        if (!isJumping && isStandingOnGround) {
            ySpeed = 0;
        } else {
            ySpeed = Math.max(ySpeed - GRAVITY, -MAX_FALL_SPEED);
        }
        int newYSpeed = collisionsChecker.getAdjustedYSpeedToAvoidCollision(this);
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
        x += collisionsChecker.getAdjustedXSpeedToAvoidCollisions(this);
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
        g2d.drawImage(
                SpritesManager.getSprites("player")[spriteNum],
                CoordConverter.getScreenX(x - Block.SIZE / 2, camera),
                CoordConverter.getScreenY(y + Block.SIZE, camera),
                CoordConverter.getResizedLength(Block.SIZE, camera),
                CoordConverter.getResizedLength(Block.SIZE, camera),
                null
        );
    }

    @Override
    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        g2d.setColor(Color.RED);
        g2d.drawRect(
                CoordConverter.getScreenX(x - solidBounds.width / 2, camera),
                CoordConverter.getScreenY(y + solidBounds.height, camera),
                CoordConverter.getResizedLength(solidBounds.width, camera),
                CoordConverter.getResizedLength(solidBounds.height, camera)
        );
        g2d.drawString(
                "Life: " + health,
                CoordConverter.getScreenX(x - Block.SIZE / 2, camera),
                CoordConverter.getScreenY(y, camera) - 50
        );

        g2d.setColor(Color.BLUE);
        Rectangle spriteRect = new Rectangle(
                CoordConverter.getScreenX(x - Block.SIZE / 2, camera),
                CoordConverter.getScreenY(y + Block.SIZE, camera),
                CoordConverter.getResizedLength(Block.SIZE, camera),
                CoordConverter.getResizedLength(Block.SIZE, camera)
        );
        g2d.drawRect(spriteRect.x, spriteRect.y, spriteRect.width, spriteRect.height);
    }
}
