package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] cyclopArr;
    private ArrayList<Cyclop> cyclops = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        cyclops = LoadSave.GetCyclops();
        System.out.println("size of cyclops: " + cyclops.size());
    }

    public void update(int[][] levelData) {
        for (Cyclop c : cyclops)
            c.update(levelData);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCyclops(g, xLvlOffset);
        drawHitbox(g, xLvlOffset);

    }

    private void drawCyclops(Graphics g, int xLvlOffset) {
        for (Cyclop c: cyclops)
            g.drawImage(cyclopArr[c.getEnemyState()][c.getAniIndex()],
                    (int) c.getHitbox().x - xLvlOffset - CYCLOP_DRAWOFFSET_X,
                    (int) c.getHitbox().y - CYCLOP_DRAWOFFSET_Y, CYCLOP_WIDTH, CYCLOP_HEIGHT, null);

    }

    private void loadEnemyImgs() {
        cyclopArr = new BufferedImage[8][15];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CYCLOP_SPRITE);
        for (int j = 0; j < cyclopArr.length; j++)
            for (int i = 0; i < cyclopArr[j].length; i++)
                cyclopArr[j][i] = temp.getSubimage(i * CYCLOP_WIDTH_DEFAULT, j * CYCLOP_HEIGHT_DEFAULT, CYCLOP_WIDTH_DEFAULT, CYCLOP_HEIGHT_DEFAULT);
    }

    protected void drawHitbox(Graphics g, int xLvlOffset) {
        // For debugging the hitbox
        g.setColor(Color.PINK);
        for (Cyclop c: cyclops)
            g.drawRect((int) c.getHitbox().x - xLvlOffset, (int) c.getHitbox().y, (int) c.getHitbox().width, (int) c.getHitbox().height);
    }

}