package entities;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import main.Game;

public class Cyclop extends Enemy {
    public Cyclop(float x, float y) {
        super(x, y, CYCLOP_WIDTH, CYCLOP_HEIGHT, CYCLOP);
        initHitbox(x, y, (int) (26 * Game.SCALE), (int) (30 * Game.SCALE));

    }


    public void update(int[][] levelData, Player player) {
        updateMove(levelData, player);
        updateAnimationTick();

    }

    private void updateMove(int[][] levelData,  Player player) {
        if (firstUpdate)
            firstUpdateCheck(levelData);


        if (inAir) updateInAir(levelData);
        else {
            switch (enemyState) {
                case IDLE:
                    newState(MOVE);
                    break;
                case MOVE:
                    if (canSeePlayer(levelData, player))
                        turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player))
                        newState(STOMP);

                    move(levelData);
                    break;
            }
        }

    }


}
