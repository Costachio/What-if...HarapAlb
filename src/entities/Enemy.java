package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import gamestates.Playing;

import static utilz.Constants.Directions.*;
import static utilz.Constants.*;

import main.Game;

public abstract class Enemy extends Entity {
    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected boolean active = true;
    protected boolean attackChecked;
    protected int attackBoxOffsetX;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        walkSpeed = Game.SCALE * 0.35f;
    }

    protected void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    protected void updateAttackBoxFlip() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width;
        else
            attackBox.x = hitbox.x - attackBoxOffsetX;

        attackBox.y = hitbox.y;
    }

    protected void initAttackBox(int w, int h, int attackBoxOffsetX) {
        attackBox = new Rectangle2D.Float(x, y, (int) (w * Game.SCALE), (int) (h * Game.SCALE));
        this.attackBoxOffsetX = (int) (Game.SCALE * attackBoxOffsetX);
    }

    protected void firstUpdateCheck(int[][] levelData) {
        if (!IsEntityOnFloor(hitbox, levelData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] levelData) {
        if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] levelData) {
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData))
            if (IsFloor(hitbox, xSpeed, levelData)) {
                hitbox.x += xSpeed;
                return;
            }

        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY)
            if (isPlayerInRange(player)) {
                return IsSightClear(levelData, hitbox, player.hitbox, tileY);
            }

        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    protected void newState(int state) {
        this.state = state;
        animationTick = 0;
        animationIndex = 0;
    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0)
            switch (enemyType) {
                case CYCLOP -> {
                    newState(DEAD);
                }
                case GOLEM -> {
                    newState(GOLEM_DEAD);
                }
            }
        else {
            switch (enemyType) {
                case CYCLOP -> {
                    newState(HIT);
                }
                case GOLEM -> {
                    newState(GOLEM_HIT);
                }
            }

            if (walkDir == LEFT)
                pushBackDir = RIGHT;
            else
                pushBackDir = LEFT;
            pushBackOffsetDir = UP;
            pushDrawOffset = 0;
        }
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox))
            player.changeHealth(-GetEnemyDmg(enemyType));
        attackChecked = true;

    }


    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(enemyType, state)) {
                if (enemyType == CYCLOP || enemyType == GOLEM) {
                    animationIndex = 0;

                    switch (state) {
                        case ATTACK, HIT -> state = IDLE;
                        case DEAD -> active = false;
                    }
                }
            }
        }
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        airSpeed = 0;

        pushDrawOffset = 0;

    }

    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;
    }

    public boolean isActive() {
        return active;
    }

    public float getPushDrawOffset() {
        return pushDrawOffset;
    }

}