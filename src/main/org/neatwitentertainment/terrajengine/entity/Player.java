package org.neatwitentertainment.terrajengine.entity;

import org.neatwitentertainment.terrajengine.CollisionsChecker;
import org.neatwitentertainment.terrajengine.GamePanel;
import org.neatwitentertainment.terrajengine.KeyHandler;
import org.neatwitentertainment.terrajengine.map.MapManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    private static final int GRAVITY = 7 * GamePanel.TILE_SIZE / 120;

    private BufferedImage sprite1;
    private BufferedImage sprite2;

    private final KeyHandler keyHandler;

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        setDefaultValues();
        setSolidBounds();
        loadSprites();
    }

    public void setDefaultValues() {
        x = GamePanel.TILE_SIZE * 16;
        y = GamePanel.TILE_SIZE * 9;
        speed = 10 * GamePanel.TILE_SIZE / 120;
    }

    public void setSolidBounds() {
        solidBounds = new Rectangle(
                GamePanel.TILE_SIZE / 6,
                GamePanel.TILE_SIZE / 3,
                GamePanel.TILE_SIZE * 2/3,
                GamePanel.TILE_SIZE * 2/3
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
        isGoingUp = keyHandler.isUpPressed();
        isGoingDown = true;
        isGoingLeft = keyHandler.isLeftPressed();
        isGoingRight = keyHandler.isRightPressed();

        if (isGoingUp && !CollisionsChecker.isCollidingUp(this, mapManager)) {
            y -= speed;
        }
        if (isGoingDown && !CollisionsChecker.isCollidingDown(this, mapManager)) {
            y += GRAVITY;
        }
        if (isGoingLeft && !CollisionsChecker.isCollidingLeft(this, mapManager)) {
            x -= speed;
        }
        if (isGoingRight && !CollisionsChecker.isCollidingRight(this, mapManager)) {
            x += speed;
        }

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
}
