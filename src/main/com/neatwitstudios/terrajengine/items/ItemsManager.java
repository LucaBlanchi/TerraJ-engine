package com.neatwitstudios.terrajengine.items;

import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.map.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    private final GamePanel gamePanel;

    private final List<Item> items = new ArrayList<>();

    public ItemsManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadItems(0);
    }

    public void loadItems(int mapId) {
        items.clear();
        switch (mapId) {
            case 0 -> items.add(new NextLevelFlag(new Rectangle(
                        Block.SIZE * 22,
                        Block.SIZE * 14,
                        Block.SIZE,
                        Block.SIZE))
            );
            case 1 -> {

            }
            default -> {
            }
        }
    }

    public void checkCollisions(Player player) {
        List<String> effects = new ArrayList<>();
        for (Item item : items) {
            String effect = item.getEffectOnCollision(player);
            if (effect != null) {
                effects.add(effect);
            }
        }
        items.removeIf(Item::isPickedUp);
        for (String effect : effects) {
            switch (effect) {
                case "nextLevel" -> gamePanel.loadNextLevel();
                default -> {
                }
            }
        }
    }

    public void drawItems(Graphics2D g2d, Camera camera) {
        for (Item item : items) {
            item.draw(g2d, camera);
        }
    }

    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        for (Item item : items) {
            item.drawDebugFeatures(g2d, camera);
        }
    }
}
