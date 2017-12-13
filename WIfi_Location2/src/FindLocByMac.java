
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

	private  String m_filePath;

	public FindLocByMac(String filePath){
		m_filePath=filePath;
	}

	public WifiSpot locate(String mac) throws Exception{
		WifiSpot point = null;
		try {
			List <WifiSpot> points = findMacs(m_filePath,mac);
			if (points.size() > 0){
			point = centerOfPoints(points);
			}
		} catch (IOException e) {
			throw new Exception("fail : "+e.toString());
		} 
		return point;
	}

	public WifiSpot locate3(String mac1,String mac2,String mac3) throws Exception{
		///אלגוריתם 2
		return null;
	}

	private List <WifiSpot> findMacs(String filePath,String mac) throws IOException{
		File file = new File(filePath);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List <WifiSpot> points =new ArrayList<WifiSpot>();
		for(CSVRecord record : records){
			int numOfSamples = Integer.parseInt(record.get("WiFi networks"));
			for (int i = 1; i <= numOfSamples ; i++) {
				String index = String.valueOf(i);
				if(record.get("MAC"+index).equals(mac)){
					WifiSpot p = new WifiSpot(record.get("Signal"+index),record.get("Lat"),record.get("Lon"),record.get("Alt"));
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

	private  WifiSpot centerOfPoints(List <WifiSpot> points){
		List <WifiSpot> wpoints = new  ArrayList<WifiSpot>();
		for(int i=0; i<points.size(); i++){
			double tempWeigth = Double.parseDouble(points.get(i).getRssi());
			if(tempWeigth == 0){
				tempWeigth =-120;
			}
			double sigWeight = 1/Math.pow(tempWeigth, 2);
			double wLat = sigWeight * Double.parseDouble(points.get(i).getCurrentLatitude());
			double wLon = sigWeight * Double.parseDouble(points.get(i).getCurrentLongitude());
			double wAlt = sigWeight * Double.parseDouble(points.get(i).getAltitudeMeters());
			WifiSpot p = new WifiSpot(String.valueOf(sigWeight),String.valueOf(wLat),String.valueOf(wLon),String.valueOf(wAlt));
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
		WifiSpot result = new WifiSpot(null, String.valueOf(swLat),  String.valueOf(swLon),  String.valueOf(swAlt));
		return result;
	}

}
