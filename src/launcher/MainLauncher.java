package launcher;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 4;
private RaceTrack raceTrack = new RaceTrack();

public MainLauncher() {
	add(raceTrack, BorderLayout.CENTER);
	
	JPanel hud = new RaceInfoHUD();
	hud.setLayout(new GridLayout(2,0));
	add(hud, BorderLayout.EAST);
	
	raceTrack.setFocusable(true);
}

	public static void main(String[] args) {
		MainLauncher mainWindow = new MainLauncher();
		mainWindow.setTitle("Procrastination Racer "+version+'.'+patch);
		mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainWindow.setSize(1920, 1080);
		//mainWindow.pack();
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
		
	}
	
	static class RaceTrack extends JPanel {
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
					System.out.println("xpos: "+carXPos+"\nypos: "+carYPos+"\n");
				}
			});
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
	        g.drawImage(raceTrackImage, 0, 0, getWidth(), getHeight(), this);
			g.drawImage(carImage, carXPos, carYPos, carImage.getWidth(null), carImage.getHeight(null), this);
	        //g.drawImage(carImage, carXPos, carYPos, carXPos+100, carYPos+200, this);
		}
	}
	
	public class RaceInfoHUD extends JPanel {
		
		public RaceInfoHUD() {
			add(new JLabel("Time: 00:00.000"));
			add(new JLabel("Lap: 0"));
		}
	}
}
