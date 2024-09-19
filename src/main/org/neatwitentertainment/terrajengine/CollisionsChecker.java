package org.neatwitentertainment.terrajengine;

import org.neatwitentertainment.terrajengine.entity.Entity;
import org.neatwitentertainment.terrajengine.map.MapManager;

public class CollisionsChecker {

    public static boolean isCollidingUp(Entity entity, MapManager mapManager) {
        int entityLeftWorldX = entity.getX() + entity.getSolidBounds().x;
        int entityRightWorldX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width;
        int entityUpWorldY = entity.getY() + entity.getSolidBounds().y;

        int entityLeftCol = entityLeftWorldX / GamePanel.TILE_SIZE;
        int entityRightCol = entityRightWorldX / GamePanel.TILE_SIZE;
        int entityTopRow = (entityUpWorldY - entity.getSpeed()) / GamePanel.TILE_SIZE;

        return mapManager.isBlockSolid(entityTopRow, entityLeftCol) || mapManager.isBlockSolid(entityTopRow, entityRightCol);
    }

    public static boolean isCollidingDown(Entity entity, MapManager mapManager) {
        int entityLeftWorldX = entity.getX() + entity.getSolidBounds().x;
        int entityRightWorldX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width;
        int entityDownWorldY = entity.getY() + entity.getSolidBounds().y + entity.getSolidBounds().height;

        int entityLeftCol = entityLeftWorldX / GamePanel.TILE_SIZE;
        int entityRightCol = entityRightWorldX / GamePanel.TILE_SIZE;
        int entityBottomRow = (entityDownWorldY + entity.getSpeed()) / GamePanel.TILE_SIZE;

        return mapManager.isBlockSolid(entityBottomRow, entityLeftCol) || mapManager.isBlockSolid(entityBottomRow, entityRightCol);
    }

    public static boolean isCollidingLeft(Entity entity, MapManager mapManager) {
        int entityLeftWorldX = entity.getX() + entity.getSolidBounds().x;
        int entityUpWorldY = entity.getY() + entity.getSolidBounds().y;
        int entityDownWorldY = entity.getY() + entity.getSolidBounds().y + entity.getSolidBounds().height;

        int entityTopRow = entityUpWorldY / GamePanel.TILE_SIZE;
        int entityBottomRow = entityDownWorldY / GamePanel.TILE_SIZE;
        int entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / GamePanel.TILE_SIZE;

        return mapManager.isBlockSolid(entityTopRow, entityLeftCol) || mapManager.isBlockSolid(entityBottomRow, entityLeftCol);
    }

    public static boolean isCollidingRight(Entity entity, MapManager mapManager) {
        int entityRightWorldX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width;
        int entityUpWorldY = entity.getY() + entity.getSolidBounds().y;
        int entityDownWorldY = entity.getY() + entity.getSolidBounds().y + entity.getSolidBounds().height;

        int entityTopRow = entityUpWorldY / GamePanel.TILE_SIZE;
        int entityBottomRow = entityDownWorldY / GamePanel.TILE_SIZE;
        int entityRightCol = (entityRightWorldX + entity.getSpeed()) / GamePanel.TILE_SIZE;

        return mapManager.isBlockSolid(entityTopRow, entityRightCol) || mapManager.isBlockSolid(entityBottomRow, entityRightCol);
    }
}
