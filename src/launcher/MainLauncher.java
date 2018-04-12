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
	hud.setLayout(new GridLayout(3,0));	
	raceTrack.setFocusable(true);
}

	public static void main(String[] args) {
		MainLauncher mainWindow = new MainLauncher();
		mainWindow.setTitle("Procrastination Racer "+version+'.'+patch);
		mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainWindow.setSize(1920, 1080);
		//mainWindow.pack();
		mainWindow.setResizable(false);
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

		private int carXPos = 100;
		private int carYPos = 100;
		private int speed = 50;//don't go too fast or you'll clip thorugh stuff
		
		public RaceTrack() {
			addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_DOWN: 
						if (carYPos>=900) {
							break;
						}
						else {
							carYPos+=speed; break;
						}
					case KeyEvent.VK_UP: 
						if (carYPos<=50) {
							break;
						}
						else {
							carYPos-=speed; break;
						}
					case KeyEvent.VK_LEFT: 
						if (carXPos<=50) {
							break;
						}
						else {
							carXPos-=speed; break;
						}
					case KeyEvent.VK_RIGHT: 
						if (carXPos>=1650) {
							break;
						}
						else {
							carXPos+=speed; break;
						}
					}
					repaint();
					System.out.println("xpos: "+carXPos+"\nypos: "+carYPos+"\n");
				}
			});
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, getWidth(), getHeight());
			
	        g.drawImage(raceTrackImage, 0, 0, getWidth(), getHeight(), this);
	        //g.drawImage(carImage, carXPos, carYPos, carImage.getWidth(null), carImage.getHeight(null), this);
	        g.drawImage(carImage, carXPos, carYPos, 100, 100, this);
	        g.setColor(Color.GREEN);
	        g.fillOval(getWidth()*3/8, getHeight()*3/8, getWidth()/4, getHeight()/4);
	        g.setColor(Color.BLUE);
	        g.fillOval(getWidth()/4, getHeight()/4, getWidth()/8, getHeight()/8);
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
			
			add(new JLabel("Procrastination RACER"));
			add(seconds);
			add(new JLabel("Lap: 0"));
		}
		
		class TimerListener implements ActionListener{
			private int timeMiliSeconds = 0;
			public void actionPerformed(ActionEvent e) {
				seconds.setText((timeMiliSeconds/1000)/60+":"+(timeMiliSeconds/1000)%60+"."+(timeMiliSeconds++%1000));
			}
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 200, 1);
		}
	}
}
