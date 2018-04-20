package launcher;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import track.RaceTrack;
import hud.RaceInfoHUD;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 15; 
/*************************** Patch Notes *********************************
 * 0.15 - Game timer doesn't start until the game starts. 
 * 
 *************************** Known Issues ********************************
 * when car hits a puddle it will be able to clip into out of bounds.
 * save broke. Game isn't being saved correctly.
 ************************************************************************/
public static String saveGameName = "PRsave.dat"; //TODO this could be handled by the user.
private RaceTrack raceTrack = new RaceTrack();
private RaceInfoHUD righthud = new RaceInfoHUD();

public MainLauncher() {
	add(raceTrack, BorderLayout.CENTER);
	add(righthud, BorderLayout.EAST);
	righthud.setLayout(new GridLayout(5,0));	
	raceTrack.setFocusable(true);
}

	public static void main(String[] args) throws IOException {
		MainLauncher mainWindow = new MainLauncher();
		mainWindow.setTitle("Procrastination RACER - "+version+'.'+patch);
		mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainWindow.setSize(1920, 1080);
		//mainWindow.pack();
		mainWindow.setResizable(false);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
		
		SaveLoadDataStream.verifyLoadFile(saveGameName);
	}
}
