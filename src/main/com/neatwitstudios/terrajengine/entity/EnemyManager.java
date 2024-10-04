package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.Camera;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.CollisionsChecker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyManager {

    private final List<Entity> enemies = new ArrayList<>();

    private final Player player;
    private final AttackManager attackManager;
    private final CollisionsChecker collisionsChecker;

    public EnemyManager(Player player, AttackManager attackManager, CollisionsChecker collisionsChecker) {
        this.player = player;
        this.attackManager = attackManager;
        this.collisionsChecker = collisionsChecker;

        loadEnemies(0);
    }

    public void loadEnemies(int mapId) {
        enemies.clear();
        switch (mapId) {
            case 0 -> {
                enemies.add(new Enemy(Block.SIZE * 18, Block.SIZE * 3 + 1, player, attackManager, collisionsChecker));
                enemies.add(new Enemy(Block.SIZE * 8, Block.SIZE + 1, player, attackManager, collisionsChecker));
            }
            case 1 -> {
                enemies.add(new Enemy(Block.SIZE * 2, Block.SIZE + 1, player, attackManager, collisionsChecker));
                enemies.add(new Enemy(Block.SIZE * 23, Block.SIZE * 7 + 1, player, attackManager, collisionsChecker));
            }
            default -> {
            }
        }
    }

    public void updateEnemies() {
        for (Entity enemy : enemies) {
            enemy.update();
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
