package hud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;
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
	
	private static int timeMiliSeconds = -1;
	private static int winLaps = 5;
	private static int randomX = ThreadLocalRandom.current().nextInt(0, 1650);
	private static int randomY = ThreadLocalRandom.current().nextInt(0, 900);
	static SimpleDateFormat formatter = new SimpleDateFormat("m:ss.SS");
	private static String srecordBestTotalTime = formatter.format(SaveLoadDataStream.getBestTotalTime());
	private static String srecordBestLapTime = formatter.format(SaveLoadDataStream.getBestLapTime());
	
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
			if (RaceTrack.getLapNumber() != winLaps && timeMiliSeconds>=0)
				timeMiliSeconds++; //TODO stop the timer, not just my counter. Does 'timer.stop();' work?
			if (timeMiliSeconds%5000==0) {
		    	randomX = ThreadLocalRandom.current().nextInt(0, 1650);
		    	randomY = ThreadLocalRandom.current().nextInt(0, 900);
			}
			String result = formatter.format(timeMiliSeconds);
			seconds.setText(result);
			lap.setText("Lap: "+RaceTrack.getLapNumber());
			lapRecord.setText(RaceTrack.getLapTimes());
			bestRecods.setText("<html> Best Time: " + srecordBestTotalTime + "<br>Best Lap: " + srecordBestLapTime + "</html>");
		}
	}
	
	public static int getTimeMiliSeconds() {
		return timeMiliSeconds;
	}
	
	public static void setTimeMiliSeconds(int newTimeMiliSeconds) {
		timeMiliSeconds = newTimeMiliSeconds;
	}
	
	public static int getRandomXCoord() {
		return randomX;
	}
	
	public static int getRandomYCoord() {
		return randomY;
	}
	
	public static String getBestTotalTime() {
		return srecordBestTotalTime;
	}
	
	public static void setBestTotalTime(int totalTime) {
		srecordBestTotalTime = formatter.format(totalTime);
	}
	
	public static String getBestLapTime() {
		return srecordBestLapTime;
	}
	
	public static void setBestLapTime(int lapTime) {
		srecordBestLapTime = formatter.format(lapTime);
	}
}
