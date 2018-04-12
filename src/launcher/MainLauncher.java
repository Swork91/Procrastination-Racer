package launcher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 8; 
/*************************** Patch Notes *********************************
 * 0.8 will restrict movement to the void zone in the center. 
 * To do this I need to make a bad zone and an escape zone around it.
 * Basically you restrict all movement in the bad zone.
 * Then you restrict the down movement in the top part of the zone.
 * And you do this by increasing the zone by 'speed' for the top or w/e
 ************************************************************************/
private RaceTrack raceTrack = new RaceTrack();
private RaceInfoHUD hud = new RaceInfoHUD();
static int lapNumber = 0;

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
	/***************************************************************
	 * Panel for the race track and the car object driving over it
	 ****************************************************************/
	private static class RaceTrack extends JPanel {
		private Image raceTrackImage = new ImageIcon("images\\raceTrackBG1.png").getImage();
	    private Image carImage = new ImageIcon("images\\car1.png").getImage();

		private int carXPos = 300;
		private int carYPos = 560;
		private int speed = 20;//don't go too fast or you'll clip through stuff
		
		boolean checkpoint = false;
		
		public RaceTrack() {
			addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_DOWN: 
						if (carYPos>=900 || (carXPos>=580 && carXPos<=1100 && carYPos>=280 && carYPos<=640)) {
							break;
						}
						else {
							carYPos+=speed; break;
						}
					case KeyEvent.VK_UP: 
						if (carYPos<=50 || (carXPos>=580 && carXPos<=1100 && carYPos>=300 && carYPos<=660)) {
							break;
						}
						else {
							carYPos-=speed; break;
						}
					case KeyEvent.VK_LEFT: 
						if (carXPos<=50 || (carXPos>=580 && carXPos<=1120 && carYPos>=300 && carYPos<=640)) {
							break;
						}
						else {
							carXPos-=speed; break;
						}
					case KeyEvent.VK_RIGHT: 
						if (carXPos>=1650|| (carXPos>=560 && carXPos<=1100 && carYPos>=300 && carYPos<=640)) {
							break;
						}
						else {
							carXPos+=speed; break;
						}
					}
					repaint();
					if (carXPos>=1100 && carYPos>=550) {
						checkpoint = true;
					}
					if ((carXPos<=600 && carYPos<=550) && checkpoint) {
						lapNumber++;
						checkpoint = false;
					}
					System.out.println("xpos: "+carXPos+"\nypos: "+carYPos+"\n"+"checkpoint: "+checkpoint);
				}
			});
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);		
	        g.drawImage(raceTrackImage, 0, 0, getWidth(), getHeight(), this); //background
	        
	        g.setColor(Color.GREEN);
	        g.fillRect(getWidth()*3/8, getHeight()*3/8, getWidth()/4, getHeight()/4);// inner out of bounds
	        
	        g.setColor(Color.BLUE);
	        g.fillOval(getWidth()/8, getHeight()/8, getWidth()/8, getHeight()/8); //puddle?
	        g.fillOval(getWidth()*3/4, getHeight()/2, getWidth()/8, getHeight()/8); //puddle?
	        
	        g.setColor(Color.YELLOW);
	        g.drawLine(0, getHeight()/2, getWidth()/2, getHeight()/2); //start
	        
	        g.drawImage(carImage, carXPos, carYPos, 100, 100, this); //car
		}
	}
	/***********************************************************************************
	* Panel for showing the time since the race started and the number of laps completed. 
	************************************************************************************/
	public class RaceInfoHUD extends JPanel {
		JLabel seconds = new JLabel();
		JLabel lap = new JLabel();
		public RaceInfoHUD() {
			Timer timer = new Timer(1, new TimerListener());
			timer.start();
			
			add(new JLabel("Procrastination RACER"));
			add(seconds);
			add(lap);
		}
		
		class TimerListener implements ActionListener{
			private int timeMiliSeconds = 0;
			public void actionPerformed(ActionEvent e) {
				seconds.setText((timeMiliSeconds/1000)/60+":"+(timeMiliSeconds/1000)%60+"."+(timeMiliSeconds++%1000));
				lap.setText("Lap: "+lapNumber);
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
