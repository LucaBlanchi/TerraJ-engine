package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.camera.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Attack {

    private final int xDisplacement;
    private final int yDisplacement;
    private final Rectangle hitBox;
    private final int damage;
    private int duration;
    private final Entity owner;
    private final List<Entity> damagedEntities = new ArrayList<>();

    public Attack(int xDisplacement, int yDisplacement, int width, int height, int damage, int duration, Entity owner) {
        this.xDisplacement = xDisplacement;
        this.yDisplacement = yDisplacement;
        this.hitBox = new Rectangle(
                owner.getX() + (owner.isFacingRight ? xDisplacement : -xDisplacement),
                owner.getY() + yDisplacement,
                width,
                height
        );
        this.damage = damage;
        this.duration = duration;
        this.owner = owner;
    }

    public void update() {
        hitBox.x = owner.getX() + (owner.isFacingRight ? xDisplacement : -xDisplacement);
        hitBox.y = owner.getY() + yDisplacement;
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
        g2d.setColor(Color.RED);
        g2d.drawRect(
                CoordConverter.getScreenX(hitBox.x - hitBox.width / 2, camera),
                CoordConverter.getScreenY(hitBox.y + hitBox.height, camera),
                CoordConverter.getResizedLength(hitBox.width, camera),
                CoordConverter.getResizedLength(hitBox.height, camera)
        );
    }
}
