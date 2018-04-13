package launcher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 9; 
/*************************** Patch Notes *********************************
 * 0.9 slightly changed the zones for the checkpoint system to be more accurate.
 * It is still possible to cheat the lap system easily, but its only 1st agile iteration.
 * 
 * I also finally added the win condition and a rewarding message screen.
 * The message is great and I don't think I will ever change it. 
 *  
 * (Secret cheats are now available too for cheaters like me)
 ************************************************************************/
private RaceTrack raceTrack = new RaceTrack();
private RaceInfoHUD hud = new RaceInfoHUD();
static int lapNumber = 0;
static int winLaps = 5;

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
		private int carWidth = 100;
		private int carLength = 100;
		
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
					//cheat
					case KeyEvent.VK_C:
						speed=100;
					}
					repaint();
					//lap checkpoint system:
					if ((carXPos>=1100 && carYPos>=400) && (carXPos<=getWidth() && carYPos<=getHeight()/2)) {
						checkpoint = true;
					}
					if ((carXPos>=0 && carYPos>=getHeight()/2-carLength) && (carXPos<=680 && carYPos<=getHeight()/2) && checkpoint) {
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
	        
	        //check for win and display win screen
	        if (winLaps>lapNumber) {
	        	g.drawImage(carImage, carXPos, carYPos, carWidth, carLength, this); //car
	        }
	        else {
	        	g.setColor(Color.ORANGE);
	        	g.fillRoundRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2, getWidth()/3, getHeight()/3);
	        	
	        	g.setColor(Color.WHITE);
	        	//g.drawLine(getWidth()/2, getHeight()/2, getWidth()/2, getHeight()/2);
	        	g.drawLine(getWidth()/2-20-200, getHeight()/2-100, getWidth()/2-200, getHeight()/2);
	        	g.drawLine(getWidth()/2+20-200, getHeight()/2-100, getWidth()/2-200, getHeight()/2);
	        	g.drawLine(getWidth()/2-20-200+40, getHeight()/2-100, getWidth()/2-200+40, getHeight()/2);
	        	g.drawLine(getWidth()/2+20-200+40, getHeight()/2-100, getWidth()/2-200+40, getHeight()/2);
	        	
	        	g.drawLine(getWidth()/2, getHeight()/2-50, getWidth()/2, getHeight()/2+50);
	        	
	        	g.drawLine(getWidth()/2+200, getHeight()/2+100-50, getWidth()/2+200, getHeight()/2+100+50);
	        	g.drawLine(getWidth()/2+200+50, getHeight()/2+100-50, getWidth()/2+200, getHeight()/2+100+50);
	        	g.drawLine(getWidth()/2+200+50, getHeight()/2+100-50, getWidth()/2+200+50, getHeight()/2+100+50);
	        }
	        
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
