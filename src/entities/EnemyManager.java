package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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
    }

    public void update(int[][] levelData, Player player) {
        for (Cyclop c : cyclops)
            if (c.isActive())
                c.update(levelData, player);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCyclops(g, xLvlOffset);
    }

    private void drawCyclops(Graphics g, int xLvlOffset) {
        for (Cyclop c : cyclops)
            if (c.isActive()) {
                g.drawImage(cyclopArr[c.getState()][c.getAnimationIndex()],
                        (int) c.getHitbox().x - xLvlOffset - CYCLOP_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitbox().y - CYCLOP_DRAWOFFSET_Y, CYCLOP_WIDTH * c.flipW(), CYCLOP_HEIGHT, null);
//                c.drawHitbox(g, xLvlOffset);
//                c.drawAttackBox(g, xLvlOffset);
            }

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Cyclop c: cyclops)
            if (c.isActive())
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(10);
                    return;
                }
    }

    private void loadEnemyImgs() {
        cyclopArr = new BufferedImage[8][15];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CYCLOP_SPRITE);
        for (int j = 0; j < cyclopArr.length; j++)
            for (int i = 0; i < cyclopArr[j].length; i++)
                cyclopArr[j][i] = temp.getSubimage(i * CYCLOP_WIDTH_DEFAULT, j * CYCLOP_HEIGHT_DEFAULT, CYCLOP_WIDTH_DEFAULT, CYCLOP_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies() {
        for (Cyclop c: cyclops)
            c.resetEnemy();
    }


}