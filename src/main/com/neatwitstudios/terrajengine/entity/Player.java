package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.map.MapManager;
import com.neatwitstudios.terrajengine.CollisionsChecker;
import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    private int x;
    private int y;
    private int speed;
    private Rectangle solidBounds;

    private static final int GRAVITY = 10 * GamePanel.TILE_SIZE / 120;

    private int spriteCounter = 0;
    private int spriteNum = 1;
    private BufferedImage sprite1;
    private BufferedImage sprite2;

    private int yDiff = 0;
    private int xDiff = 0;

    private final KeyHandler keyHandler;

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        setDefaultValues();
        setSolidBounds();
        loadSprites();
    }

    public void setDefaultValues() {
        x = 0;
        y = GamePanel.TILE_SIZE * 16 - 1;
        speed = 10 * GamePanel.TILE_SIZE / 120;
    }

    public void setSolidBounds() {
        solidBounds = new Rectangle(
                GamePanel.TILE_SIZE / 6,
                GamePanel.TILE_SIZE / 10,
                GamePanel.TILE_SIZE * 2/3,
                GamePanel.TILE_SIZE * 9 / 10
        );
    }

    public void loadSprites() {
        try {
            sprite1 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite1.png"));
            sprite2 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(MapManager mapManager) {
        if (CollisionsChecker.isCollidingUp(this, mapManager)) {
            yDiff = 0;
        }
        int yDiff = this.yDiff - GRAVITY;
        if (yDiff < 0 && !CollisionsChecker.isCollidingDown(this, mapManager)) {
            y -= yDiff;
        }
        if (yDiff > 0 && !CollisionsChecker.isCollidingUp(this, mapManager)) {
            y -= yDiff;
        }
        if (this.yDiff > 0) {
            this.yDiff -= 1;
        } else {
            this.yDiff = 0;
        }

        if (keyHandler.isUpPressed() && !CollisionsChecker.isCollidingUp(this, mapManager)) {
            jump(mapManager);
        }
        if (keyHandler.isLeftPressed() && !CollisionsChecker.isCollidingLeft(this, mapManager)) {
            x -= speed;
        }
        if (keyHandler.isRightPressed() && !CollisionsChecker.isCollidingRight(this, mapManager)) {
            x += speed;
        }

        updateSpriteNum();
    }

    private void jump(MapManager mapManager) {
        if (CollisionsChecker.isCollidingDown(this, mapManager)) {
            yDiff = 35 * GamePanel.TILE_SIZE / 120;
        }
    }

    private void updateSpriteNum() {
        if (!keyHandler.isUpPressed()
                && !keyHandler.isLeftPressed()
                && !keyHandler.isRightPressed()) {
            spriteCounter = 13;
            return;
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image = selectSprite();
        g2d.drawImage(image, GamePanel.SCREEN_CENTER_X, GamePanel.SCREEN_CENTER_Y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }

    private BufferedImage selectSprite() {
        return spriteNum == 1 ? sprite1 : sprite2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle getSolidBounds() {
        return solidBounds;
    }
}
