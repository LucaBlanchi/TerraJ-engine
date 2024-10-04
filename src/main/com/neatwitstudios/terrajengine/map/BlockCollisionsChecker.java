package com.neatwitstudios.terrajengine.map;

import com.neatwitstudios.terrajengine.entity.Entity;

public class BlockCollisionsChecker implements CollisionsChecker {

    private final BlockMapManager blockMapManager;

    public BlockCollisionsChecker(BlockMapManager blockMapManager) {
        this.blockMapManager = blockMapManager;
    }

    public boolean isStandingOnGround(Entity entity) {
        int leftX = entity.getX() + entity.getSolidBounds().x;
        int rightX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width;
        int justUnderBottomY = entity.getY() - 1 + entity.getSolidBounds().y;

        int playerLeftCol = leftX / Block.SIZE;
        int playerRightCol = rightX / Block.SIZE;
        int playerBottomRow = (justUnderBottomY) / Block.SIZE;

        return blockMapManager.isBlockSolid(playerBottomRow, playerLeftCol) || blockMapManager.isBlockSolid(playerBottomRow, playerRightCol);
    }

    public int getAdjustedXSpeedToAvoidCollisions(Entity entity) {
        int projectedRightX = entity.getX() + entity.getXSpeed() + entity.getSolidBounds().x + entity.getSolidBounds().width;
        int projectedLeftX = entity.getX() + entity.getXSpeed() + entity.getSolidBounds().x;
        int topY = entity.getY() + entity.getSolidBounds().y + entity.getSolidBounds().height;
        int bottomY = entity.getY() + entity.getSolidBounds().y;

        int playerTopRow = topY / Block.SIZE;
        int playerBottomRow = bottomY / Block.SIZE;
        int playerRightCol = projectedRightX / Block.SIZE;
        int playerLeftCol = projectedLeftX / Block.SIZE;

        if (entity.getXSpeed() > 0) {
            if (blockMapManager.isBlockSolid(playerTopRow, playerRightCol) || blockMapManager.isBlockSolid(playerBottomRow, playerRightCol)) {
                int nearestBlockLeftEdge = (playerRightCol * Block.SIZE);
                int maxMoveRight = nearestBlockLeftEdge - (entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width) - 1;
                return Math.min(entity.getXSpeed(), maxMoveRight);
            }
        } else if (entity.getXSpeed() < 0) {
            if (blockMapManager.isBlockSolid(playerTopRow, playerLeftCol) || blockMapManager.isBlockSolid(playerBottomRow, playerLeftCol)) {
                int nearestBlockRightEdge = (playerLeftCol * Block.SIZE) + Block.SIZE;
                int maxMoveLeft = nearestBlockRightEdge - (entity.getX() + entity.getSolidBounds().x) + 1;
                return Math.max(entity.getXSpeed(), maxMoveLeft);
            }
        }

        return entity.getXSpeed();
    }

    public int getAdjustedYSpeedToAvoidCollision(Entity entity) {
        int rightX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width;
        int leftX = entity.getX() + entity.getSolidBounds().x;
        int projectedTopY = entity.getY() + entity.getYSpeed() + entity.getSolidBounds().y;
        int projectedBottomY = entity.getY() + entity.getYSpeed() + entity.getSolidBounds().y + entity.getSolidBounds().height;

        int playerTopRow = projectedTopY / Block.SIZE;
        int playerBottomRow = projectedBottomY / Block.SIZE;
        int playerRightCol = rightX / Block.SIZE;
        int playerLeftCol = leftX / Block.SIZE;

        if (entity.getYSpeed() < 0) {
            if (blockMapManager.isBlockSolid(playerTopRow, playerLeftCol) || blockMapManager.isBlockSolid(playerTopRow, playerRightCol)) {
                int nearestBlockBottomEdge = (playerTopRow * Block.SIZE) + Block.SIZE;
                int maxMoveUp = nearestBlockBottomEdge - (entity.getY() + entity.getSolidBounds().y);
                return Math.max(entity.getYSpeed(), maxMoveUp);
            }
        } else if (entity.getYSpeed() > 0) {
            if (blockMapManager.isBlockSolid(playerBottomRow, playerLeftCol) || blockMapManager.isBlockSolid(playerBottomRow, playerRightCol)) {
                int nearestBlockTopEdge = (playerBottomRow * Block.SIZE);
                int maxMoveDown = nearestBlockTopEdge - (entity.getY() + entity.getSolidBounds().y + entity.getSolidBounds().height) - 1;
                entity.setYSpeed(0);
                return Math.min(entity.getYSpeed(), maxMoveDown);
            }
        }
        return entity.getYSpeed();
    }
}
