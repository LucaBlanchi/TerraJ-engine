package org.neatwitentertainment.terrajengine;

import org.neatwitentertainment.terrajengine.entity.Entity;
import org.neatwitentertainment.terrajengine.tiles.TileManager;

public class CollisionsChecker {

    public void checkTile(Entity entity, TileManager tileManager) {
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
                if (tileManager.isTileSolid(entityTopRow, entityLeftCol) || tileManager.isTileSolid(entityTopRow, entityRightCol)) {
                    entity.setColliding(true);
                }
                break;
            case DOWN:
                entityBottomRow = (entityDownWorldY + entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (tileManager.isTileSolid(entityBottomRow, entityLeftCol) || tileManager.isTileSolid(entityBottomRow, entityRightCol)) {
                    entity.setColliding(true);
                }
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (tileManager.isTileSolid(entityTopRow, entityLeftCol) || tileManager.isTileSolid(entityBottomRow, entityLeftCol)) {
                    entity.setColliding(true);
                }
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / GamePanel.TILE_SIZE;
                if (tileManager.isTileSolid(entityTopRow, entityRightCol) || tileManager.isTileSolid(entityBottomRow, entityRightCol)) {
                    entity.setColliding(true);
                }
                break;
        }
    }
}
