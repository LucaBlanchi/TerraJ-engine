package com.neatwitstudios.terrajengine;

import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.BlockMapManager;

public class CollisionsChecker {

    public static boolean isCollidingDown(Player player, BlockMapManager blockMapManager) {
        int leftX = player.getX() + player.getSolidBounds().x;
        int rightX = player.getX() + player.getSolidBounds().x + player.getSolidBounds().width;
        int projectedBottomY = player.getY() + player.getYDiff() + player.getSolidBounds().y;

        int playerLeftCol = leftX / Block.SIZE;
        int playerRightCol = rightX / Block.SIZE;
        int playerBottomRow = (projectedBottomY) / Block.SIZE;

        return blockMapManager.isBlockSolid(playerBottomRow, playerLeftCol) || blockMapManager.isBlockSolid(playerBottomRow, playerRightCol);
    }

    public static int getAdjustedXDiffAvoidCollision(Player player, BlockMapManager blockMapManager) {
        int projectedRightX = player.getX() + player.getXDiff() + player.getSolidBounds().x + player.getSolidBounds().width;
        int projectedLeftX = player.getX() + player.getXDiff() + player.getSolidBounds().x;
        int topY = player.getY() + player.getSolidBounds().y + player.getSolidBounds().height;
        int bottomY = player.getY() + player.getSolidBounds().y;

        int playerTopRow = topY / Block.SIZE;
        int playerBottomRow = bottomY / Block.SIZE;
        int playerRightCol = projectedRightX / Block.SIZE;
        int playerLeftCol = projectedLeftX / Block.SIZE;

        if (player.getXDiff() > 0) {
            if (blockMapManager.isBlockSolid(playerTopRow, playerRightCol) || blockMapManager.isBlockSolid(playerBottomRow, playerRightCol)) {
                int nearestBlockLeftEdge = (playerRightCol * Block.SIZE);
                int maxMoveRight = nearestBlockLeftEdge - (player.getX() + player.getSolidBounds().x + player.getSolidBounds().width) - 1;
                return Math.min(player.getXDiff(), maxMoveRight);
            }
        } else if (player.getXDiff() < 0) {
            if (blockMapManager.isBlockSolid(playerTopRow, playerLeftCol) || blockMapManager.isBlockSolid(playerBottomRow, playerLeftCol)) {
                int nearestBlockRightEdge = (playerLeftCol * Block.SIZE) + Block.SIZE;
                int maxMoveLeft = nearestBlockRightEdge - (player.getX() + player.getSolidBounds().x) + 1;
                return Math.max(player.getXDiff(), maxMoveLeft);
            }
        }

        return player.getXDiff();
    }

    public static int getAdjustedYDiffAvoidCollisionResettingOnHeadBump(Player player, BlockMapManager blockMapManager) {
        int rightX = player.getX() + player.getSolidBounds().x + player.getSolidBounds().width;
        int leftX = player.getX() + player.getSolidBounds().x;
        int projectedTopY = player.getY() + player.getYDiff() + player.getSolidBounds().y;
        int projectedBottomY = player.getY() + player.getYDiff() + player.getSolidBounds().y + player.getSolidBounds().height;

        int playerTopRow = projectedTopY / Block.SIZE;
        int playerBottomRow = projectedBottomY / Block.SIZE;
        int playerRightCol = rightX / Block.SIZE;
        int playerLeftCol = leftX / Block.SIZE;

        if (player.getYDiff() < 0) {
            if (blockMapManager.isBlockSolid(playerTopRow, playerLeftCol) || blockMapManager.isBlockSolid(playerTopRow, playerRightCol)) {
                int nearestBlockBottomEdge = (playerTopRow * Block.SIZE) + Block.SIZE;
                int maxMoveUp = nearestBlockBottomEdge - (player.getY() + player.getSolidBounds().y);
                return Math.max(player.getYDiff(), maxMoveUp);
            }
        } else if (player.getYDiff() > 0) {
            if (blockMapManager.isBlockSolid(playerBottomRow, playerLeftCol) || blockMapManager.isBlockSolid(playerBottomRow, playerRightCol)) {
                int nearestBlockTopEdge = (playerBottomRow * Block.SIZE);
                int maxMoveDown = nearestBlockTopEdge - (player.getY() + player.getSolidBounds().y + player.getSolidBounds().height) - 1;
                player.setYDiff(0);
                return Math.min(player.getYDiff(), maxMoveDown);
            }
        }
        return player.getYDiff();
    }

}
