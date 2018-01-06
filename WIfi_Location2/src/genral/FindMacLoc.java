package genral;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import readAndWrite.WriteCsv;

/**
 * @Description The class returns all the MAC location.
 * This is the first algorithm 
 * @author Yair Ivgi
 */

public class FindMacLoc {

	private int m_Accuracy;
	private List<CSVRecord> m_records;
	
	public FindMacLoc( List<CSVRecord> records, int accuracy) {
		m_records = new ArrayList<CSVRecord>();
		m_records.addAll(records);
		this.m_Accuracy=accuracy;
	}
	
	/**
	 * Execute the algorithm for specific mac
	 * @author Idan Hollander
	 */

	public void locateMac_FromExistingMac(String mac) throws Exception {
		List <WifiSpot> points = null;
		WifiSpot point = null;
		AveragingElaborateCoordinate AE = new AveragingElaborateCoordinate();
		points = findMacsInDB(mac);
		List<WifiSpot> allPoints=new ArrayList<WifiSpot>();
		try {
		point = AE.centerOfPoints(points);
		allPoints.add(point);
		}
		catch(IndexOutOfBoundsException e) {
			throw new Exception("error: "+e.getMessage());
		}
		String folder = System.getProperty("user.dir");
		String output=folder+"\\Algorithm1.csv";
		WriteCsv w= new WriteCsv(output, allPoints);
		w.ListOfpointsFormat();
		w.close();
	}
	
//return the 3 strongest appearances of the mac in the DB
	private List <WifiSpot> findMacsInDB(String mac) throws IOException{
		List <WifiSpot> points =new ArrayList<WifiSpot>();
		for(CSVRecord record : m_records){
			int numOfSamples = Integer.parseInt(record.get("WiFi networks"));
			for (int i = 1; i <= numOfSamples ; i++) {
				String index = String.valueOf(i);
				if(record.get("MAC"+index).equals(mac)){
					WifiSpot p = new WifiSpot(record.get("ID"),record.get("MAC"+index),record.get("SSID"+index),record.get("Time"),record.get("Frequncy"+index),record.get("Signal"+index),record.get("Lat"),record.get("Lon"),record.get("Alt"));
					points.add(p);
				}
			}
		}
		Collections.sort(points, new Comparator<WifiSpot>() {
			@Override
			public int compare(WifiSpot o1, WifiSpot o2) {
				return o1.getRssi().compareTo(o2.getRssi());
			}
		});
		List <WifiSpot> result =new ArrayList<WifiSpot>(); 
		int total=points.size();
		if(total>m_Accuracy){
			total=m_Accuracy;
		}
		for (int i = 0; i < total; i++) {
			result.add(points.get(i));
		}
		return result;
	}
}
