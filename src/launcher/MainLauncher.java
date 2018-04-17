package launcher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 11; 
/*************************** Patch Notes *********************************
 * 0.11
 * minor improvements to code readability. 
 * added a display for the lap times that shows the time for each lap.
 * I had to use HTML for it, but its fine. It works. 
 * Also I have double the amount of global variables I started with. 
 * So I guess I'm working on making my first god class. 
 ************************************************************************/
private RaceTrack raceTrack = new RaceTrack();
private RaceInfoHUD hud = new RaceInfoHUD();
static int lapNumber = 0;
static int winLaps = 5;

private static int timeMiliSeconds = 0;

static String lapTimes = "Lap Times!";
static final String gameTitle = "Procrastination RACER";

public MainLauncher() {
	add(raceTrack, BorderLayout.CENTER);
	add(hud, BorderLayout.EAST);
	hud.setLayout(new GridLayout(4,0));	
	raceTrack.setFocusable(true);
}

	public static void main(String[] args) {
		MainLauncher mainWindow = new MainLauncher();
		mainWindow.setTitle(gameTitle+" - "+version+'.'+patch);
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
		
		private boolean checkpoint = false;
		
		private String lap1 = "error";
		private String lap2 = "error";
		private String lap3 = "error";
		private String lap4 = "error";
		private String lap5 = "error";
		
		private int previousMiliseconds, tempTime;
		
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
					/* lap checkpoint system:
					 * should keep track if the car actually makes a loop around the track 
					 * this prevents players from just going backwards and cheating laps
					 */
					if ((carXPos>=1100 && carYPos>=400) && (carXPos<=getWidth() && carYPos<=getHeight()/2)) {
						checkpoint = true;
					}
					if ((carXPos>=0 && carYPos>=getHeight()/2-carLength) && (carXPos<=680 && carYPos<=getHeight()/2) && checkpoint) {
						lapNumber++;
						checkpoint = false;
						switch (lapNumber) {
							case 1:
								previousMiliseconds = timeMiliSeconds;
								lap1 = ((timeMiliSeconds/1000/60) + ":" + (timeMiliSeconds/1000%60) + "." + (timeMiliSeconds%1000));
								lapTimes = lap1;
								break;
							case 2:
								tempTime = timeMiliSeconds - previousMiliseconds;
								previousMiliseconds = timeMiliSeconds;
								lap2 = ((tempTime/1000/60) + ":" + (tempTime/1000%60) + "." + (tempTime%1000));
								lapTimes = "<html>" + lap1 + "<br>" + lap2 + "</html>";
								break;
							case 3:
								tempTime = timeMiliSeconds - previousMiliseconds;
								previousMiliseconds = timeMiliSeconds;
								lap3 = ((tempTime/1000/60) + ":" + (tempTime/1000%60) + "." + (tempTime%1000));
								lapTimes = "<html>" + lap1 + "<br>" + lap2 + "<br>" + lap3 + "</html>";
								break;
							case 4:
								tempTime = timeMiliSeconds - previousMiliseconds;
								previousMiliseconds = timeMiliSeconds;
								lap4 = ((tempTime/1000/60) + ":" + (tempTime/1000%60) + "." + (tempTime%1000));
								lapTimes = "<html>" + lap1 + "<br>" + lap2 + "<br>" + lap3 + "<br>" + lap4 + "</html>";
								break;
							case 5:
								tempTime = timeMiliSeconds - previousMiliseconds;
								previousMiliseconds = timeMiliSeconds;
								lap5 = ((tempTime/1000/60) + ":" + (tempTime/1000%60) + "." + (tempTime%1000));
								lapTimes = "<html>" + lap1 + "<br>" + lap2 + "<br>" + lap3 + "<br>" + lap4 + "<br>" + lap5 + "</html>";
								break;
						}
						
					}
					System.out.println("xpos: "+carXPos+"\nypos: "+carYPos+"\n"+"checkpoint: "+checkpoint);
				}
			});
		}
		/******************************************************************
		 * Creates the graphics for the raceTrack frame.
		 * includes:
		 * 	track asphalt 
		 * 	green void zone in center
		 * 	finish/starting line
		 * 	danger puddles
		 * 	The Race Car
		 * 	Win Screen
		 ******************************************************************/
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);		
	        g.drawImage(raceTrackImage, 0, 0, getWidth(), getHeight(), this); //background
	        
	        g.setColor(Color.GREEN);
	        g.fillRect(getWidth()*3/8, getHeight()*3/8, getWidth()/4, getHeight()/4);// inner out of bounds
	        //TODO puddles move and slow car down. 
	        g.setColor(Color.BLUE);
	        g.fillOval(getWidth()/8, getHeight()/8, getWidth()/8, getHeight()/8); //puddle?
	        g.fillOval(getWidth()*3/4, getHeight()/2, getWidth()/8, getHeight()/8); //puddle?
	        
	        g.setColor(Color.YELLOW);
	        g.fillRect(0, getHeight()/2, getWidth()/2-getWidth()/8, getHeight()/64); //start
	        
	        //check for win and display win screen
	        if (winLaps>lapNumber) {
	        	g.drawImage(carImage, carXPos, carYPos, carWidth, carLength, this); //car
	        }
	        else {
	        	g.setColor(Color.ORANGE);
	        	g.fillRoundRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2, getWidth()/3, getHeight()/3);
	        	
	        	g.setColor(Color.WHITE);
	        	// W
	        	g.drawLine(getWidth()/2-20-200, getHeight()/2-100, getWidth()/2-200, getHeight()/2);
	        	g.drawLine(getWidth()/2+20-200, getHeight()/2-100, getWidth()/2-200, getHeight()/2);
	        	g.drawLine(getWidth()/2-20-200+40, getHeight()/2-100, getWidth()/2-200+40, getHeight()/2);
	        	g.drawLine(getWidth()/2+20-200+40, getHeight()/2-100, getWidth()/2-200+40, getHeight()/2);
	        	// I
	        	g.drawLine(getWidth()/2, getHeight()/2-50, getWidth()/2, getHeight()/2+50);
	        	// N
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
		JLabel seconds = new JLabel("", SwingConstants.CENTER);
		JLabel lap = new JLabel("", SwingConstants.CENTER);
		JLabel lapRecord = new JLabel("", SwingConstants.CENTER);
		
		public RaceInfoHUD() {
			Timer timer = new Timer(1, new TimerListener());
			timer.start();
			
			add(new JLabel(gameTitle));
			add(seconds);
			add(lap);
			add(lapRecord);
		}
		
		class TimerListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				// TODO String.format this text to not look terrible.
				if (lapNumber != winLaps)
					timeMiliSeconds++; //TODO stop the timer, not just my counter. 
				
				seconds.setText((timeMiliSeconds/1000/60) + ":" + (timeMiliSeconds/1000%60) + "." + (timeMiliSeconds%1000));
				lap.setText("Lap: "+lapNumber);
				lapRecord.setText(lapTimes);
			}
		}
	}
}
