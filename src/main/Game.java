package main;

import gamestates.Gamestate;
import gamestates.Leaderboard;
import gamestates.Menu;
import gamestates.Playing;
import utilz.Database;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

import static utilz.Database.saveScoreToDatabase;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private Leaderboard leaderboard;


    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    public String playerName;
    boolean firstTimePlaying = true;


    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();
    }

    public void showNameInputDialog() {
        String temp = JOptionPane.showInputDialog("Introdu numele tău:");

        if (temp != null && !temp.isEmpty()) {
            playerName = temp;

        }
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        leaderboard = new Leaderboard(this);

    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {

        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
//                if(playerName == null)
//                    showNameInputDialog();
                playing.update();
                break;
            case LEADERBOARD:
                leaderboard.update();

                break;
            case QUIT:
            default:
                saveScoreToDatabase(playerName,playing.getPlayer().getScore());
                System.exit(0);
                break;
        }
    }


    public void render(Graphics g) {

        switch (Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case LEADERBOARD:
                leaderboard.draw(g);
            default:
                break;
        }

    }


    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;


        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;

        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {

            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                System.out.println("Score:" + playing.getPlayer().getScore());
                frames = 0;
                updates = 0;
            }
        }

    }


    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDirBooleans();

    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }


    public Leaderboard getLeaderboard() {
        return leaderboard;
    }
}