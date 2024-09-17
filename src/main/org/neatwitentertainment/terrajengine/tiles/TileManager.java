package org.neatwitentertainment.terrajengine.tiles;

import org.neatwitentertainment.terrajengine.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private Tile[] tiles;
    private int[][] mapTileNum;
    private GamePanel gamePanel;

    public TileManager(GamePanel gamePanel) {
        tiles = new Tile[10];
        mapTileNum = new int[GamePanel.MAX_WORLD_ROW][GamePanel.MAX_WORLD_COL];

        loadMap();
        getTileImage();
        this.gamePanel = gamePanel;
    }

    public void loadMap() {
        InputStream inputStream = getClass().getResourceAsStream("/resources/static/maps/map.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            for (int i = 0; i < GamePanel.MAX_WORLD_ROW; i++) {
                String line = bufferedReader.readLine();
                String[] tokens = line.split(" ");
                for (int j = 0; j < GamePanel.MAX_WORLD_COL; j++) {
                    mapTileNum[i][j] = Integer.parseInt(tokens[j]);
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
        for (int worldRow = 0; worldRow < GamePanel.MAX_WORLD_ROW; worldRow++) {
            for (int worldCol = 0; worldCol < GamePanel.MAX_WORLD_COL; worldCol++) {

                int CAMERA_MARGIN = GamePanel.TILE_SIZE * 2;
                if (!(worldCol * GamePanel.TILE_SIZE > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().SCREEN_X - CAMERA_MARGIN
                        && worldCol * GamePanel.TILE_SIZE < gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().SCREEN_X + GamePanel.WIDTH + CAMERA_MARGIN
                        && worldRow * GamePanel.TILE_SIZE > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().SCREEN_Y - CAMERA_MARGIN
                        && worldRow * GamePanel.TILE_SIZE < gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().SCREEN_Y + GamePanel.HEIGHT + CAMERA_MARGIN)) {
                    continue;
                }

                int tileNum = mapTileNum[worldRow][worldCol];

                int worldX = worldCol * GamePanel.TILE_SIZE;
                int worldY = worldRow * GamePanel.TILE_SIZE;
                int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().SCREEN_X;
                int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().SCREEN_Y;

                graphics2D.drawImage(tiles[tileNum].getImage(), screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            }
        }
    }

    public Tile getTile(int tileNum) {
        return tiles[tileNum];
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }
}
