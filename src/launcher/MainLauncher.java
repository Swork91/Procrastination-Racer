package launcher;
import java.awt.*;
import javax.swing.*;
//My Stuff
import cars.Vehicle;
import displayFrames.*;


public class MainLauncher extends JFrame{
static final private int version = 0; //0 for beta I guess
static final private int patch = 1;

public MainLauncher() {
	add(new RaceTrack(), BorderLayout.CENTER);
	JPanel hud = new RaceInfoHUD();
	hud.setLayout(new GridLayout(2,0));
	add(hud, BorderLayout.EAST);
}

	public static void main(String[] args) {
		MainLauncher mainWindow = new MainLauncher();
		mainWindow.setTitle("Procrastination Racer "+version+'.'+patch);
		mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainWindow.setSize(800, 600);
		//mainWindow.pack();
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);

	}

}
