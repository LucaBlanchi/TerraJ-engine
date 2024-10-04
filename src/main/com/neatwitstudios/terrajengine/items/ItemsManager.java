package com.neatwitstudios.terrajengine.items;

import com.neatwitstudios.terrajengine.Camera;
import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.map.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    private final GamePanel gamePanel;

    private final List<Item> items = new ArrayList<>();

    public ItemsManager(GamePanel gamePanel, int level) {
        this.gamePanel = gamePanel;
        loadItems(level);
    }

    public void loadItems(int level) {
        items.clear();
        switch (level) {
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
        for (Item item : items) {
            item.checkCollision(gamePanel, player);
        }
        items.removeIf(Item::isPickedUp);
    }

    public void drawItems(Graphics2D g2d, Camera camera) {
        for (Item item : items) {
            item.draw(g2d, camera);
        }
    }
}
