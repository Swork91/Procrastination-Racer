package track;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import hud.RaceInfoHUD;
import launcher.MainLauncher;
import launcher.SaveLoadDataStream;

/***************************************************************
 * Panel for the race track, the car driving on it, and any other
 * objects to be used on the track. 
 ****************************************************************/
public class RaceTrack extends JPanel {
	private Image raceTrackImage = new ImageIcon("images\\raceTrackBG1.png").getImage();
    private Image carImage = new ImageIcon("images\\car1.png").getImage();
    private Image startOverlay = new ImageIcon("images\\Procrastination_Racer_Start_Menu.png").getImage();
    
	private int carXPos = 0;
	private int carYPos = 0;
	private int speed = 20;
	private int carWidth = 100;
	private int carLength = 100;
	
	private boolean checkpoint = false;
	private boolean didGameStart = false;
	private boolean reverseControls = false;
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("m:ss.SS");
	private static String lapTimes = "Lap Times!";
	private static String lap1 = "error";
	private static String lap2 = "error";
	private static String lap3 = "error";
	private static String lap4 = "error";
	private static String lap5 = "error";
	private int lap1Time, lap2Time, lap3Time, lap4Time, lap5Time;
	private static int lapNumber = -1;
	private static int winLaps = 5;
	
	public RaceTrack() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				boolean done = false;
				do {
					switch (e.getKeyCode()) {
					/** DOWN PRESSED */
					case (KeyEvent.VK_DOWN):
						speed = getHeight()/50;
						if (reverseControls) {
							if (upPathBlocked()) {
								done = true;
								break;
							}
							else {
								upMove();
								done = true;
								break;
							}
						}
						else {
							if (downPathBlocked()) {
								done = true;
								break;
							}
							else {
								downMove();
								if (hitPuddle()) {
									reverseControls = true;
									done = true;
									break;
								}
								done = true;
								break;
							}
						}
					/** UP PRESSED */
					case KeyEvent.VK_UP: 
						speed = getHeight()/50;
						if (reverseControls) {
							if (downPathBlocked()) {
								done = true;
								break;
							}
							else {
								downMove();
								done = true;
								break;
							}
						}
						else {
							if (upPathBlocked()) {
								done = true;
								break;
							}
							else {
								upMove();
								if (hitPuddle()) {
									reverseControls = true;
									done = true;
									break;
								}
								done = true;
								break;
							}
						}
					/** LEFT PRESSED */
					case KeyEvent.VK_LEFT: 
						speed = getHeight()/50;
						if (reverseControls) {
							if (rightPathBlocked()) {
								done = true;
								break;
							}
							else {
								rightMove();
								done = true;
								break;
							}
						}
						else {
							if (leftPathBlocked()) {
								done = true;
								break;
							}
							else {
								leftMove();
								if (hitPuddle()) {
									reverseControls = true;
									done = true;
									break;
								}
								done = true;
								break;
							}
						}
					/** RIGHT PRESSED */
					case KeyEvent.VK_RIGHT: 
						speed = getHeight()/50;
						if (reverseControls) {
							if (leftPathBlocked()) {
								done = true;
								break;
							}
							else {
								leftMove();
								done = true;
								break;
							}
						}
						else {
							if (rightPathBlocked()) {
								done = true;
								break;
							}
							else {
								rightMove();
								if (hitPuddle()) {
									reverseControls = true;
									done = true;
									break;
								}
								done = true;
								break;
							}
						}
					/** - Alternate Controls - 
					 * Link to normal controls and don't
					 *  give the loop's exit condition */
					case KeyEvent.VK_W:
						e.setKeyCode(KeyEvent.VK_UP);
						break;
					case KeyEvent.VK_A:
						e.setKeyCode(KeyEvent.VK_LEFT);
						break;
					case KeyEvent.VK_S:
						e.setKeyCode(KeyEvent.VK_DOWN);
						break;
					case KeyEvent.VK_D:
						e.setKeyCode(KeyEvent.VK_RIGHT);
						break;
					/** CHEAT KEY PRESSED */
					case KeyEvent.VK_C:
						lapNumber=5;
						done = true;
						break;
					/** start game */
					case KeyEvent.VK_SPACE:
						if (!didGameStart) {
							lapNumber=0;
							speed = getHeight()/50;
							gameStart();
						}
						done = true;
						break;
					}
				} while(!done);
				repaint();
				checkpointSystem();
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
	 *  Start Screen
	 ******************************************************************/
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
        g.drawImage(raceTrackImage, 0, 0, getWidth(), getHeight(), this); //background
        
