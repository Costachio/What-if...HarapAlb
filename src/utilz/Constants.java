package utilz;

import main.Game;

public class Constants {

    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int ANIMATION_SPEED = 20;
    public static final int ANIMATION_SPEED_ATTACk = 20;

    public static class EnemyConstants {
        public static final int CYCLOP = 0;
        public static final int GOLEM = 1;
        public static final int BEAR = 2;

        public static final int IDLE = 0;
        public static final int MOVE = 1;
        public static final int ATTACK = 2;
        public static final int THROW = 3;
        public static final int HIT = 4;
        public static final int HIT_MORE = 5;
        public static final int DEAD = 6;
        public static final int LASER = 8;


        public static final int GOLEM_HIT = 3;
        public static final int GOLEM_DEAD = 4;
        public static final int BEAR_MOVE = 0;


        public static final int CYCLOP_WIDTH_DEFAULT = 64;
        public static final int CYCLOP_HEIGHT_DEFAULT = 64;

        public static final int CYCLOP_WIDTH = (int) (CYCLOP_WIDTH_DEFAULT * Game.SCALE * 1.5);
        public static final int CYCLOP_HEIGHT = (int) (CYCLOP_HEIGHT_DEFAULT * Game.SCALE * 1.5);

        public static final int CYCLOP_DRAWOFFSET_X = (int) (30 * Game.SCALE);
        public static final int CYCLOP_DRAWOFFSET_Y = (int) (60 * Game.SCALE);

        public static final int GOLEM_WIDTH_DEFAULT = 192;
        public static final int GOLEM_HEIGHT_DEFAULT = 128;
        public static final int GOLEM_WIDTH = (int) (GOLEM_WIDTH_DEFAULT * Game.SCALE);
        public static final int GOLEM_HEIGHT = (int) (GOLEM_HEIGHT_DEFAULT * Game.SCALE);
        public static final int GOLEM_DRAWOFFSET_X = (int) (75 * Game.SCALE);
        public static final int GOLEM_DRAWOFFSET_Y = (int) (75 * Game.SCALE);
        public static final int BEAR_WIDTH_DEFAULT = 54;
        public static final int BEAR_HEIGHT_DEFAULT = 63;
        public static final int BEAR_WIDTH = (int) (BEAR_WIDTH_DEFAULT * Game.SCALE);
        public static final int BEAR_HEIGHT = (int) (BEAR_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BEAR_DRAWOFFSET_X = (int) (13 * Game.SCALE);
        public static final int BEAR_DRAWOFFSET_Y = (int) (27 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {

            switch (enemy_type) {
                case CYCLOP:
                    switch (enemy_state) {
                        case IDLE:
                            return 15;
                        case MOVE:
                            return 12;
                        case ATTACK:
                            return 7;
                        case HIT:
                            return 3;
                        case HIT_MORE:
                            return 5;
                        case DEAD:
                            return 9;
                    }
                case GOLEM:
                    switch (enemy_state) {
                        case IDLE:
                            return 6;
                        case MOVE:
                            return 10;
                        case ATTACK:
                            return 14;
                        case GOLEM_HIT:
                            return 7;
                        case GOLEM_DEAD:
                            return 16;
                    }
                case BEAR:
                    switch (enemy_state) {
                        case BEAR_MOVE:
                            return 4;
                    }

            }

            return 0;

        }

        public static int GetMaxHealth(int enemy_type) {
            switch (enemy_type) {
                case CYCLOP:
                    return 1;
                case GOLEM:
                    return 1;
                case BEAR:
                    return 1;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDmg(int enemy_type) {
            switch (enemy_type) {
                case CYCLOP:
                    return 1;
                case GOLEM:
                    return 1;
                default:
                    return 1;
            }

        }

    }

    public static class UI {
        public static class Buttons {
            public static final int BUTTON_WIDTH_DEFAULT = 140;
            public static final int BUTTON_HEIGHT_DEFAULT = 56;
            public static final int BUTTON_WIDTH = (int) (BUTTON_WIDTH_DEFAULT * Game.SCALE);
            public static final int BUTTON_HEIGHT = (int) (BUTTON_HEIGHT_DEFAULT * Game.SCALE);

        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
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
        public static final int ATTACK = 2;
        public static final int ATTACK2 = 3;
        public static final int ATTACK3 = 4;
        public static final int JUMP = 5;
        public static final int HIT = 6;
        public static final int DEAD = 7;
        public static final int DOGE = 12;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case IDLE:
                    return 13;
                case ATTACK:
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
