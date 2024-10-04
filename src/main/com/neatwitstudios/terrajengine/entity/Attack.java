package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.map.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Attack {

    private final Rectangle hitBox;
    private final int damage;
    private int duration;
    private final Entity owner;
    private final List<Entity> damagedEntities = new ArrayList<>();

    public Attack(Rectangle hitBox, int damage, int duration, Entity owner) {
        this.hitBox = hitBox;
        this.damage = damage;
        this.duration = duration;
        this.owner = owner;
    }

    public void update() {
        duration--;
    }

    public boolean hasDamagedEntity(Entity entity) {
        return damagedEntities.contains(entity);
    }

    public void addDamagedEntity(Entity entity) {
        damagedEntities.add(entity);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getDamage() {
        return damage;
    }

    public int getDuration() {
        return duration;
    }

    public Entity getOwner() {
        return owner;
    }

    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        int screenX = GamePanel.SCREEN_CENTER_X - (camera.getCenterX() - hitBox.x) * GamePanel.TILE_SIZE / Block.SIZE;
        int screenY = GamePanel.SCREEN_CENTER_Y + (camera.getCenterY() - hitBox.y) * GamePanel.TILE_SIZE / Block.SIZE;

        g2d.setColor(Color.RED);
        g2d.drawRect(
                screenX,
                screenY,
                hitBox.width * GamePanel.TILE_SIZE / Block.SIZE,
                hitBox.height * GamePanel.TILE_SIZE / Block.SIZE
        );
    }
}
