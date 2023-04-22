package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import static utilz.Constants.Directions.*;

import main.Game;


public class Cyclop extends Enemy {


    // AttackBox
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Cyclop(float x, float y) {
        super(x, y, CYCLOP_WIDTH, CYCLOP_HEIGHT, CYCLOP);
        initHitbox(26 ,30 );
        initAttackBox();

    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 10);
    }


    public void update(int[][] levelData, Player player) {
        updateBehavior(levelData, player);
        updateAnimationTick();
        updateAttackBox();

    }

    private void updateAttackBox() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 3);
        else if (walkDir == LEFT)
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 3);

        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }

    private void updateBehavior(int[][] levelData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(levelData);


        if (inAir) updateInAir(levelData);
        else {
            switch (state) {
                case IDLE:
                    newState(MOVE);
                    break;
                case MOVE:
                    if (canSeePlayer(levelData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(STOMP);
                    }

                    move(levelData);
                    break;
                case STOMP:
                    if (animationIndex == 0)
                        attackChecked = false;

                    if (animationIndex == 4 && !attackChecked)
                        checkPlayerHit(attackBox, player);

                    break;
                case HIT:
                    break;
            }
        }
    }

    public int flipX() {
        if (walkDir == LEFT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == LEFT)
            return -1;
        else
            return 1;
    }
}
