package com.neatwitstudios.terrajengine.items;

import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.entity.SpritesManager;
import com.neatwitstudios.terrajengine.map.Block;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class NextLevelFlag extends Item {

    public NextLevelFlag(Rectangle bounds) {
        this.bounds = bounds;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/flag.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEffectOnCollision(Player player) {
        if (!bounds.contains(player.getX(), player.getY())) {
            return null;
        }
        pickedUp = true;
        return "nextLevel";
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        int itemScreenX = GamePanel.SCREEN_CENTER_X - (camera.getCenterX() - bounds.x) * GamePanel.TILE_SIZE / Block.SIZE;
        int itemScreenY = GamePanel.SCREEN_CENTER_Y + (camera.getCenterY() - bounds.y) * GamePanel.TILE_SIZE / Block.SIZE;

        g2d.drawImage(
                sprite,
                itemScreenX,
                itemScreenY,
                GamePanel.TILE_SIZE,
                GamePanel.TILE_SIZE,
                null
        );
    }

    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        int itemScreenX = GamePanel.SCREEN_CENTER_X - (camera.getCenterX() - bounds.x) * GamePanel.TILE_SIZE / Block.SIZE;
        int itemScreenY = GamePanel.SCREEN_CENTER_Y + (camera.getCenterY() - bounds.y) * GamePanel.TILE_SIZE / Block.SIZE;

        g2d.setColor(Color.RED);
        g2d.drawRect(
                itemScreenX,
                itemScreenY,
                bounds.width * GamePanel.TILE_SIZE / Block.SIZE,
                bounds.height * GamePanel.TILE_SIZE / Block.SIZE
        );
    }
}
