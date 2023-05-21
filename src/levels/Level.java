package levels;

import entities.Cyclop;
import entities.Golem;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.CYCLOP;
import static utilz.Constants.EnemyConstants.GOLEM;
import static utilz.HelpMethods.GetCyclops;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {

    private BufferedImage img;
    private  int [][] levelData;
    private ArrayList<Cyclop> cyclops = new ArrayList<>();
    private ArrayList<Golem> golems = new ArrayList<>();
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        levelData = new int[img.getHeight()][img.getWidth()];
        loadLevel();
        calcLvlOffsets();
    }


    private void loadLevel() {

        // Looping through the image colors just once. Instead of one per
        // object/enemy/etc
        // Removed many methods in HelpMethods class.

        for (int y = 0; y < img.getHeight(); y++)
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
//                int blue = c.getBlue();

                loadLevelData(red, x, y);
                loadEntities(green, x, y);
            }
    }

    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 50)
            levelData[y][x] = 0;
        else
            levelData[y][x] = redValue;
    }

    private void loadEntities(int greenValue, int x, int y) {
        switch (greenValue) {
            case CYCLOP -> cyclops.add(new Cyclop(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
//            case GOLEM -> golems.add(new Golem(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case 100 -> playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
        }
    }
    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
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
    public ArrayList<Golem> getGolems() {
        return golems;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }
}
