package launcher;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import track.RaceTrack;
import hud.RaceInfoHUD;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 24; 
/******************** Patch Notes **************************
 * 0.24 - bug fixing 4
 * Added a visual indicator if controls get reversed. 
 * Removed car clipping through center OOB.
 * Fixed car getting stuck only when going left.
 * Now the car gets stuck from any direction, but
 * it can back out when it gets stuck. So not REALLY stuck.
 ******************** Known Issues *************************
 * 1. Middle out of bounds is sticky. (feature or bug?)
 * 2. Car goes slightly off screen sometimes.
 * 3. Main method isn't thread safe. 
 * 3. I'm going to v1.0 soon and the game is still bad. 
 **********************************************************/
public static String saveGameName = "PRsave.dat"; //TODO this could be handled by the user.
private RaceTrack raceTrack = new RaceTrack();
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
