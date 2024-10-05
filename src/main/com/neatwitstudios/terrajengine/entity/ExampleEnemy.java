package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.CollisionsChecker;

import java.awt.*;

public class ExampleEnemy extends Entity {

    private static final int SPEED = Block.SIZE * 3/64;
    private static final int GRAVITY = Block.SIZE / 64;
    private static final int MAX_FALL_SPEED = Block.SIZE * 3/32;

    private int spriteNum = 0;
    private int attackCoolDown = 0;

    private boolean isFacingRight = false;

    private final Player player;
    private final AttackManager attackManager;
    private final CollisionsChecker collisionsChecker;

    public ExampleEnemy(int x, int y, Player player, AttackManager attackManager, CollisionsChecker collisionsChecker) {
        this.player = player;
        this.attackManager = attackManager;
        this.collisionsChecker = collisionsChecker;

        this.x = x;
        this.y = y;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.solidBounds = new Rectangle(
                Block.SIZE / 6,
                0,
                Block.SIZE * 2/3,
                Block.SIZE * 3/10
        );
        this.health = 100;
    }

    @Override
    public void update() {
        handleAttacking();
        updatePositionAndSpeed();
        updateSprite();
    }

    private void handleAttacking() {
        if (Math.abs(player.getX() - x) < Block.SIZE / 3 && Math.abs(player.getY() - y) < Block.SIZE / 3 && attackCoolDown == 0) {
            attackManager.submitAttack(new Attack(
                    new Rectangle(
                            x + (isFacingRight ? solidBounds.width : -solidBounds.width) / 2,
                            y + solidBounds.height / 2,
                            Block.SIZE / 2,
                            Block.SIZE / 4
                    ),
                    30,
                    12,
                    this
            ));
            attackCoolDown = 60;
        }
        if (attackCoolDown > 0) {
            attackCoolDown--;
        }
    }

    private void updatePositionAndSpeed() {
        if (isPlayerInRange()) {
            if (player.getX() < x) {
                xSpeed = -SPEED;
                isFacingRight = false;
            } else {
                xSpeed = SPEED;
                isFacingRight = true;
            }
            x += collisionsChecker.getAdjustedXSpeedToAvoidCollisions(this);
        }

        boolean isStandingOnGround = collisionsChecker.isStandingOnGround(this);
        if (isStandingOnGround) {
            ySpeed = 0;
        } else {
            ySpeed = Math.max(ySpeed - GRAVITY, -MAX_FALL_SPEED);
        }
        y += collisionsChecker.getAdjustedYSpeedToAvoidCollision(this);
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
        g2d.drawImage(
                SpritesManager.getSprites("enemy")[spriteNum],
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
                CoordConverter.getScreenY(y, camera) - 30
        );
        g2d.drawString(
                "Attack cooldown: " + attackCoolDown,
                CoordConverter.getScreenX(x - Block.SIZE / 2, camera),
                CoordConverter.getScreenY(y, camera) - 40
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
