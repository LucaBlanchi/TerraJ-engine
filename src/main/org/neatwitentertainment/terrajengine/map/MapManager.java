package org.neatwitentertainment.terrajengine.map;

import org.neatwitentertainment.terrajengine.GamePanel;
import org.neatwitentertainment.terrajengine.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapManager {

    private final Tile[] tiles;
    private final int[][] mapGrid;

    private static final int MAX_WORLD_COL = 32;
    private static final int MAX_WORLD_ROW = 18;

    public MapManager() {
        tiles = new Tile[16];
        loadTiles();

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
                    mapGrid[i][j] = Integer.parseInt(tokens[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTiles() {
        try {
            tiles[0] = new Tile();
            tiles[0].setImage(ImageIO.read(getClass().getResourceAsStream("/resources/static/tiles/dirt.png")));

            tiles[1] = new Tile();
            tiles[1].setImage(ImageIO.read(getClass().getResourceAsStream("/resources/static/tiles/grass.png")));
            tiles[1].setSolid(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D, int cameraCenterX, int cameraCenterY) {
        int cameraMargin = GamePanel.TILE_SIZE * 2;

        int startCol = Math.max((cameraCenterX - GamePanel.SCREEN_CENTER_X - cameraMargin) / GamePanel.TILE_SIZE, 0);
        int endCol = Math.min((cameraCenterX - GamePanel.SCREEN_CENTER_X + GamePanel.WIDTH + cameraMargin) / GamePanel.TILE_SIZE, MAX_WORLD_COL - 1);

        int startRow = Math.max((cameraCenterY - GamePanel.SCREEN_CENTER_Y - cameraMargin) / GamePanel.TILE_SIZE, 0);
        int endRow = Math.min((cameraCenterY - GamePanel.SCREEN_CENTER_Y + GamePanel.HEIGHT + cameraMargin) / GamePanel.TILE_SIZE, MAX_WORLD_ROW - 1);

        for (int worldRow = startRow; worldRow <= endRow; worldRow++) {
            for (int worldCol = startCol; worldCol <= endCol; worldCol++) {

                int tileNum = mapGrid[worldRow][worldCol];

                int worldX = worldCol * GamePanel.TILE_SIZE;
                int worldY = worldRow * GamePanel.TILE_SIZE;
                int screenX = worldX - cameraCenterX + GamePanel.SCREEN_CENTER_X;
                int screenY = worldY - cameraCenterY + GamePanel.SCREEN_CENTER_Y;

                graphics2D.drawImage(tiles[tileNum].getImage(), screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            }
        }
    }

    public boolean isTileSolid(int row, int col) {
        return tiles[mapGrid[row][col]].isSolid();
    }
}
