package launcher;
import java.io.*;
public class SaveLoadDataStream {
	private static int bestTotal = 20000;
	private static int bestLap = 4000;
	
	//checks if the save file exists, if it does load it, else create it first. 
	static void verifyLoadFile(String fileName) throws IOException {
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) { 
			load(fileName);
		}
		else {
			save(fileName);
			load(fileName);
		}
	}
	
	static void load(String fileName) throws IOException {
		DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
		bestTotal = input.readInt();
		bestLap = input.readInt();
	}
	
	//you can't save without loading or else your records get DELETED
	static void save(String fileName) throws IOException {
		DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		output.writeInt(bestTotal);
		output.writeInt(bestLap);
		output.close();
	}
	
	static int getBestTotalTime() {
		return bestTotal;
	}
	
	static int getBestLapTime() {
		return bestLap;
	}
	
	static void setBestTotalTime(int btt) {
		bestTotal = btt;
	}
	
	static void setBestLapTime(int blt) {
		bestLap = blt;
	}
}
