package displayFrames;
import javax.swing.*;

public class RaceInfoHUD extends JPanel {
	
	public RaceInfoHUD() {
		add(new JLabel("Time: 00:00.000"));
		add(new JLabel("Lap: 0"));
	}
}
