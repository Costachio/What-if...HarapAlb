package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage [] idleAnimation;
    private  int animationTick, animationIndex, animationSpeed = 15;


    public GamePanel() {
        mouseInputs = new MouseInputs(this);


        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        idleAnimation = new BufferedImage[12];
        for( int i = 0; i < idleAnimation.length; i++)
            idleAnimation[i]=img.getSubimage(i*32,0,32, 32);

    }

	private void importImg() {
		InputStream is = getClass().getResourceAsStream("/player_sprites.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }
    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed)
        {
            animationTick =0;
            animationIndex++;
            if(animationTick == idleAnimation.length)
                animationIndex = 0;
        }

    }

    public void paintComponent(Graphics g) {
        updateAnimationTick();

        g.drawImage(idleAnimation[animationIndex], (int)xDelta, (int)yDelta,64, 64, null);

    }



}