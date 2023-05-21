package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.EnemyConstants.GOLEM_DRAWOFFSET_Y;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] cyclopArr;
    private BufferedImage[][] golemArr;
    private BufferedImage[][] bearArr;
    private ArrayList<Cyclop> cyclops = new ArrayList<>();
    private ArrayList<Golem> golems = new ArrayList<>();
    private ArrayList<Bear> bears = new ArrayList<>();

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	public void loadEnemies(Level level) {
		cyclops = level.getCyclops();
        golems=level.getGolems();
        bears=level.getBears();
	}

    public void update(int[][] levelData, Player player) {
        boolean isAnyActive = false;
        for (Cyclop c : cyclops)
            if (c.isActive()){
                c.update(levelData, player);
                isAnyActive = true;
            }

        for(Golem g:golems)
            if(g.isActive()){
                g.update(levelData, player);
                isAnyActive=true;
            }
        for (Bear b : bears)
            if(b.isActive()){
                b.update(levelData, player);
                isAnyActive=true;
            }
		if(!isAnyActive)
            playing.setLevelCompleted(true);

}


    public void draw(Graphics g, int xLvlOffset) {
        drawCyclops(g, xLvlOffset);
        drawGolems(g, xLvlOffset);
        drawBears(g, xLvlOffset);
    }

    private void drawCyclops(Graphics g, int xLvlOffset) {
        for (Cyclop c : cyclops)
            if (c.isActive()) {
                g.drawImage(cyclopArr[c.getState()][c.getAnimationIndex()],
                        (int) c.getHitbox().x - xLvlOffset - CYCLOP_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitbox().y - CYCLOP_DRAWOFFSET_Y, CYCLOP_WIDTH * c.flipW(), CYCLOP_HEIGHT, null);
                c.drawHitbox(g, xLvlOffset);
                c.drawAttackBox(g, xLvlOffset);
            }

    }

    private void drawGolems(Graphics g, int xLvlOffset) {
        for (Golem gl : golems)
            if (gl.isActive())  {
                g.drawImage(golemArr[gl.getState()][gl.getAnimationIndex()],
                        (int) gl.getHitbox().x - xLvlOffset - GOLEM_DRAWOFFSET_X + gl.flipX(),
                        (int) gl.getHitbox().y - GOLEM_DRAWOFFSET_Y, GOLEM_WIDTH * gl.flipW(), GOLEM_HEIGHT, null);
                gl.drawHitbox(g, xLvlOffset);
                gl.drawAttackBox(g, xLvlOffset);
            }

    }

    private void drawBears(Graphics g, int xLvlOffset) {
        for (Bear b : bears)
            if (b.isActive())  {
                g.drawImage(bearArr[b.getState()][b.getAnimationIndex()],
                        (int) b.getHitbox().x - xLvlOffset - BEAR_DRAWOFFSET_X + b.flipX(),
                        (int) b.getHitbox().y - BEAR_DRAWOFFSET_Y, BEAR_WIDTH * b.flipW(), BEAR_HEIGHT, null);
                b.drawHitbox(g, xLvlOffset);
                b.drawAttackBox(g, xLvlOffset);
            }

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Cyclop c: cyclops)
            if (c.isActive())
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(20);
                    return;
                }
        for (Golem g : golems)
            if (g.isActive()) {
                    if (attackBox.intersects(g.getHitbox())) {
                        g.hurt(20);
                        return;
                    }
            }
        for (Bear b : bears)
            if (b.isActive()) {
                    if (attackBox.intersects(b.getHitbox())) {
                        b.hurt(20);
                        return;
                    }
            }
    }

    private void loadEnemyImgs() {
        cyclopArr = new BufferedImage[8][15];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CYCLOP_SPRITE);
        for (int j = 0; j < cyclopArr.length; j++)
            for (int i = 0; i < cyclopArr[j].length; i++)
                cyclopArr[j][i] = temp.getSubimage(i * CYCLOP_WIDTH_DEFAULT, j * CYCLOP_HEIGHT_DEFAULT, CYCLOP_WIDTH_DEFAULT, CYCLOP_HEIGHT_DEFAULT);

        golemArr = new BufferedImage[5][16];
        BufferedImage temp2 = LoadSave.GetSpriteAtlas(LoadSave.GOLEM_ATLAS);
        for (int j = 0; j < golemArr.length; j++)
            for (int i = 0; i < golemArr[j].length; i++)
                golemArr[j][i] = temp2.getSubimage(i * GOLEM_WIDTH_DEFAULT, j * GOLEM_HEIGHT_DEFAULT, GOLEM_WIDTH_DEFAULT, GOLEM_HEIGHT_DEFAULT);
        bearArr = new BufferedImage[1][4];
        BufferedImage temp3 = LoadSave.GetSpriteAtlas(LoadSave.BEAR_ATLAS);
        for (int j = 0; j < bearArr.length; j++)
            for (int i = 0; i < bearArr[j].length; i++)
                bearArr[j][i] = temp3.getSubimage(i * BEAR_WIDTH_DEFAULT, j * BEAR_HEIGHT_DEFAULT, BEAR_WIDTH_DEFAULT, BEAR_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies() {
        for (Cyclop c: cyclops)
            c.resetEnemy();
        for (Golem g: golems)
            g.resetEnemy();
        for (Bear b : bears)
            b.resetEnemy();
    }


}