package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.Camera;
import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.BlockMapManager;

import java.awt.*;

public class Enemy extends Entity {

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.solidBounds = new Rectangle(
                0,
                0,
                0,
                0
        );
    }

    @Override
    public void update(BlockMapManager blockMapManager) {

    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        int enemyScreenX = GamePanel.SCREEN_CENTER_X - (camera.getCenterX() - x) * GamePanel.TILE_SIZE / Block.SIZE;
        int enemyScreenY = GamePanel.SCREEN_CENTER_Y + (camera.getCenterY() - y) * GamePanel.TILE_SIZE / Block.SIZE;

        g2d.drawImage(
                SpritesManager.getSprites("enemy")[0],
                enemyScreenX,
                enemyScreenY,
                GamePanel.TILE_SIZE,
                GamePanel.TILE_SIZE,
                null
        );
    }
}
