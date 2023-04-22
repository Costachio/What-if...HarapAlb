package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Cyclop;
import main.Game;

import static utilz.Constants.EnemyConstants.CYCLOP;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String CYCLOP_SPRITE = "cyclop_sprites.png";
    public static final String LEVEL_ATLAS = "blocuri3.png";
    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND_IMG = "background.png";
    public static final String SOUND_BUTTONS = "sound_button.png";

    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String PLAYING_BG_IMG = "harta (1).png";
    public static final String STATUS_BAR = "health_power_bar.png";


    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] GetLevelData() {

        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
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

    public static ArrayList<Cyclop> GetCyclops() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
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
}
