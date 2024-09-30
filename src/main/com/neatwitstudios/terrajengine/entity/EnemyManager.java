package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.Camera;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.BlockMapManager;

import java.awt.*;

public class EnemyManager {

    private final Enemy testEnemy;

    public EnemyManager(Player player) {
        testEnemy = new Enemy(Block.SIZE * 18, Block.SIZE * 3 + 1, player);
    }

    public void updateEnemies(BlockMapManager blockMapManager) {
        testEnemy.update(blockMapManager);
    }

    public void drawEnemies(Graphics2D g2d, Camera camera) {
        testEnemy.draw(g2d, camera);
    }
}
