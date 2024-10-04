package com.neatwitstudios.terrajengine.map;

import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BlockMapManager {

    private final Block[] blocks;
    private int[][] mapGrid;
    private int maxWorldCol;
    private int maxWorldRow;

    public BlockMapManager() {
        blocks = new Block[16];
        loadBlocks();
        loadMap(0);
    }

    public void loadMap(int mapId) {
        String mapPath = switch (mapId) {
            case 0 -> "/resources/static/maps/map.txt";
            case 1 -> "/resources/static/maps/map1.txt";
            default -> "/resources/static/maps/map.txt";
        };

        InputStream inputStream = getClass().getResourceAsStream(mapPath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            int rowCount = 0;
            int colCount = 0;

            while ((line = bufferedReader.readLine()) != null) {
                rowCount++;
                String[] tokens = line.split(" ");
                colCount = Math.max(colCount, tokens.length);
            }

            maxWorldRow = rowCount;
            maxWorldCol = colCount;

            mapGrid = new int[maxWorldRow][maxWorldCol];

            bufferedReader.close();
            inputStream = getClass().getResourceAsStream(mapPath);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            for (int i = 0; i < maxWorldRow; i++) {
                line = bufferedReader.readLine();
                String[] tokens = line.split(" ");
                for (int j = 0; j < tokens.length; j++) {
                    mapGrid[maxWorldRow - 1 - i][j] = Integer.parseInt(tokens[j]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBlocks() {
        try {
            blocks[0] = new Block();
            blocks[0].setImage(ImageIO.read(getClass().getResourceAsStream("/static/blocks/air.png")));

            blocks[1] = new Block();
            blocks[1].setImage(ImageIO.read(getClass().getResourceAsStream("/static/blocks/dirt.png")));
            blocks[1].setSolid(true);

            blocks[2] = new Block();
            blocks[2].setImage(ImageIO.read(getClass().getResourceAsStream("/static/blocks/grass.png")));
            blocks[2].setSolid(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D, Camera camera) {
        int cameraXPosition = camera.getCenterX();
        int cameraYPosition = camera.getCenterY();

        int startCol = Math.max((cameraXPosition - camera.getWidth() / 2) / Block.SIZE - 1, 0);
        int endCol = Math.min((cameraXPosition + camera.getWidth() / 2) / Block.SIZE + 1, maxWorldCol - 1);

        int startRow = Math.max((cameraYPosition - camera.getHeight() / 2) / Block.SIZE - 1, 0);
        int endRow = Math.min((cameraYPosition + camera.getHeight() / 2) / Block.SIZE + 1, maxWorldRow - 1);

        for (int currentRow = startRow; currentRow <= endRow; currentRow++) {
            for (int currentCol = startCol; currentCol <= endCol; currentCol++) {

                int tileNum = mapGrid[currentRow][currentCol];
                if (tileNum == 0) {
                    continue;
                }

                int blockXPosition = currentCol * Block.SIZE;
                int blockYPosition = currentRow * Block.SIZE;

                int screenX = GamePanel.SCREEN_CENTER_X + (blockXPosition - cameraXPosition) * GamePanel.TILE_SIZE / Block.SIZE;
                int screenY = GamePanel.SCREEN_CENTER_Y - (blockYPosition - cameraYPosition) * GamePanel.TILE_SIZE / Block.SIZE;

                graphics2D.drawImage(blocks[tileNum].getImage(), screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            }
        }
    }

    public boolean isBlockSolid(int row, int col) {
        if (row < 0 || row >= maxWorldRow || col < 0 || col >= maxWorldCol) {
            return true;
        }
        return blocks[mapGrid[row][col]].isSolid();
    }
}
