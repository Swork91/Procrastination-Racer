package launcher;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.ImageIcon;

import track.RaceTrack;
import track.TrackTwo;
import hud.RaceInfoHUD;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 25; 
/******************** Patch Notes **************************
 * 0.25 - Some actual OOP
 * Used the RaceTrack object to create a second race track.
 * It just swaps some colors, but I supposed it could 
 * change just cars or something later if I want.
 * Also switching tracks requires commenting out the other
 * track. Thats pretty inconvenient to the user.  
 * Changed the puddle to start at 0,0 to prevent it from
 * spawn on top of the car at the beginning. 
 ******************** Known Issues *************************
 * 1. Middle out of bounds is sticky. (feature or bug?)
 * 2. Car goes slightly off screen sometimes.
 * 3. Main method isn't thread safe. 
 * 4. Pressing any other key freezes the game. 
 **********************************************************/
public static String saveGameName = "PRsave.dat"; //TODO this could be handled by the user.
Image donk = new ImageIcon("images\\car2.png").getImage();
//private RaceTrack raceTrack = new RaceTrack(); // Default Track
private TrackTwo raceTrack = new TrackTwo(new ImageIcon("images\\car2.png").getImage(), new ImageIcon("images\\raceTrackBG2.png").getImage(), Color.GRAY); //Track 2
private RaceInfoHUD righthud = new RaceInfoHUD();

public MainLauncher() {
	add(raceTrack, BorderLayout.CENTER);
	add(righthud, BorderLayout.EAST);
	raceTrack.setFocusable(true);
}

	public static void main(String[] args) throws IOException {
		/** Make sure you load game data before initializing anything else */
		SaveLoadDataStream.verifyLoadFile(saveGameName);
		
		MainLauncher mainWindow = new MainLauncher();
		mainWindow.setTitle("Procrastination RACER - "+version+'.'+patch);
		mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainWindow.setSize(1280, 720);
		//mainWindow.pack();
		//mainWindow.setResizable(false);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
	}
}
