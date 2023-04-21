package entities;

import static utilz.Constants.EnemyConstants.*;
import main.Game;
public class Cyclop extends Enemy{
    public Cyclop(float x, float y) {
        super(x, y, CYCLOP_WIDTH, CYCLOP_HEIGHT, CYCLOP);
        initHitbox(x, y, (int) (50 * Game.SCALE), (int) (30 * Game.SCALE));

    }
}
