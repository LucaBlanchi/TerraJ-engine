package com.neatwitstudios.terrajengine;

import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.MapManager;

public class CollisionsChecker {

    public static boolean isCollidingDown(Player player, MapManager mapManager) {
        int leftX = player.getX() + player.getSolidBounds().x;
        int rightX = player.getX() + player.getSolidBounds().x + player.getSolidBounds().width;
        int projectedBottomY = player.getY() + player.getYDiff() + player.getSolidBounds().y;

        int playerLeftCol = leftX / Block.SIZE;
        int playerRightCol = rightX / Block.SIZE;
        int playerBottomRow = (projectedBottomY) / Block.SIZE;

        return mapManager.isBlockSolid(playerBottomRow, playerLeftCol) || mapManager.isBlockSolid(playerBottomRow, playerRightCol);
    }

    public static int getAdjustedXDiffAvoidCollision(Player player, MapManager mapManager) {
        int projectedRightX = player.getX() + player.getXDiff() + player.getSolidBounds().x + player.getSolidBounds().width;
        int projectedLeftX = player.getX() + player.getXDiff() + player.getSolidBounds().x;
        int topY = player.getY() + player.getSolidBounds().y + player.getSolidBounds().height;
        int bottomY = player.getY() + player.getSolidBounds().y;

        int playerTopRow = topY / Block.SIZE;
        int playerBottomRow = bottomY / Block.SIZE;
        int playerRightCol = projectedRightX / Block.SIZE;
        int playerLeftCol = projectedLeftX / Block.SIZE;

        if (player.getXDiff() > 0
                && (mapManager.isBlockSolid(playerTopRow, playerRightCol) || mapManager.isBlockSolid(playerBottomRow, playerRightCol))) {
            return 0;
        } else if (player.getXDiff() < 0
                && (mapManager.isBlockSolid(playerTopRow, playerLeftCol) || mapManager.isBlockSolid(playerBottomRow, playerLeftCol))) {
            return 0;
        }
        return player.getXDiff();
    }

    public static int getAdjustedYDiffAvoidCollisionResettingOnHeadBump(Player player, MapManager mapManager) {
        int rightX = player.getX() + player.getSolidBounds().x + player.getSolidBounds().width;
        int leftX = player.getX() + player.getSolidBounds().x;
        int projectedTopY = player.getY() + player.getYDiff() + player.getSolidBounds().y + player.getSolidBounds().height;
        int projectedBottomY = player.getY() + player.getYDiff() + player.getSolidBounds().y;

        int playerTopRow = projectedTopY / Block.SIZE;
        int playerBottomRow = projectedBottomY / Block.SIZE;
        int playerRightCol = rightX / Block.SIZE;
        int playerLeftCol = leftX / Block.SIZE;

        if (player.getYDiff() < 0
                && (mapManager.isBlockSolid(playerBottomRow, playerLeftCol) || mapManager.isBlockSolid(playerBottomRow, playerRightCol))) {
            return 0;
        } else if (player.getYDiff() > 0
                && (mapManager.isBlockSolid(playerTopRow, playerLeftCol) || mapManager.isBlockSolid(playerTopRow, playerRightCol))) {
            player.setYDiff(0);
            return 0;
        }
        return player.getYDiff();
    }
}
