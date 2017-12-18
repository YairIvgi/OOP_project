import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

/** 
 * This class writes the data of all the WifiSpot into a .csv format file.
 * @author Yair Ivgi
 */

public class WriteCsv {

	private FileWriter fw;
	private PrintWriter outs;
	private List <WifiSpot> m_Points;
	static private String outputPath;

	/** 
	 * The contractor sets the first line of headers.
	 * @throws Exception
	 * @author Yair Ivgi
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
	
	/** 
	 * The contractor sets the first line of headers in List Of points Format.
	 * @throws Exception
	 * @author Yair Ivgi
	 */
	
	public  WriteCsv(String outputPath,List <WifiSpot> Points) throws Exception{
		try {
			m_Points= new ArrayList<WifiSpot>();
			m_Points.addAll(Points);
			WriteCsv.outputPath=outputPath;
			fw = new FileWriter(WriteCsv.outputPath);
			outs = new PrintWriter(fw);
			String line= ",ID,Mac,SSID,Time,Frequncy,Signal,Lat,Lon,Alt";
			writeLine(line);
		} catch (Exception e) {
			throw new Exception("Writing to file has failde: "+e);
		}
	}
	
	/**
	 * The method write all the points in .csv format.
	 * @author Yair Ivgi
	 */
	
	public void ListOfpointsFormat() throws Exception{
		if(m_Points == null){
			throw new Exception("The list of is empty.");
		}
		for (int i = 0; i < m_Points.size(); i++){
			String line;
			line=String.valueOf(i);
			line+=","+m_Points.get(i).getId();
			line+=","+m_Points.get(i).getMac();
			line+=","+m_Points.get(i).getSsid();
			line+=","+m_Points.get(i).getTime();
			line+=","+m_Points.get(i).getChannel();
			line+=","+m_Points.get(i).getRssi();
			line+=","+m_Points.get(i).getCurrentLatitude();
			line+=","+m_Points.get(i).getCurrentLongitude();
			line+=","+m_Points.get(i).getAltitudeMeters();
			writeLine(line);
		}
	}
	

	/**
	 * The method write all the points in .csv format of 46 columns.
	 * @author Yair Ivgi
	 */

	public void dataBaseFormat(List<RawData> data){
		for(int i=0; i< data.size(); i++){
			RawData raw = data.get(i);
			WifiSpot spot = raw.getSamples().get(0);
			int namberOfWifi=raw.getSamples().size();
			String line = null;
			line=spot.getTime();					//"Time"
			line+=","+spot.getId();						//"ID"
			line+=","+spot.getCurrentLatitude();		//"Lat"
			line+=","+spot.getCurrentLongitude();		//"Lon"
			line+=","+spot.getAltitudeMeters();			//"Alt"
			line+=","+String.valueOf(namberOfWifi);		//"WiFi networks"

			for (int j = 0; j < namberOfWifi; j++) {
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
	public void estimatedLocationFormat(List<CsvRecordPoint> dataList){
		for(CsvRecordPoint record : dataList) {
			int namberOfWifi=Integer.parseInt(record.m_record.get("WiFi networks"));
			String line = null;
			line=record.m_record.get("Time");						//"Time"
			line+=","+record.m_record.get("ID");					//"ID"
			line+=","+record.m_point.getCurrentLatitude();			//"Lat"
			line+=","+record.m_point.getCurrentLongitude();			//"Lon"
			line+=","+record.m_point.getAltitudeMeters();			//"Alt"
			line+=","+String.valueOf(namberOfWifi);					//"WiFi networks"

			for (int i = 1; i <= namberOfWifi; i++) {
				line+=","+record.m_record.get("SSID"+i);			//"SSID"
				line+=","+record.m_record.get("MAC"+i);				//"MAC"
				line+=","+record.m_record.get("Frequncy"+i);		//"Frequency/channel"
				line+=","+record.m_record.get("Signal"+i);			//"Signal/RSSI"			
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
	 * Close the file.
	 * @throws Exception
	 * @author Yair Ivgi 
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
	 * Prints the line.
	 * @author Yair Ivgi 
	 */

	void writeLine(String line){
		outs.println(line);
	}

}
