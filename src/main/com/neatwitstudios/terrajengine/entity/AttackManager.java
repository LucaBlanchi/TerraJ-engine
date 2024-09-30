package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AttackManager {

    private final List<Attack> attacks = new ArrayList<>();

    public void updateAttacks() {
        for (Attack attack : attacks) {
            attack.update();
        }
        attacks.removeIf(attack -> attack.getDuration() <= 0);
    }

    public void damageEntities(List<Entity> entities) {
        for (Attack attack : attacks) {
            for (Entity entity : entities) {
                Rectangle solidBounds = entity.getSolidBounds();
                Rectangle entityHitBox = new Rectangle(
                        solidBounds.x + entity.getX(),
                        solidBounds.y + entity.getY(),
                        solidBounds.width,
                        solidBounds.height
                );
                if (attack.getOwner() != entity
                        && attack.getHitBox().intersects(entityHitBox)
                        && !attack.hasDamagedEntity(entity)) {
                    entity.takeDamage(attack.getDamage());
                    attack.addDamagedEntity(entity);
                }
            }
        }
    }

    public void submitAttack(Attack attack) {
        attacks.add(attack);
    }
}
