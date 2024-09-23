package com.neatwitstudios.terrajengine.map;

import com.neatwitstudios.terrajengine.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapManager {

    private final Block[] blocks;
    private final int[][] mapGrid;

    private static final int MAX_WORLD_COL = 32;
    private static final int MAX_WORLD_ROW = 18;

    public MapManager() {
        blocks = new Block[16];
        loadBlocks();

        mapGrid = new int[MAX_WORLD_ROW][MAX_WORLD_COL];
        loadMap();
    }

    private void loadMap() {
        InputStream inputStream = getClass().getResourceAsStream("/resources/static/maps/map.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            for (int i = 0; i < MAX_WORLD_ROW; i++) {
                String line = bufferedReader.readLine();
                String[] tokens = line.split(" ");
                for (int j = 0; j < MAX_WORLD_COL; j++) {
                    mapGrid[MAX_WORLD_ROW - 1 - i][j] = Integer.parseInt(tokens[j]);
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

    public void draw(Graphics2D graphics2D, int cameraXPosition, int cameraYPosition) {
        int startCol = Math.max((cameraXPosition - Block.SIZE * 10) / Block.SIZE, 0);
        int endCol = Math.min((cameraXPosition + Block.SIZE * 10) / Block.SIZE, MAX_WORLD_COL - 1);

        int startRow = Math.max((cameraYPosition - Block.SIZE * 6) / Block.SIZE, 0);
        int endRow = Math.min((cameraYPosition + Block.SIZE * 6) / Block.SIZE, MAX_WORLD_ROW - 1);

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
        if (row < 0 || row >= MAX_WORLD_ROW || col < 0 || col >= MAX_WORLD_COL) {
            return true;
        }
        return blocks[mapGrid[row][col]].isSolid();
    }
}