        g.setColor(Color.GREEN);
        g.fillRect(getWidth()*3/8, getHeight()*3/8, getWidth()/4, getHeight()/4);// inner out of bounds
        
        g.setColor(Color.WHITE);
        g.fillRect(0, getHeight()/2, getWidth()/2-getWidth()/8, getHeight()/128); //starting line
        
        g.setColor(Color.BLUE);
        g.fillOval(RaceInfoHUD.getRandomXCoord(), RaceInfoHUD.getRandomYCoord(), getWidth()/8, getHeight()/8); //puddle
        
        //check for win and display win screen
        if (winLaps>lapNumber) {
        	g.drawImage(carImage, carXPos, carYPos, carWidth, carLength, this); //car
        }
        else { //you WIN
        	g.setColor(Color.ORANGE);
        	g.fillRoundRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2, getWidth()/3, getHeight()/3);
        	
        	g.setColor(Color.WHITE);
        	// W
        	g.fillRect(getWidth()*3/8, getHeight()*15/32, getWidth()/128, getHeight()/16);
        	g.fillRect(getWidth()*3/8+getWidth()/64, getHeight()*15/32, getWidth()/128, getHeight()/16);
        	g.fillRect(getWidth()*3/8+getWidth()*2/64, getHeight()*15/32, getWidth()/128, getHeight()/16);
        	g.fillRect(getWidth()*3/8, getHeight()*33/64, getWidth()/32, getHeight()/64);
        	// I
        	g.fillRect(getWidth()*1/2, getHeight()*15/32, getWidth()/128, getHeight()/16);
        	// N
        	g.fillRect(getWidth()*5/8-getWidth()*2/64, getHeight()*15/32, getWidth()/128, getHeight()/16);
        	g.fillRect(getWidth()*5/8-getWidth()*2/64, getHeight()*31/64, getWidth()/64, getHeight()/64);
        	g.fillRect(getWidth()*5/8-getWidth()*1/64, getHeight()*16/32, getWidth()/64, getHeight()/64);
        	g.fillRect(getWidth()*5/8-getWidth()*1/128, getHeight()*15/32, getWidth()/128, getHeight()/16);
        }
        /** START SCREEN */
        if (lapNumber<0) {
        	g.setColor(Color.WHITE);
        	g.fillRect(0, 0, getWidth(), getHeight());
        	g.drawImage(startOverlay, 0, 0, getWidth(), getHeight(), this);
        	
        }
	}

	public static String getLapTimes() {
		return lapTimes;
	}
	
	public static int getLapNumber() {
		return lapNumber;
	}
	/***********************************************************************
	 *  Lap Checkpoint System:
	 * Keeps track if the car actually makes a loop around the track 
	 * this prevents players from just going backwards and cheating laps.
	 * Also writes the lap times and total time to variables. 
	 ************************************************************************/
	private void checkpointSystem() {
		if (carXPos>=getWidth()*3/8 && carYPos>=getHeight()*3/8 && carYPos<=getHeight()*5/8) {
			checkpoint = true;
		}
		if ((carXPos>=0 && carYPos>=getHeight()/2-carLength) && (carXPos<=getWidth()*3/8 && carYPos<=getHeight()/2) && checkpoint) {
			lapNumber++;
			checkpoint = false;
			//calculates time for each lap and write it to the lapTimes string. 
			switch (lapNumber) {
				case 1:
					lap1Time = RaceInfoHUD.getTimeMiliSeconds();
					lap1 = formatter.format(lap1Time);
					lapTimes = lap1;
					//new record
					checkSaveLapTime(lap1Time);
					break;
				case 2:
					lap2Time = RaceInfoHUD.getTimeMiliSeconds() - lap1Time;
					lap2 = formatter.format(lap2Time);
					lapTimes = "<html>" + lap1 + "<br>" + lap2 + "</html>";
					checkSaveLapTime(lap2Time);
					break;
				case 3:
					lap3Time = RaceInfoHUD.getTimeMiliSeconds() - (lap2Time + lap1Time);
					lap3 = formatter.format(lap3Time);
					lapTimes = "<html>" + lap1 + "<br>" + lap2 + "<br>" + lap3 + "</html>";
					checkSaveLapTime(lap3Time);
					break;
				case 4:
					lap4Time = RaceInfoHUD.getTimeMiliSeconds() - (lap3Time + lap2Time + lap1Time);
					lap4 = formatter.format(lap4Time);
					lapTimes = "<html>" + lap1 + "<br>" + lap2 + "<br>" + lap3 + "<br>" + lap4 + "</html>";
					checkSaveLapTime(lap4Time);
					break;
				case 5:
					lap5Time = RaceInfoHUD.getTimeMiliSeconds() - (lap4Time + lap3Time + lap2Time + lap1Time);
					lap5 = formatter.format(lap5Time);
					lapTimes = "<html>" + lap1 + "<br>" + lap2 + "<br>" + lap3 + "<br>" + lap4 + "<br>" + lap5 + "</html>";
					checkSaveLapTime(lap5Time);
					checkSaveTotalTime();
					autoSave();
					break;
			}
			
		}
	}
	
	/** Method run to position everything correctly when the game starts */
	private void gameStart() {
		didGameStart = true;
		carXPos = getWidth()/7;
		carYPos = getHeight()/2;
		carWidth = getWidth()/20;
		carLength = getHeight()/10;
		RaceInfoHUD.setTimeMiliSeconds(0);
	}
	/** Checks if a LAP time is a record. Then temp-saves it and updates the HUD. */
	private void checkSaveLapTime(int lapTime) {
		if (lapTime < SaveLoadDataStream.getBestLapTime()) {
			SaveLoadDataStream.setBestLapTime(lapTime);
			RaceInfoHUD.setBestLapTime(lapTime);
		}
	}
	/** Checks if the TOTAL time is a record. Then temp-saves it and updates the HUD. */
	private void checkSaveTotalTime() {
		if (RaceInfoHUD.getTimeMiliSeconds() < SaveLoadDataStream.getBestTotalTime()) {
			SaveLoadDataStream.setBestTotalTime(RaceInfoHUD.getTimeMiliSeconds());
			RaceInfoHUD.setBestTotalTime(RaceInfoHUD.getTimeMiliSeconds());
		}
	}
	/** Auto Save (actually writes to data file) */
	private void autoSave() {
		try {
			SaveLoadDataStream.save(MainLauncher.saveGameName);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/** Returns if the car is in a puddles area. */
	private boolean hitPuddle() {
		return (carXPos>=RaceInfoHUD.getRandomXCoord() && carXPos<=RaceInfoHUD.getRandomXCoord()+getWidth()/8 &&
				carYPos>=RaceInfoHUD.getRandomYCoord() && carYPos<=RaceInfoHUD.getRandomYCoord()+getHeight()/8);
	}
	/** returns false if it is possible to move down, true if the path is blocked. */
	public boolean downPathBlocked() {
		return (carYPos+carLength>=getHeight() || (carXPos>=getWidth()*3/8 && carXPos<=getWidth()*5/8 && carYPos+carLength>=getHeight()*3/8 && carYPos+carLength<=getHeight()*5/8));
	}
	
	private void downMove() {
		carYPos+=speed;
	}
	/** returns false if it is possible to move up, true if the path is blocked. */
	private boolean upPathBlocked() {
		return (carYPos<=0 || (carXPos>=getWidth()*3/8 && carXPos<=getWidth()*5/8 && carYPos>=getHeight()*3/8 && carYPos<=getHeight()*5/8));
	}
	
	private void upMove() {
		carYPos-=speed;
	}
	/** returns false if it is possible to move left, true if the path is blocked. */
	private boolean leftPathBlocked() {
		return (carXPos<=0 || (carXPos>=getWidth()*3/8 && carXPos<=getWidth()*5/8 && carYPos>=getHeight()*3/8 && carYPos<=getHeight()*5/8));
	}
	
	private void leftMove() {
		carXPos-=speed;
	}
	/** returns false if it is possible to move right, true if the path is blocked. */
	private boolean rightPathBlocked() {
		return (carXPos+carWidth>=getWidth() || (carXPos+carWidth>=getWidth()*3/8 && carXPos<=getWidth()*5/8 && carYPos>=getHeight()*3/8 && carYPos<=getHeight()*5/8));
	}
	
	private void rightMove() {
		carXPos+=speed;
	}
}