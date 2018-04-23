package hud;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private Box box = Box.createVerticalBox();
	
	private JLabel title = new JLabel("Procrastination RACER");
	private JLabel seconds = new JLabel("", JLabel.CENTER);
	private JLabel lap = new JLabel("", JLabel.CENTER);
	private JLabel lapRecord = new JLabel("", JLabel.CENTER);
	private JLabel bestRecods = new JLabel("", JLabel.CENTER);
	
	private static int timeMiliSeconds = -1;
	private static int winLaps = 5;
	private static int randomX = ThreadLocalRandom.current().nextInt(0, 1650);
	private static int randomY = ThreadLocalRandom.current().nextInt(0, 900);
	private static SimpleDateFormat formatter = new SimpleDateFormat("m:ss.SS");
	private static String srecordBestTotalTime = formatter.format(SaveLoadDataStream.getBestTotalTime());
	private static String srecordBestLapTime = formatter.format(SaveLoadDataStream.getBestLapTime());
	
	public RaceInfoHUD() {
		this.setLayout(new BorderLayout());
		Timer timer = new Timer(1, new TimerListener());
		timer.start();
				
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		box.add(title);
		box.add(Box.createVerticalStrut(20));
		
		seconds.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		box.add(seconds);
		box.add(Box.createVerticalStrut(8));
		
		lap.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		box.add(lap);
		box.add(Box.createVerticalStrut(8));
		
		lapRecord.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		box.add(lapRecord);
		box.add(Box.createGlue());
		
		bestRecods.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		box.add(bestRecods);
		
		add(box, BorderLayout.CENTER);
	}
	
	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (RaceTrack.getLapNumber() != winLaps && timeMiliSeconds>=0)
				timeMiliSeconds++; //TODO stop the timer, not just my counter. Does 'timer.stop();' work?
			if (timeMiliSeconds%5000==0) {
		    	randomX = ThreadLocalRandom.current().nextInt(0, getWidth());
		    	randomY = ThreadLocalRandom.current().nextInt(0, getHeight());
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
