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
    
	private int carXPos = 300;
	private int carYPos = 560;
	private int speed = 20;//don't go too fast or you'll clip through stuff
	private int carWidth = 100;
	private int carLength = 100;
	
	private boolean checkpoint = false;
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("m:ss.SS");
	private static String lapTimes = "Lap Times!";
	private static String lap1 = "error";
	private static String lap2 = "error";
	private static String lap3 = "error";
	private static String lap4 = "error";
	private static String lap5 = "error";
	private int lap1Time, lap2Time, lap3Time, lap4Time, lap5Time;
	private static int lapNumber = 0;
	private static int winLaps = 5;
	
	public RaceTrack() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case (KeyEvent.VK_DOWN): 
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
					//calculates time for each lap and write it to the lapTimes string. 
					switch (lapNumber) {
						case 1:
							lap1Time = RaceInfoHUD.getTimeMiliSeconds();
							lap1 = formatter.format(lap1Time);
							lapTimes = lap1;
							//new record
							if (lap1Time<SaveLoadDataStream.getBestLapTime()) {
								SaveLoadDataStream.setBestLapTime(lap1Time);
							}
							break;
						case 2:
							lap2Time = RaceInfoHUD.getTimeMiliSeconds() - lap1Time;
							lap2 = formatter.format(lap2Time);
							lapTimes = "<html>" + lap1 + "<br>" + lap2 + "</html>";
							if (lap2Time<SaveLoadDataStream.getBestLapTime()) {
								SaveLoadDataStream.setBestLapTime(lap2Time);
							}
							break;
						case 3:
							lap3Time = RaceInfoHUD.getTimeMiliSeconds() - (lap2Time + lap1Time);
							lap3 = formatter.format(lap3Time);
							lapTimes = "<html>" + lap1 + "<br>" + lap2 + "<br>" + lap3 + "</html>";
							if (lap3Time<SaveLoadDataStream.getBestLapTime()) {
								SaveLoadDataStream.setBestLapTime(lap3Time);
							}
							break;
						case 4:
							lap4Time = RaceInfoHUD.getTimeMiliSeconds() - (lap3Time + lap2Time + lap1Time);
							lap4 = formatter.format(lap4Time);
							lapTimes = "<html>" + lap1 + "<br>" + lap2 + "<br>" + lap3 + "<br>" + lap4 + "</html>";
							if (lap4Time<SaveLoadDataStream.getBestLapTime()) {
								SaveLoadDataStream.setBestLapTime(lap4Time);
							}
							break;
						case 5:
							lap5Time = RaceInfoHUD.getTimeMiliSeconds() - (lap4Time + lap3Time + lap2Time + lap1Time);
							lap5 = formatter.format(lap5Time);
							lapTimes = "<html>" + lap1 + "<br>" + lap2 + "<br>" + lap3 + "<br>" + lap4 + "<br>" + lap5 + "</html>";
							
							if (lap5Time<SaveLoadDataStream.getBestLapTime()) {
								SaveLoadDataStream.setBestLapTime(lap5Time);
							}
							//new record for total time
							if (RaceInfoHUD.getTimeMiliSeconds()<SaveLoadDataStream.getBestTotalTime()) {
								SaveLoadDataStream.setBestTotalTime(RaceInfoHUD.getTimeMiliSeconds());
							}
							//save any new records.
							try {
								SaveLoadDataStream.save(MainLauncher.saveGameName);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							break;
					}
					
				}
				//TODO delete me when done debugging
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
        else { //you WIN
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
	
	public static String getLapTimes() {
		return lapTimes;
	}
	
	public static int getLapNumber() {
		return lapNumber;
	}
}