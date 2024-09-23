package com.neatwitstudios.terrajengine;

import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.MapManager;

public class CollisionsChecker {

    public static boolean isCollidingDown(Player player, MapManager mapManager) {
        int playerLeftWorldX = player.getX() + player.getSolidBounds().x;
        int playerRightWorldX = player.getX() + player.getSolidBounds().x + player.getSolidBounds().width;
        int playerDownWorldY = player.getY() + player.getYDiff() + player.getSolidBounds().y + player.getSolidBounds().height;

        int playerLeftCol = playerLeftWorldX / Block.SIZE;
        int playerRightCol = playerRightWorldX / Block.SIZE;
        int playerBottomRow = (playerDownWorldY + player.getSpeed()) / Block.SIZE;

        return mapManager.isBlockSolid(playerBottomRow, playerLeftCol) || mapManager.isBlockSolid(playerBottomRow, playerRightCol);
    }

    public static int getAdjustedXDiffAvoidCollision(Player player, MapManager mapManager) {
        int projectedRightX = player.getX() + player.getXDiff() + player.getSolidBounds().x + player.getSolidBounds().width;
        int projectedLeftX = player.getX() + player.getXDiff() + player.getSolidBounds().x;
        int projectedTopY = player.getY() + player.getSolidBounds().y;
        int projectedBottomY = player.getY() + player.getSolidBounds().y + player.getSolidBounds().height;

        int playerTopRow = projectedTopY / Block.SIZE;
        int playerBottomRow = projectedBottomY / Block.SIZE;
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

    public static int getAdjustedYDiffAvoidCollision(Player player, MapManager mapManager) {
        int projectedRightX = player.getX() + player.getSolidBounds().x + player.getSolidBounds().width;
        int projectedLeftX = player.getX() + player.getSolidBounds().x;
        int projectedTopY = player.getY() + player.getYDiff() + player.getSolidBounds().y;
        int projectedBottomY = player.getY() + player.getYDiff() + player.getSolidBounds().y + player.getSolidBounds().height;

        int playerTopRow = projectedTopY / Block.SIZE;
        int playerBottomRow = projectedBottomY / Block.SIZE;
        int playerRightCol = projectedRightX / Block.SIZE;
        int playerLeftCol = projectedLeftX / Block.SIZE;

        if (player.getYDiff() > 0
                && (mapManager.isBlockSolid(playerBottomRow, playerLeftCol) || mapManager.isBlockSolid(playerBottomRow, playerRightCol))) {
            return 0;
        } else if (player.getYDiff() < 0
                && (mapManager.isBlockSolid(playerTopRow, playerLeftCol) || mapManager.isBlockSolid(playerTopRow, playerRightCol))) {
            return 0;
        }
        return player.getYDiff();
    }
}
