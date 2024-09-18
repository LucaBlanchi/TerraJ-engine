package org.neatwitentertainment.terrajengine.entity;

import org.neatwitentertainment.terrajengine.GamePanel;
import org.neatwitentertainment.terrajengine.KeyHandler;
import org.neatwitentertainment.terrajengine.tiles.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    private final KeyHandler keyHandler;
    private final GamePanel gamePanel;

    public Player(KeyHandler keyHandler, GamePanel gamePanel) {
        this.keyHandler = keyHandler;
        setDefaultValues();
        loadSprites();
        solidBounds = new Rectangle(8, 16, 32, 32);
        this.gamePanel = gamePanel;
    }

    public void setDefaultValues() {
        x = GamePanel.TILE_SIZE * 16;
        y = GamePanel.TILE_SIZE * 9;
        speed = 4;
        direction = Direction.DOWN;
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

    public void update(TileManager tileManager) {

        if (!keyHandler.isUpPressed()
                && !keyHandler.isDownPressed()
                && !keyHandler.isLeftPressed()
                && !keyHandler.isRightPressed()) {
            spriteCounter = 13;
            return;
        }

        if (keyHandler.isUpPressed()) {
            direction = Direction.UP;
        }
        if (keyHandler.isDownPressed()) {
            direction = Direction.DOWN;
        }
        if (keyHandler.isLeftPressed()) {
            direction = Direction.LEFT;
        }
        if (keyHandler.isRightPressed()) {
            direction = Direction.RIGHT;
        }

        isColliding = false;
        gamePanel.getCollisionsChecker().checkTile(this, tileManager);

        if (!isColliding) {
            switch (direction) {
                case UP:
                    y -= speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
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
        switch (direction) {
            case UP:
                if (spriteNum == 1) {
                    return up1;
                }
                return up2;
            case DOWN:
                if (spriteNum == 1) {
                    return down1;
                }
                return down2;
            case LEFT:
                if (spriteNum == 1) {
                    return left1;
                }
                return left2;
            case RIGHT:
                if (spriteNum == 1) {
                    return right1;
                }
                return right2;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }
}
