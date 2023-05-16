package levels;

import entities.Cyclop;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.GetCyclops;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {

    private BufferedImage img;
    private  int [][] levelData;
    private ArrayList<Cyclop> cyclops;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
        calcPlayerSpawn();
    }
    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        cyclops = GetCyclops(img);
    }

    private void createLevelData() {
        levelData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y)
    {
        return levelData[y][x];
    }

    public int[][] getLevelData()
    {
        return levelData;
    }


    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public ArrayList<Cyclop> getCyclops() {
        return cyclops;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }
}
