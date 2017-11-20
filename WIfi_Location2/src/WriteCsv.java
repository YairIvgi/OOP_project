import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class WriteCsv {
	private FileWriter fw;
	private PrintWriter outs;

	public  WriteCsv(String outputPath) throws Exception{
		try {
			fw = new FileWriter(outputPath);
			outs = new PrintWriter(fw);
			String line= "Time,ID,Lat,Lon,Alt,WiFi networks";
			for (int i = 1; i <= 10; i++){
				line+=",SSID"+i;
				line+=",MAC"+i;
				line+=",Frequncy"+i;
				line+=",Signal"+i;
			}
			writeLine(line);
		} catch (Exception e) {
			throw new Exception("Writing to file has failde: "+e);
		}
	}

	public void write(List<RawData> data){
		for(int i=0; i< data.size(); i++){
			RawData raw = data.get(i);
			WifiSpot spot = raw.getSamples().get(0);
			int namberOfWifi=raw.getSamples().size();
			String line = null;
			line=spot.getFirstSeen();					//"Time"
			line+=","+spot.getId();						//"ID"
			line+=","+spot.getCurrentLatitude();		//"Lat"
			line+=","+spot.getCurrentLongitude();		//"Lon"
			line+=","+spot.getAltitudeMeters();			//"Alt"
			line+=","+String.valueOf(namberOfWifi);		//"WiFi networks"
			
			for (int j = 0; j < raw.getSamples().size(); j++) {
				WifiSpot wS = raw.getSamples().get(j);
				line+=","+wS.getSsid();				//"SSID"
				line+=","+wS.getMac();				//"MAC"
				line+=","+wS.getChannel();			//"Frequency/channel"
				line+=","+wS.getRssi();				//"Signal/RSSI"			
				}
			if(namberOfWifi<10){
				for (int q = namberOfWifi; q < 10; q++) {
					line+=",,,";
				}
			}
			writeLine(line);
		}
	}

	public void close() throws Exception{
		try {
			outs.close();
			fw.close();
		} catch (Exception e) {
			throw new Exception("Closing has failde: "+e);
		}
	}

	private void writeLine(String line){
		outs.println(line);
	}

}
