package track;

import java.awt.Color;
import java.awt.Image;

/*****************************************************
 * Changes the following for the reacetrack object:
 * Car image
 * racetrack image
 * middle out of bounds color.
******************************************************/
public class TrackTwo extends RaceTrack{
	public TrackTwo(Image overlay, Image car, Image track, Color middle) {
		startOverlay = overlay;
		carImage = car;
		raceTrackImage = track;
		middleBoundry = middle;
	}
}
