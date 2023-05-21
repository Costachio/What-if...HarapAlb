
package entities;

        import static utilz.Constants.Directions.LEFT;
        import static utilz.Constants.Directions.RIGHT;
        import static utilz.Constants.EnemyConstants.*;
        import static utilz.HelpMethods.CanMoveHere;
        import static utilz.HelpMethods.IsFloor;

        import gamestates.Playing;
        import main.Game;

        import java.awt.*;
        import java.awt.geom.Rectangle2D;

public class Golem extends Enemy {

    private Rectangle2D.Float attackBox;
    private  int attackBoxOffsetX;
    public Golem(float x, float y) {
        super(x, y, GOLEM_WIDTH, GOLEM_HEIGHT, GOLEM);
        initHitbox(40, 30);
        initAttackBox();
    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (82 * Game.SCALE), (int) (19 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 30);
    }
    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
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
                            newState(ATTACK);
                    }

                    move(levelData);
                    break;
                case ATTACK:
                    if (animationIndex == 0)
                        attackChecked = false;

                    if (animationIndex == 6 && !attackChecked)
                        checkPlayerHit(attackBox, player);

                    break;
                case GOLEM_HIT:
                    break;
            }
        }
    }
    public int flipX() {
        if (walkDir == LEFT)
            return 0;
        else
            return width;
    }

    public int flipW() {
        if (walkDir == LEFT)
            return 1;
        else
            return -1;
    }

}