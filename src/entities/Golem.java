
package entities;

        import static utilz.Constants.Directions.LEFT;
        import static utilz.Constants.EnemyConstants.*;
        import static utilz.HelpMethods.CanMoveHere;
        import static utilz.HelpMethods.IsFloor;

        import gamestates.Playing;

public class Golem extends Enemy {

    public Golem(float x, float y) {
        super(x, y, GOLEM_WIDTH, GOLEM_HEIGHT, GOLEM);
        initHitbox(18, 22);
        initAttackBox(20, 20, 20);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBoxFlip();
    }

    private void updateBehavior(int[][] levelData, Playing playing) {
        if (firstUpdate)
            firstUpdateCheck(levelData);

        if (!inAir) {
            switch (state) {
                case IDLE:
                    if (IsFloor(hitbox, levelData))
                        newState(MOVE);
                    else
                        inAir = true;
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
                    else if (animationIndex == 3) {
                        if (!attackChecked)
                            checkPlayerHit(attackBox, playing.getPlayer());
                        attackMove(levelData, playing);
                    }

                    break;
                case HIT:
                    if (animationIndex <= GetSpriteAmount(enemyType, state) - 2)
                        pushBack(pushBackDir, levelData, 2f);
                    updatePushBackDrawOffset();
                    break;
            }
        }
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