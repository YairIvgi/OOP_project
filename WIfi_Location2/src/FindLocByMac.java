
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

	public void locateMac_FromFile(String filePath,int x) throws Exception{
		m_filePath = filePath; 
		m_folderPath = filePath.replace(".csv","");
		locate(x);
	}

	public void locateMac_FromFolder(String folderPath,int x) throws Exception{
		m_folderPath = folderPath;
		RawCsvReader folder=new RawCsvReader();
		try {
			folder.readFolder(folderPath);
		} catch (Exception e) {
			System.err.println("faild: "+e.toString());
		}
		m_filePath = folder.getOutputFile();
		m_folderPath+="//newData//";
		locate(x);
	}

	public WifiSpot locate3(String mac1,String mac2,String mac3,int x) throws Exception{
		///׳�׳�׳’׳•׳¨׳™׳×׳� 2
		return null;
	}
	private void locate(int x) throws Exception{
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
					points = findMacsInDB(mac,x);
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
					points = findMacsInDB(mac,x);
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
	
//return the X strongest appearances of the mac in the DB
	private List <WifiSpot> findMacsInDB(String mac,int x) throws IOException{
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
		if(total>x){
			total=x;
		}
		for (int i = 0; i < total; i++) {
			result.add(points.get(i));
		}
		return result;
	}

//להתייחס למקרה בו התקיות זהות
	private List <List <WifiSpot>> findMacs3(String mac1,String mac2,String mac3,int x) throws IOException{
		File file = new File(m_filePath);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List <List<WifiSpot>> points =new ArrayList<List<WifiSpot>>();
		for(CSVRecord record : records){
			int numOfSamples = Integer.parseInt(record.get("WiFi networks"));
			List<WifiSpot> macs=new ArrayList<WifiSpot>();
			for (int i = 1; i <= numOfSamples ; i++) {
				String index = String.valueOf(i);
				if(record.get("MAC"+index).equals(mac1)||record.get("MAC"+index).equals(mac2)||record.get("MAC"+index).equals(mac3)){
					WifiSpot p = new WifiSpot(record.get("ID"),record.get("MAC"+index),record.get("SSID"+index),record.get("Time"),record.get("Frequncy"+index),record.get("Signal"+index),record.get("Lat"),record.get("Lon"),record.get("Alt"));
					macs.add(p);
				}
			}
			if(!macs.isEmpty()) {
				points.add(macs);
			}
		}
		Collections.sort(points, new Comparator<List<WifiSpot>>() {
			@Override
			public int compare(List<WifiSpot> o1, List<WifiSpot> o2) {
				double sum1=0;
				double sum2=0;
				for(int i=0;i<o1.size();i++) {
					sum1+=Double.parseDouble(o1.get(i).getRssi());
				}
				if(o1.size()==2) {
					sum1+=-120;
				}
				if(o1.size()==1) {
					sum1+=-240;
				}
				for(int i=0;i<o2.size();i++) {
					sum2+=Double.parseDouble(o2.get(i).getRssi());
				}
				if(o1.size()==2) {
					sum2+=-120;
				}
				if(o1.size()==1) {
					sum2+=-240;
				}
				if(sum1<sum2) {
					return -1;
				}
				else if(sum1>sum2) {
					return 1;
				}
				return 0;
			}
		});
		List <List<WifiSpot>> result =new ArrayList<List<WifiSpot>>(); 
		int total=points.size();
		if(total>x){
			total=x;
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
