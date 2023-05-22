package gamestates;

import main.Game;
import ui.UrmButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.URMButtons.URM_SIZE;
import static utilz.Database.bestScore;

public class Leaderboard extends State implements Statemethods {
    private Menu menu;

    private UrmButton menuButton;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;
    private BufferedImage backgroundImgForest;

    public Leaderboard(Game game) {
        super(game);
        initImg();
        initButtons();
        backgroundImgForest = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
    }

    private void initButtons() {
        int y = (int) (300 * Game.SCALE);
        menuButton = new UrmButton(Game.GAME_WIDTH / 2 - URM_SIZE / 2, y, URM_SIZE, URM_SIZE, 2);
    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImgForest, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);


        menuButton.draw(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.BOLD,50));
        g.drawString("LEADERBOARD",Game.GAME_WIDTH/2 -200,100);
        String champ[]=bestScore();
        g.setColor(Color.BLACK);
        g.drawString(champ[0],Game.GAME_WIDTH/2-champ[0].length()*11-15,Game.GAME_HEIGHT/2-100);
        g.setColor(Color.RED);
        g.drawString(champ[1],Game.GAME_WIDTH/2-champ[1].length()*11-15,Game.GAME_HEIGHT/2);
        g.drawString(champ[2],Game.GAME_WIDTH/2-champ[2].length()*11-15,Game.GAME_HEIGHT/2+100);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void update() {
        menuButton.update();
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);

        if (isIn(menuButton, e))
            menuButton.setMouseOver(true);

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(menuButton, e)) {
            if (menuButton.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
            }
            menuButton.resetBools();
        }

        menuButton.resetBools();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(menuButton, e))
            menuButton.setMousePressed(true);
    }

}
