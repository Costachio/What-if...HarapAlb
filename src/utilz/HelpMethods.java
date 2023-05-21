package utilz;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Bear;
import entities.Cyclop;
import entities.Golem;
import main.Game;

import static utilz.Constants.EnemyConstants.*;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if (!IsSolid(x, y, levelData))
            if (!IsSolid(x + width, y + height, levelData))
                if (!IsSolid(x + width, y, levelData))
                    if (!IsSolid(x, y + height, levelData))
                        return true;

        return false;


    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {
        int maxWidth = levelData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        return IsTileSolid((int) xIndex, (int) yIndex, levelData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] levelData) {
        int value = levelData[yTile][xTile];
        if (value >= 8 || value < 0 || value != 7)
            return true;
        return false;


    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            //Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;

        } else
            //Left
            return currentTile * Game.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffSet = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffSet - 1;

        } else
            // Jumping
            return currentTile * Game.TILES_SIZE;
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData) {
        //Check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData))
                return false;
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData) {
        if (xSpeed > 0)
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, levelData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }

        return true;
    }

    public static boolean IsSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, levelData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, levelData);

    }

    public static int[][] GetLevelData(BufferedImage img) {

        int[][] levelData = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 7)
                    value = 7;
                levelData[j][i] = value;

            }
        return levelData;
    }

    public static ArrayList<Cyclop> GetCyclops(BufferedImage img) {
        ArrayList<Cyclop> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == CYCLOP)
                    list.add(new Cyclop(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;
    }

    public static ArrayList<Golem> GetGolems(BufferedImage img) {
        ArrayList<Golem> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == GOLEM)
                    list.add(new Golem(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;
    }
    public static ArrayList<Bear> GetBears(BufferedImage img) {
        ArrayList<Bear> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == BEAR)
                    list.add(new Bear(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;
    }

    public static Point GetPlayerSpawn(BufferedImage img) {
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
            }
        return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
    }
}

