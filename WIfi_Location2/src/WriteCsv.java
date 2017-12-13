import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class WriteCsv {
	/**
	 * @author Yair Ivgi 
	 * This class writes the data of all the WifiSpot into a .csv format file.
	 */
	private FileWriter fw;
	private PrintWriter outs;
	static private String outputPath;
	/**
	 * @author Yair Ivgi 
	 * The  builder WriteCsv sets the first line of headers.
	 */
	public  WriteCsv(String outputPath) throws Exception{
		try {
			WriteCsv.outputPath=outputPath;
			fw = new FileWriter(WriteCsv.outputPath);
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
	///////TODO add explanations 
//	public   WriteCsv(List<CSVRecord> records) throws Exception{
//		try {
//			outputPath=outputPath.replace(".csv", "afterSort.csv");
//			fw = new FileWriter(outputPath);
//			WriteCsv wc =new WriteCsv(outputPath);
//			outs = new PrintWriter(fw);
//			for(CSVRecord record : records){
//				writeLine(record.toString());
//			}
//			
//		} catch (Exception e) {
//			throw new Exception("Writing to file has failde: "+e);
//		}
//	}
	
	/**
	 * @author Yair Ivgi 
	 * The method write all the points in .csv format
	 */
	public void writeFormat(List<RawData> data){
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
	
	/**
	 * @author Yair Ivgi 
	 * close the file 
	 */
	public void close() throws Exception{
		try {
			outs.close();
			fw.close();
		} catch (Exception e) {
			throw new Exception("Closing has failde: "+e);
		}
	}
	/**
	 * @author Yair Ivgi 
	 * prints the line 
	 */
	private void writeLine(String line){
		outs.println(line);
	}

}
