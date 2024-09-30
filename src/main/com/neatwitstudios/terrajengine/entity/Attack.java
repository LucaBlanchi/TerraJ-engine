package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.Camera;
import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.map.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Attack {

    private Rectangle hitBox;
    private int damage;
    private int duration;
    private Entity owner;
    private List<Entity> damagedEntities = new ArrayList<>();

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
}
