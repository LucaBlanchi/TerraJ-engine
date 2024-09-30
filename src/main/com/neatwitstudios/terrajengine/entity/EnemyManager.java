package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.Camera;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.BlockMapManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyManager {

    private final List<Entity> enemies = new ArrayList<>();

    private final Player player;
    private final AttackManager attackManager;

    public EnemyManager(Player player, AttackManager attackManager) {
        this.player = player;
        this.attackManager = attackManager;

        loadEnemies(0);
    }

    public void loadEnemies(int level) {
        enemies.clear();
        switch (level) {
            case 0:
                enemies.add(new Enemy(Block.SIZE * 18, Block.SIZE * 3 + 1, player, attackManager));
                enemies.add(new Enemy(Block.SIZE * 8, Block.SIZE + 1, player, attackManager));
                break;
            case 1:
                enemies.add(new Enemy(Block.SIZE * 2, Block.SIZE + 1, player, attackManager));
                enemies.add(new Enemy(Block.SIZE * 23, Block.SIZE * 7 + 1, player, attackManager));
                break;
            default:
                break;
        }
    }

    public void updateEnemies(BlockMapManager blockMapManager) {
        for (Entity enemy : enemies) {
            enemy.update(blockMapManager);
        }
        attackManager.updateAttacks();
        attackManager.damageEntities(enemies);
        enemies.removeIf(enemy -> enemy.getHealth() <= 0);
    }

    public void drawEnemies(Graphics2D g2d, Camera camera) {
        for (Entity enemy : enemies) {
            enemy.draw(g2d, camera);
        }
    }
}
