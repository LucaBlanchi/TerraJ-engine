package org.neatwitentertainment.terrajengine;

import org.neatwitentertainment.terrajengine.entity.Entity;

public class CollisionsChecker {

    private GamePanel gamePanel;

    public CollisionsChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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
            case "up":
                entityTopRow = (entityUpWorldY - entity.getSpeed()) / GamePanel.TILE_SIZE;
                tileNum1 = gamePanel.getTileManager().getMapTileGrid()[entityTopRow][entityLeftCol];
                tileNum2 = gamePanel.getTileManager().getMapTileGrid()[entityTopRow][entityRightCol];
                if (gamePanel.getTileManager().getTile(tileNum1).isSolid() || gamePanel.getTileManager().getTile(tileNum2).isSolid())
                    entity.setCollisionOn(true);
                break;
            case "down":
                entityBottomRow = (entityDownWorldY + entity.getSpeed()) / GamePanel.TILE_SIZE;
                tileNum1 = gamePanel.getTileManager().getMapTileGrid()[entityBottomRow][entityLeftCol];
                tileNum2 = gamePanel.getTileManager().getMapTileGrid()[entityBottomRow][entityRightCol];
                if (gamePanel.getTileManager().getTile(tileNum1).isSolid() || gamePanel.getTileManager().getTile(tileNum2).isSolid())
                    entity.setCollisionOn(true);
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / GamePanel.TILE_SIZE;
                tileNum1 = gamePanel.getTileManager().getMapTileGrid()[entityTopRow][entityLeftCol];
                tileNum2 = gamePanel.getTileManager().getMapTileGrid()[entityBottomRow][entityLeftCol];
                if (gamePanel.getTileManager().getTile(tileNum1).isSolid() || gamePanel.getTileManager().getTile(tileNum2).isSolid())
                    entity.setCollisionOn(true);
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / GamePanel.TILE_SIZE;
                tileNum1 = gamePanel.getTileManager().getMapTileGrid()[entityTopRow][entityRightCol];
                tileNum2 = gamePanel.getTileManager().getMapTileGrid()[entityBottomRow][entityRightCol];
                if (gamePanel.getTileManager().getTile(tileNum1).isSolid() || gamePanel.getTileManager().getTile(tileNum2).isSolid())
                    entity.setCollisionOn(true);
                break;
        }
    }
}
