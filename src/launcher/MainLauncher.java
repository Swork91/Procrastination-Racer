package launcher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 5;
private RaceTrack raceTrack = new RaceTrack();
private RaceInfoHUD hud = new RaceInfoHUD();

public MainLauncher() {
	add(raceTrack, BorderLayout.CENTER);
	add(hud, BorderLayout.EAST);
	hud.setLayout(new GridLayout(2,0));	
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
	/***********
	 * Panel for the race track and the car object driving over it
	 * 
	 *********/
	private static class RaceTrack extends JPanel {
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
		}
	}
	/**********
	//Panel for showing the time since the race started and the number of laps completed. 
	 ***********/
	public class RaceInfoHUD extends JPanel {
		JLabel seconds = new JLabel();
		public RaceInfoHUD() {
			Timer timer = new Timer(1, new TimerListener());
			timer.start();
			
			add(seconds);
			add(new JLabel("Lap: 0"));
		}
		
		class TimerListener implements ActionListener{
			private int timeMiliSeconds = 0;
			public void actionPerformed(ActionEvent e) {
				seconds.setText("00:"+timeMiliSeconds/1000+"."+(timeMiliSeconds++%1000));			
			}
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 200, 1);
		}
	}
}
