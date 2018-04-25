package launcher;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import track.RaceTrack;
import hud.RaceInfoHUD;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 23; 
/******************** Patch Notes **************************
 * 0.23 - WASD control Support
 * Added a loop surrounding the key listener that exits 
 * on expected controls being used. Then just had the 
 * alternate controls link to them without exiting the loop.
 ******************** Known Issues *************************
 * 1. left movement can get stuck in middle out of bounds.
 * 2. right movement goes slightly off screen. (same as #1)
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
