package com.neatwitstudios.terrajengine.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SpritesManager {

    private static final Map<String, BufferedImage[]> sprites = init();
    private static final String PLAYER = "player";

    private SpritesManager() {
    }

    private static Map<String, BufferedImage[]> init() {
        Map<String, BufferedImage[]> map = Map.of(
                PLAYER, new BufferedImage[2]
        );
        try {
            map.get(PLAYER)[0] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerWalk1.png")));
            map.get(PLAYER)[1] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerWalk2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static BufferedImage[] getSprites(String name) {
        return sprites.get(name);
    }
}
