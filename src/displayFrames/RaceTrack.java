package displayFrames;
import javax.swing.*;

public class RaceTrack extends JPanel {
	private ImageIcon track1 = new ImageIcon("images\\raceTrackBG1.png");
	
	public RaceTrack() {
		add(new JLabel(track1));
	}
}
