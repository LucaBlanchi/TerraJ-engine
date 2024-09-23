package com.neatwitstudios.terrajengine;

import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.map.MapManager;

public class CollisionsChecker {

    public static boolean isCollidingUp(Player player, MapManager mapManager) {
        int playerLeftWorldX = player.getX() + player.getSolidBounds().x;
        int playerRightWorldX = player.getX() + player.getSolidBounds().x + player.getSolidBounds().width;
        int playerUpWorldY = player.getY() + player.getSolidBounds().y;

        int playerLeftCol = playerLeftWorldX / GamePanel.TILE_SIZE;
        int playerRightCol = playerRightWorldX / GamePanel.TILE_SIZE;
        int playerTopRow = (playerUpWorldY - player.getSpeed()) / GamePanel.TILE_SIZE;

        return mapManager.isBlockSolid(playerTopRow, playerLeftCol) || mapManager.isBlockSolid(playerTopRow, playerRightCol);
    }

    public static boolean isCollidingDown(Player player, MapManager mapManager) {
        int playerLeftWorldX = player.getX() + player.getSolidBounds().x;
        int playerRightWorldX = player.getX() + player.getSolidBounds().x + player.getSolidBounds().width;
        int playerDownWorldY = player.getY() + player.getSolidBounds().y + player.getSolidBounds().height;

        int playerLeftCol = playerLeftWorldX / GamePanel.TILE_SIZE;
        int playerRightCol = playerRightWorldX / GamePanel.TILE_SIZE;
        int playerBottomRow = (playerDownWorldY + player.getSpeed()) / GamePanel.TILE_SIZE;

        return mapManager.isBlockSolid(playerBottomRow, playerLeftCol) || mapManager.isBlockSolid(playerBottomRow, playerRightCol);
    }

    public static boolean isCollidingLeft(Player player, MapManager mapManager) {
        int playerLeftWorldX = player.getX() + player.getSolidBounds().x;
        int playerUpWorldY = player.getY() + player.getSolidBounds().y;
        int playerDownWorldY = player.getY() + player.getSolidBounds().y + player.getSolidBounds().height;

        int playerTopRow = playerUpWorldY / GamePanel.TILE_SIZE;
        int playerBottomRow = playerDownWorldY / GamePanel.TILE_SIZE;
        int playerLeftCol = (playerLeftWorldX - player.getSpeed()) / GamePanel.TILE_SIZE;

        return mapManager.isBlockSolid(playerTopRow, playerLeftCol) || mapManager.isBlockSolid(playerBottomRow, playerLeftCol);
    }

    public static boolean isCollidingRight(Player player, MapManager mapManager) {
        int playerRightWorldX = player.getX() + player.getSolidBounds().x + player.getSolidBounds().width;
        int playerUpWorldY = player.getY() + player.getSolidBounds().y;
        int playerDownWorldY = player.getY() + player.getSolidBounds().y + player.getSolidBounds().height;

        int playerTopRow = playerUpWorldY / GamePanel.TILE_SIZE;
        int playerBottomRow = playerDownWorldY / GamePanel.TILE_SIZE;
        int playerRightCol = (playerRightWorldX + player.getSpeed()) / GamePanel.TILE_SIZE;

        return mapManager.isBlockSolid(playerTopRow, playerRightCol) || mapManager.isBlockSolid(playerBottomRow, playerRightCol);
    }
}
