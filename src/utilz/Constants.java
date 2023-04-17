package utilz;

import main.Game;

public class Constants {
    public class UI {
        public class Buttons {
            public static final int BUTTON_WIDTH_DEFAULT = 140;
            public static final int BUTTON_HEIGHT_DEFAULT = 56;
            public static final int BUTTON_WIDTH = (int) (BUTTON_WIDTH_DEFAULT * Game.SCALE);
            public static final int BUTTON_HEIGHT = (int) (BUTTON_HEIGHT_DEFAULT * Game.SCALE);

        }
    }

    public static class Directions {
        public static final int LEFT = 0;

        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK_1 = 2;
        public static final int ATTACK_2 = 3;
        public static final int ATTACK_3 = 4;
        public static final int JUMP = 5;
        public static final int HIT = 6;
        public static final int DEAD = 7;
        public static final int DOGE = 12;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case IDLE:
                    return 13;
                case ATTACK_1:
                case ATTACK_2:
                case ATTACK_3:
                    return 10;
                case RUNNING:
                    return 8;
                case DEAD:
                    return 7;
                case JUMP:
                    return 6;
                case DOGE:
                    return 5;
                case HIT:
                    return 4;
                default:
                    return 1;

            }

        }


    }

}
