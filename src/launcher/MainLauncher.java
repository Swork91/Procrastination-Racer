package launcher;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import track.RaceTrack;
import hud.RaceInfoHUD;

public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 13; 
/*************************** Patch Notes *********************************
 * 0.13 - backend changes
 * Added more+better documentation and created separate classes to
 * help make this readable because 
 * WOOOOOOOOOOOOOOW
 * it was getting bad
 * Now maybe someone else could read it and not have an aneurysm
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
