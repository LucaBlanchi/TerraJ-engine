package org.neatwitentertainment.terrajengine.tiles;

import org.neatwitentertainment.terrajengine.GamePanel;
import org.neatwitentertainment.terrajengine.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private final Tile[] tiles;
    private final int[][] mapTileGrid;
    private final Player player;

    private static final int MAX_WORLD_COL = 32;
    private static final int MAX_WORLD_ROW = 18;

    public TileManager(Player player) {
        tiles = new Tile[16];
        mapTileGrid = new int[MAX_WORLD_ROW][MAX_WORLD_COL];

        loadMap();
        getTileImage();
        this.player = player;
    }

    public void loadMap() {
        InputStream inputStream = getClass().getResourceAsStream("/resources/static/maps/map.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            for (int i = 0; i < MAX_WORLD_ROW; i++) {
                String line = bufferedReader.readLine();
                String[] tokens = line.split(" ");
                for (int j = 0; j < MAX_WORLD_COL; j++) {
                    mapTileGrid[i][j] = Integer.parseInt(tokens[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
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

    public void draw(Graphics2D graphics2D) {
        for (int worldRow = 0; worldRow < MAX_WORLD_ROW; worldRow++) {
            for (int worldCol = 0; worldCol < MAX_WORLD_COL; worldCol++) {

                int cameraMargin = GamePanel.TILE_SIZE * 2;
                if (!(worldCol * GamePanel.TILE_SIZE > player.getX() - player.SCREEN_X - cameraMargin
                        && worldCol * GamePanel.TILE_SIZE < player.getX() - player.SCREEN_X + GamePanel.WIDTH + cameraMargin
                        && worldRow * GamePanel.TILE_SIZE > player.getY() - player.SCREEN_Y - cameraMargin
                        && worldRow * GamePanel.TILE_SIZE < player.getY() - player.SCREEN_Y + GamePanel.HEIGHT + cameraMargin)) {
                    continue;
                }

                int tileNum = mapTileGrid[worldRow][worldCol];

                int worldX = worldCol * GamePanel.TILE_SIZE;
                int worldY = worldRow * GamePanel.TILE_SIZE;
                int screenX = worldX - player.getX() + player.SCREEN_X;
                int screenY = worldY - player.getY() + player.SCREEN_Y;

                graphics2D.drawImage(tiles[tileNum].getImage(), screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            }
        }
    }

    public Tile getTile(int tileNum) {
        return tiles[tileNum];
    }

    public int[][] getMapTileGrid() {
        return mapTileGrid;
    }
}
