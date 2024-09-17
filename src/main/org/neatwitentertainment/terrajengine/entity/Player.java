package org.neatwitentertainment.terrajengine.entity;

import org.neatwitentertainment.terrajengine.GamePanel;
import org.neatwitentertainment.terrajengine.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    private KeyHandler keyHandler;
    private GamePanel gamePanel;

    public final int SCREEN_X = GamePanel.WIDTH / 2 - GamePanel.TILE_SIZE / 2;
    public final int SCREEN_Y = GamePanel.HEIGHT / 2 - GamePanel.TILE_SIZE / 2;

    public Player(KeyHandler keyHandler, GamePanel gamePanel) {
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
        solidBounds = new Rectangle(8, 16, 32, 32);
        this.gamePanel = gamePanel;
    }

    public void setDefaultValues() {
        worldX = GamePanel.TILE_SIZE * 16;
        worldY = GamePanel.TILE_SIZE * 9;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
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

    public void update() {

        if (!keyHandler.isUpPressed()
                && !keyHandler.isDownPressed()
                && !keyHandler.isLeftPressed()
                && !keyHandler.isRightPressed()) {
            spriteCounter = 13;
            return;
        }

        if (keyHandler.isUpPressed()) {
            direction = "up";
        }
        if (keyHandler.isDownPressed()) {
            direction = "down";
        }
        if (keyHandler.isLeftPressed()) {
            direction = "left";
        }
        if (keyHandler.isRightPressed()) {
            direction = "right";
        }

        isCollisionOn = false;
        gamePanel.getCollisionsChecker().checkTile(this);

        if (!isCollisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
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
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else {
                    image = right2;
                }
                break;
        }
        g2d.drawImage(image, SCREEN_X, SCREEN_Y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }
}
