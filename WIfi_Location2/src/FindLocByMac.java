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
 * @Description The class returns estimated location if you don't have GPS reception, from your previous data.
 * This is the second algorithm 
 * @author Yair Ivgi and Idan Hollander
 */


public class FindLocByMac {

	private	String m_filePath;
	private String m_DataBaseFilePath;
	private int m_Accuracy;

	public FindLocByMac(String DataBaseFilePath,int accuracy){
		m_DataBaseFilePath = DataBaseFilePath;
		m_Accuracy = accuracy;
	}

	public void estimatedLoc_FromFile(String filePath) throws Exception{
		m_filePath = filePath;
		File file = new File(m_filePath);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List<CsvRecordPoint> dataList = new ArrayList<CsvRecordPoint>(); 
		for(CSVRecord record : records){//File Records
			WifiSpot point = findInDataBase(record);
			CsvRecordPoint crp = new CsvRecordPoint(record, point);
			dataList.add(crp);		
		}
		printCsv(dataList);
	}
	//searching in database file
	private WifiSpot findInDataBase(CSVRecord record) throws IOException{
		File file = new File(m_DataBaseFilePath);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List <WifiSpot> points = new ArrayList<WifiSpot>();
		for(CSVRecord DBrecord : records){//Data Base Records
			double PI=lineResemblance(DBrecord,record);
			WifiSpot point = new WifiSpot(String.valueOf(PI), DBrecord.get("Lat"), DBrecord.get("Lon"), DBrecord.get("Alt"));
			points.add(point);
		}
		if(!ifAnyMacInDB(points)) {//if there is no mac in database that equals one mac in the row in the noGPS file
			String id = points.get(0).getId();
			String ssid = points.get(0).getSsid();
			String time = points.get(0).getTime();
			String channel = points.get(0).getChannel();
			String mac = points.get(0).getMac();
			String rssi = points.get(0).getRssi();
			WifiSpot result = new WifiSpot(id,mac,ssid,time,channel,rssi,"Not Found","Not Found","Not Found");
			return result;
		}
		Collections.sort(points, new Comparator<WifiSpot>() {
			@Override
			public int compare(WifiSpot o1, WifiSpot o2) {
				return o1.getRssi().compareTo(o2.getRssi());
			}
		});
		int index = points.size()-1;
		while(index>=0&&points.get(index).getRssi().contains("E")) {//if we have some numbers that written with expo
			index--;
		}
		List <WifiSpot> result =new ArrayList<WifiSpot>(); 
		for (int i = 0; i < m_Accuracy; i++) {
			if(index> m_Accuracy) {//if we have  more regular numbers that not written with expo than than numbers we equals with
				result.add(points.get(index-i));
			}
			else
				result.add(points.get(i));
		}
		AveragingElaborateCoordinate AV = new AveragingElaborateCoordinate();
		return AV.centerWeightOfPoints(result);
	}
	//Check if there is a mac in database that equals one mac in the row in the noGPS file
	public boolean ifAnyMacInDB(List<WifiSpot>points) {
		for(int i=1;i<points.size();i++) {
			if(!points.get(0).getRssi().equals(points.get(i).getRssi())) {
				return true;
			}
		}
		return false;
	}
	//line scanning
	private Double lineResemblance(CSVRecord DBrecord,CSVRecord record){
		int rNumOfSamples = Integer.parseInt(record.get("WiFi networks"));
		int dbNumOfSamples = Integer.parseInt(DBrecord.get("WiFi networks"));
		double arr[] =new double[rNumOfSamples];
		boolean existsSuchMac;
		for (int i = 1; i <= rNumOfSamples; i++){//scanning samples in file
			existsSuchMac= false;
			for (int j = 1; j <= dbNumOfSamples; j++){//scanning samples in DB
				//check if file and data base has same MAC in scanned line
				if(record.get("MAC"+String.valueOf(i)).equals(DBrecord.get("MAC"+String.valueOf(j)))){
					arr[i-1] = clacPercentage(record.get("Signal"+String.valueOf(i)),DBrecord.get("Signal"+String.valueOf(j)));
					existsSuchMac= true;
				}
			}
			//check if existing mac not found
			if(!existsSuchMac){
				arr[i-1] = clacPercentage(record.get("Signal"+String.valueOf(i)),"-120");
			}
		}
		double resemblance=1;
		for (int i = 0; i < arr.length; i++) {
			resemblance*=arr[i];
		}
		return resemblance;
	}
	//writing csv
	private void printCsv(List<CsvRecordPoint> dataList) throws Exception{
		String outputPath = m_filePath.replace(".csv", "Estimated_Location.csv");
		WriteCsv wc = new WriteCsv(outputPath);
		wc.estimatedLocationFormat(dataList);
		wc.close();
	}

	//calculate weight
	private static double clacPercentage(String strA,String strB){
		double x = Double.parseDouble(strA);
		double y = Double.parseDouble(strB);
		double diff;
		//check if y is signal
		if(y!=-120 ){
			diff = Math.abs(Math.abs(x)-Math.abs(y));
		}else{
			diff=100;
		}
		if(diff<3){
			diff=3;
		}
		double result=10000/(Math.pow(diff, 0.4)*Math.pow(x, 2));
		return result;
	}
}
