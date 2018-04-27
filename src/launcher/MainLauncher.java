package launcher;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

import track.RaceTrack;
import track.TrackTwo;
import hud.RaceInfoHUD;

public class MainLauncher extends JFrame{
static final private int version = 1;
static final private int patch = 0; 
/******************** Patch Notes **************************
 * 1.0 - Final touches
 * Fixed issue 4. Game wont freeze now.
 * We're thread safe now. 
 * Deleted my save.dat from directory
 * Added a start menu to let the user choose a track object.
 ******************** Known Issues *************************
 * 1. Middle out of bounds is sticky. (feature or bug?)
 * 2. Car goes slightly off screen sometimes.
 **********************************************************/
public static String saveGameName = "PRsave.dat"; //TODO this could be handled by the user.
public static int trackNumber = 0;
private static RaceTrack raceTrack = new RaceTrack(); // Default Track
private static TrackTwo raceTrack2 = new TrackTwo(new ImageIcon("images\\Procrastination_Racer_Start_Menu.png").getImage(), new ImageIcon("images\\car2.png").getImage(), new ImageIcon("images\\raceTrackBG2.png").getImage(), Color.GRAY); //TODO give user access to this. 
private static RaceInfoHUD righthud = new RaceInfoHUD();

/** Build the game GUI */
private static void createAndShowGUI() throws IOException {
	/** Make sure you load game data before initializing anything else */
	SaveLoadDataStream.verifyLoadFile(saveGameName);
	
	MainLauncher mainWindow = new MainLauncher();
	switch (trackNumber) {
	case 1:
		mainWindow.add(raceTrack, BorderLayout.CENTER);
		raceTrack.setFocusable(true);
		break;
	case 2: 
		mainWindow.add(raceTrack2, BorderLayout.CENTER);
		raceTrack2.setFocusable(true);
		break;
	default:
		mainWindow.add(raceTrack, BorderLayout.CENTER);
		raceTrack.setFocusable(true);
		break;
	}
	mainWindow.add(righthud, BorderLayout.EAST);
		
	mainWindow.setTitle("Procrastination RACER - "+version+'.'+patch);
	mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
	mainWindow.setSize(1280, 720);
	mainWindow.setLocationRelativeTo(null);
	mainWindow.setVisible(true);
}
/** Creates a welcome screen to allow the user to perform some actions before the game loads anything. */
// TODO: improve this. I threw it together on a Friday afternoon. 
private static void welcomeScreen() {
	JFrame welcome = new JFrame();
	JButton track1 = new JButton("Track 1");
	JButton track2 = new JButton("Track 2");
	JLabel jlblwelcome = new JLabel("Welcome! \n Please select a track.");
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	p2.setLayout(new GridLayout(2,0));
	track1.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			trackNumber = 1;
			try {
				createAndShowGUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	track2.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			trackNumber = 2;
			try {
				createAndShowGUI();
			} catch (IOException o) {
				// TODO Auto-generated catch block
				o.printStackTrace();
			}
		}
		
	});
	p1.add(jlblwelcome);
	p2.add(track1);
	p2.add(track2);
	welcome.add(p1, BorderLayout.CENTER);
	welcome.add(p2, BorderLayout.SOUTH);
	welcome.setTitle("Procrastination RACER - "+version+'.'+patch);
	welcome.setDefaultCloseOperation(EXIT_ON_CLOSE);
	welcome.pack();
	welcome.setLocationRelativeTo(null);
	welcome.setVisible(true);
}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				welcomeScreen();
			}
		});
	}
}
