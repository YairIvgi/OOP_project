
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class FindLocByMac {

	private  String m_filePath = null;
	private	 String m_folderPath = null;

	public void locateMac_FromFile(String filePath) throws Exception{
		m_filePath = filePath; 
		m_folderPath = filePath.replace(".csv","");
		locate();
	}

	public void locateMac_FromFolder(String folderPath) throws Exception{
		m_folderPath = folderPath;
		RawCsvReader folder=new RawCsvReader();
		try {
			folder.readFolder(folderPath);
		} catch (Exception e) {
			System.err.println("faild: "+e.toString());
		}
		m_filePath = folder.getOutputFile();
		m_folderPath+="//newData//";
		locate();
	}

	private void locate() throws Exception{
		List <WifiSpot> allPoints = new ArrayList<WifiSpot>();		
		File file = new File(m_filePath);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		//scan every line in the input
		for(CSVRecord record : records){	
			int numOfSamples = Integer.parseInt(record.get("WiFi networks"));
			//check all mac's in line
			for (int i = 1; i <= numOfSamples ; i++) {
				List <WifiSpot> points = null;
				String mac = record.get("MAC"+String.valueOf(i));
				WifiSpot point = null;
				//check if we already have that mac.
				if(allPoints.size() == 0){
					points = findMacsInDB(mac);
					point = centerOfPoints(points);
					allPoints.add(point);
				}
				boolean flage=false;
				for (int j=0; j < allPoints.size();j++) {
					if(allPoints.get(j).getMac().equals(mac)){
						flage=true;
					}
				}
				if(!flage){
					points = findMacsInDB(mac);
					if (points.size() >= 0){
						point = centerOfPoints(points);
						allPoints.add(point);			
					}
				}
			}
		}
		WriteCsv W = new WriteCsv(m_folderPath+"Mac_estimated_Loc.csv", allPoints);
		W.ListOfpointsFormat();
		W.close();
	}
	
//return the 3 strongest appearances of the mac in the DB
	private List <WifiSpot> findMacsInDB(String mac) throws IOException{
		File file = new File(m_filePath);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List <WifiSpot> points =new ArrayList<WifiSpot>();
		for(CSVRecord record : records){
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
		if(total>3){
			total=3;
		}
		for (int i = 0; i < total; i++) {
			result.add(points.get(i));
		}
		return result;
	}
	
//return the center point of the 3 appearances of the mac
	private  WifiSpot centerOfPoints(List <WifiSpot> points){
		List <WifiSpot> wpoints = new  ArrayList<WifiSpot>();
		String id = points.get(0).getId();
		String ssid = points.get(0).getSsid();
		String time = points.get(0).getTime();
		String channel = points.get(0).getChannel();
		String mac = points.get(0).getMac();
		String rssi = points.get(0).getRssi();
		for(int i=0; i<points.size(); i++){
			double tempWeigth = Double.parseDouble(points.get(i).getRssi());
			if(tempWeigth == 0){
				tempWeigth =-120;
			}
			double sigWeight = 1/Math.pow(tempWeigth, 2);
			double wLat = sigWeight * Double.parseDouble(points.get(i).getCurrentLatitude());
			double wLon = sigWeight * Double.parseDouble(points.get(i).getCurrentLongitude());
			double wAlt = sigWeight * Double.parseDouble(points.get(i).getAltitudeMeters());
			WifiSpot p = new WifiSpot(id,mac,ssid,time,channel,String.valueOf(sigWeight),String.valueOf(wLat),String.valueOf(wLon),String.valueOf(wAlt));
			wpoints.add(p);
		}
		double sigSWeight = 0, swLat = 0, swLon = 0, swAlt = 0;
		for (int i = 0; i < wpoints.size(); i++) {
			sigSWeight +=Double.parseDouble(wpoints.get(i).getRssi());
			swLat += Double.parseDouble(wpoints.get(i).getCurrentLatitude());
			swLon += Double.parseDouble(wpoints.get(i).getCurrentLongitude());
			swAlt += Double.parseDouble(wpoints.get(i).getAltitudeMeters());
		}
		if(sigSWeight != 0){
			swLat = swLat / sigSWeight;
			swLon = swLon / sigSWeight;
			swAlt = swAlt / sigSWeight;
		} 
		WifiSpot result = new WifiSpot(id,mac,ssid,time,channel,rssi, String.valueOf(swLat), String.valueOf(swLon),  String.valueOf(swAlt));
		return result;
	}
}
