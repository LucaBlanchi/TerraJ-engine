package org.neatwitentertainment.terrajengine;

import org.neatwitentertainment.terrajengine.entity.Entity;
import org.neatwitentertainment.terrajengine.tiles.TileManager;

public class CollisionsChecker {

    private TileManager tileManager;

    public CollisionsChecker(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getX() + entity.getSolidBounds().x;
        int entityRightWorldX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width;
        int entityUpWorldY = entity.getY() + entity.getSolidBounds().y;
        int entityDownWorldY = entity.getY() + entity.getSolidBounds().y + entity.getSolidBounds().height;

        int entityLeftCol = entityLeftWorldX / GamePanel.TILE_SIZE;
        int entityRightCol = entityRightWorldX / GamePanel.TILE_SIZE;
        int entityTopRow = entityUpWorldY / GamePanel.TILE_SIZE;
        int entityBottomRow = entityDownWorldY / GamePanel.TILE_SIZE;

        int tileNum1;
        int tileNum2;

        switch (entity.getDirection()) {
            case UP:
                entityTopRow = (entityUpWorldY - entity.getSpeed()) / GamePanel.TILE_SIZE;
                tileNum1 = tileManager.getMapTileGrid()[entityTopRow][entityLeftCol];
                tileNum2 = tileManager.getMapTileGrid()[entityTopRow][entityRightCol];
                if (tileManager.getTile(tileNum1).isSolid() || tileManager.getTile(tileNum2).isSolid())
                    entity.setColliding(true);
                break;
            case DOWN:
                entityBottomRow = (entityDownWorldY + entity.getSpeed()) / GamePanel.TILE_SIZE;
                tileNum1 = tileManager.getMapTileGrid()[entityBottomRow][entityLeftCol];
                tileNum2 = tileManager.getMapTileGrid()[entityBottomRow][entityRightCol];
                if (tileManager.getTile(tileNum1).isSolid() || tileManager.getTile(tileNum2).isSolid())
                    entity.setColliding(true);
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / GamePanel.TILE_SIZE;
                tileNum1 = tileManager.getMapTileGrid()[entityTopRow][entityLeftCol];
                tileNum2 = tileManager.getMapTileGrid()[entityBottomRow][entityLeftCol];
                if (tileManager.getTile(tileNum1).isSolid() || tileManager.getTile(tileNum2).isSolid())
                    entity.setColliding(true);
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / GamePanel.TILE_SIZE;
                tileNum1 = tileManager.getMapTileGrid()[entityTopRow][entityRightCol];
                tileNum2 = tileManager.getMapTileGrid()[entityBottomRow][entityRightCol];
                if (tileManager.getTile(tileNum1).isSolid() || tileManager.getTile(tileNum2).isSolid())
                    entity.setColliding(true);
                break;
        }
    }
}
