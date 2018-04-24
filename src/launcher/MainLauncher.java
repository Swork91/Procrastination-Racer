package launcher;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import track.RaceTrack;
import hud.RaceInfoHUD;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 22; 
/*************************** Patch Notes *********************************
 * 0.22 - What a pretty car. 
 * I hate java's layout managers. If I ever make a GUI with them 
 * again I'm becoming a plumber so I have to deal with less shit. 
 * Instead I replaced all the graphics with more appeasing things. 
 *************************** Known Issues ********************************
 * left movement can get stuck in middle out of bounds. (layout issue.)
 * right movement goes slightly off screen. (layout issue.)
 ************************************************************************/
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
