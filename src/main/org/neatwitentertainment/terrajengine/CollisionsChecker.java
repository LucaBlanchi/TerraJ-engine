package org.neatwitentertainment.terrajengine;

import org.neatwitentertainment.terrajengine.entity.Entity;
import org.neatwitentertainment.terrajengine.map.MapManager;

public class CollisionsChecker {

    public boolean isColliding(Entity entity, MapManager mapManager) {
        int entityLeftWorldX = entity.getX() + entity.getSolidBounds().x;
        int entityRightWorldX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width;
        int entityUpWorldY = entity.getY() + entity.getSolidBounds().y;
        int entityDownWorldY = entity.getY() + entity.getSolidBounds().y + entity.getSolidBounds().height;

        int entityLeftCol = entityLeftWorldX / GamePanel.TILE_SIZE;
        int entityRightCol = entityRightWorldX / GamePanel.TILE_SIZE;
        int entityTopRow = entityUpWorldY / GamePanel.TILE_SIZE;
        int entityBottomRow = entityDownWorldY / GamePanel.TILE_SIZE;

        switch (entity.getDirection()) {
            case UP:
                entityTopRow = (entityUpWorldY - entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (mapManager.isTileSolid(entityTopRow, entityLeftCol) || mapManager.isTileSolid(entityTopRow, entityRightCol)) {
                    return true;
                }
                break;
            case DOWN:
                entityBottomRow = (entityDownWorldY + entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (mapManager.isTileSolid(entityBottomRow, entityLeftCol) || mapManager.isTileSolid(entityBottomRow, entityRightCol)) {
                    return true;
                }
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (mapManager.isTileSolid(entityTopRow, entityLeftCol) || mapManager.isTileSolid(entityBottomRow, entityLeftCol)) {
                    return true;
                }
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (mapManager.isTileSolid(entityTopRow, entityRightCol) || mapManager.isTileSolid(entityBottomRow, entityRightCol)) {
                    return true;
                }
                break;
        }
        return false;
    }
}
