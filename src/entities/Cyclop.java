package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import static utilz.Constants.Directions.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.IsFloor;

import main.Game;
import gamestates.Playing;


public class Cyclop extends Enemy {


    // AttackBox
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Cyclop(float x, float y) {
        super(x, y, CYCLOP_WIDTH, CYCLOP_HEIGHT, CYCLOP);
        initHitbox(26 ,30 );
        initAttackBox(20, 20, 20);

    }

    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (82 * Game.SCALE), (int) (19 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 30);
    }


    public void update(int[][] levelData, Playing playing) {
        updateBehavior(levelData, playing);
        updateAnimationTick();
        updateAttackBoxFlip();

    }

    protected void updateAttackBox() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 3);
        else if (walkDir == LEFT)
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 3);

        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }

    private void updateBehavior(int[][] levelData, Playing playing) {
        if (firstUpdate)
            firstUpdateCheck(levelData);


        if (inAir) updateInAir(levelData);
        else {
            switch (state) {
                case IDLE:
                    newState(MOVE);
                    break;
                case MOVE:
                    if (canSeePlayer(levelData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer()))
                            newState(ATTACK);
                    }

                    move(levelData);
                    break;
                case ATTACK:
                    if (animationIndex == 0)
                        attackChecked = false;
                   else if (animationIndex == 4 && !attackChecked)
                        checkPlayerHit(attackBox,playing.getPlayer());
                    attackMove(levelData, playing);


                    break;
                case HIT:
                    if (animationIndex <= GetSpriteAmount(enemyType, state) - 2)
                        pushBack(pushBackDir, levelData, 2f);
                    updatePushBackDrawOffset();
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


    protected void attackMove(int[][] lvlData, Playing playing) {
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed * 4, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if (IsFloor(hitbox, xSpeed * 4, lvlData)) {
                hitbox.x += xSpeed * 4;
                return;
            }
        newState(IDLE);
    }
}
