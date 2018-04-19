package hud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import launcher.SaveLoadDataStream;
import track.RaceTrack;

/***********************************************************************************
* Panel for showing 
* -Game name.
* -Race time.
* -Lap times
* -Best times. (records)
************************************************************************************/
public class RaceInfoHUD extends JPanel {
	JLabel seconds = new JLabel("", SwingConstants.CENTER);
	JLabel lap = new JLabel("", SwingConstants.CENTER);
	JLabel lapRecord = new JLabel("", SwingConstants.CENTER);
	JLabel bestRecods = new JLabel("", SwingConstants.CENTER);
	
	private static int timeMiliSeconds = 0;
	private static int winLaps = 5;
	static SimpleDateFormat formatter = new SimpleDateFormat("m:ss.SS");
	
	public RaceInfoHUD() {
		Timer timer = new Timer(1, new TimerListener());
		timer.start();

		add(new JLabel("Procrastination RACER"));
		add(seconds);
		add(lap);
		add(lapRecord);
		add(bestRecods);
	}
	
	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (RaceTrack.getLapNumber() != winLaps)
				timeMiliSeconds++; //TODO stop the timer, not just my counter. Does 'timer.stop();' work?
			
			String result = formatter.format(timeMiliSeconds);
			seconds.setText(result);
			lap.setText("Lap: "+RaceTrack.getLapNumber());
			lapRecord.setText(RaceTrack.getLapTimes());
			
			String srecordBestTotalTime = formatter.format(SaveLoadDataStream.getBestTotalTime());
			String srecordBestLapTime = formatter.format(SaveLoadDataStream.getBestLapTime());
			bestRecods.setText("<html> Best Time: " + srecordBestTotalTime + "<br>Best Lap: " + srecordBestLapTime + "</html>");
		}
	}
	
	public static int getTimeMiliSeconds() {
		return timeMiliSeconds;
	}
}
