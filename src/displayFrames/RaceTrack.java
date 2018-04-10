package displayFrames;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import javax.swing.*;

public class RaceTrack extends JPanel {
	private Image image; //test
	private Image raceTrackImage = new ImageIcon("images\\raceTrackBG1.png").getImage();
    private Image carImage = new ImageIcon("images\\car1.png").getImage();

	private int carXPos = 0;
	private int carYPos = 0;
	private int speed = 50;
	
	public RaceTrack() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DOWN: carYPos+=speed; break;
				case KeyEvent.VK_UP: carYPos-=speed; break;
				case KeyEvent.VK_LEFT: carXPos-=speed; break;
				case KeyEvent.VK_RIGHT: carXPos+=speed; break;
				}
				repaint();
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(raceTrackImage, 0, 0, getWidth(), getHeight(), this);
        //g.drawImage(carImage, carXPos, carYPos, getWidth(), getHeight(), this);
        g.drawImage(carImage, carXPos, carYPos, carXPos+100, carYPos+200, this);
	}
}
