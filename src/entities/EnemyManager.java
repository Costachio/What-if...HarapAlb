package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] cyclopArr, golemArr;
    private ArrayList<Cyclop> cyclops = new ArrayList<>();
    private Level currentLevel;

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

    public void loadEnemies(Level level) {
        this.currentLevel = level;
    }


//    public void loadEnemies(Level level) {
//		cyclops = level.getCyclops();
//	}

    public void update(int[][] levelData, Player player) {
        boolean isAnyActive = false;
        for (Cyclop c : cyclops)
            if (c.isActive()){
                c.update(levelData, playing);
        isAnyActive = true;
    }

        for (Golem s : currentLevel.getGolems())
            if (s.isActive()) {
                s.update(levelData, playing);
                isAnyActive = true;
            }
		if(!isAnyActive)
            playing.setLevelCompleted(true);
}


    public void draw(Graphics g, int xLvlOffset) {
        drawCyclops(g, xLvlOffset);
        drawGolems(g, xLvlOffset);
    }

    private void drawCyclops(Graphics g, int xLvlOffset) {
        for (Cyclop c : cyclops)
            if (c.isActive()) {
                g.drawImage(cyclopArr[c.getState()][c.getAnimationIndex()],
                        (int) c.getHitbox().x - xLvlOffset - CYCLOP_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitbox().y - CYCLOP_DRAWOFFSET_Y, CYCLOP_WIDTH * c.flipW(), CYCLOP_HEIGHT, null);
            }

    }

    private void drawGolems(Graphics g, int xLvlOffset) {
        for (Golem s : currentLevel.getGolems())
            if (s.isActive()) {
                g.drawImage(golemArr[s.getState()][s.getAnimationIndex()], (int) s.getHitbox().x - xLvlOffset - GOLEM_DRAWOFFSET_X + s.flipX(),
                        (int) s.getHitbox().y - GOLEM_DRAWOFFSET_Y + (int) s.getPushDrawOffset(), GOLEM_WIDTH * s.flipW(), GOLEM_HEIGHT, null);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Cyclop c : currentLevel.getCyclops())
            if (c.isActive())
                if (c.getState() != DEAD && c.getState() != HIT)
                    if (attackBox.intersects(c.getHitbox())) {
                        c.hurt(20);
                        return;
                    }

        for (Golem g : currentLevel.getGolems())
            if (g.isActive()) {
                if (g.getState() != GOLEM_DEAD && g.getState() != GOLEM_HIT)
                    if (attackBox.intersects(g.getHitbox())) {
                        g.hurt(20);
                        return;
                    }
            }
    }

    private void loadEnemyImgs() {
        cyclopArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.CYCLOP_SPRITE), 7, 14, CYCLOP_WIDTH_DEFAULT, CYCLOP_HEIGHT_DEFAULT);
        golemArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.GOLEM_ATLAS), 3, 14, GOLEM_WIDTH_DEFAULT, GOLEM_HEIGHT_DEFAULT);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 0; i < tempArr[j].length; i++)
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
        return tempArr;
    }

    public void resetAllEnemies() {
        for (Cyclop c: currentLevel.getCyclops())
            c.resetEnemy();
        for (Golem g: currentLevel.getGolems())
            g.resetEnemy();
    }


}