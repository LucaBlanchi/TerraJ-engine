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
            up1 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/resources/static/sprites/sprite2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(MapManager mapManager, CollisionsChecker collisionsChecker) {

        if (!keyHandler.isUpPressed()
                && !keyHandler.isDownPressed()
                && !keyHandler.isLeftPressed()
                && !keyHandler.isRightPressed()) {
            spriteCounter = 13;
            return;
        }

        isGoingUp = keyHandler.isUpPressed();
        isGoingDown = keyHandler.isDownPressed();
        isGoingLeft = keyHandler.isLeftPressed();
        isGoingRight = keyHandler.isRightPressed();

        if (!collisionsChecker.isColliding(this, mapManager)) {
            if (isGoingUp) {
                y -= speed;
            }
            if (isGoingDown) {
                y += speed;
            }
            if (isGoingLeft) {
                x -= speed;
            }
            if (isGoingRight) {
                x += speed;
            }
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
        if (isGoingUp) {
            if (spriteNum == 1) {
                return up1;
            }
            return up2;
        }
        if (isGoingDown) {
            if (spriteNum == 1) {
                return down1;
            }
            return down2;
        }
        if (isGoingLeft) {
            if (spriteNum == 1) {
                return left1;
            }
            return left2;
        }
        if (isGoingRight) {
            if (spriteNum == 1) {
                return right1;
            }
            return right2;
        }
        return down1;
    }
}
