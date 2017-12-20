
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

/**
 * @Description The class returns all the MAC location.
 * This is the first algorithm 
 * @author Yair Ivgi
 */

public class FindMacLoc {

	private  String m_filePath = null;
	private	 String m_folderPath = null;
	private int m_Accuracy;

	/**
	 * Receive organized data from file 
	 * @author Yair Ivgi
	 */
	
	public void locateMac_FromFile(String filePath,int accuracy) throws Exception{
		m_filePath = filePath; 
		m_folderPath = filePath.replace(".csv","");
		m_Accuracy=accuracy;
		locate();
	}

	/**
	 * Receive raw data from folder 
	 * @author Yair Ivgi
	 */
	
	public void locateMac_FromFolder(String folderPath,int accuracy) throws Exception{
		m_folderPath = folderPath;
		m_Accuracy=accuracy;
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
				AveragingElaborateCoordinate AE = new AveragingElaborateCoordinate();
				if(allPoints.size() == 0){
					points = findMacsInDB(mac);
					point = AE.centerOfPoints(points);
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
						point = AE.centerOfPoints(points);
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
		if(total>m_Accuracy){
			total=m_Accuracy;
		}
		for (int i = 0; i < total; i++) {
			result.add(points.get(i));
		}
		return result;
	}
	
//return the center point of the 3 appearances of the mac

}
