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
	private static final double POWER = 2;
	private static final double NORM = 10000;
	private static final double SIG_DIF = 0.4;
	private static final double MIN_DIF = 3;
	private static final double NO_SIG = -120;
	private static final double DIFF_NO_SIG = 100;

	/**
	 * Contractor receive filePath and the accuracy required
	 * @author Yair Ivgi and Idan Hollander
	 */
	
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
		for(CSVRecord record : records){					
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
		for(CSVRecord DBrecord : records){				//Data Base Records
			double PI	=	lineResemblance(DBrecord,record);
			WifiSpot point = new WifiSpot(String.valueOf(PI), DBrecord.get("Lat"), DBrecord.get("Lon"), DBrecord.get("Alt"));
			points.add(point);
		}
		//if there is no mac in database that equals one mac in the row in the noGPS file
		if(!ifAnyNoMacInDB(points)) {		
			String id = points.get(0).getId();
			String ssid = points.get(0).getSsid();
			String time = points.get(0).getTime();
			String channel = points.get(0).getChannel();
			String mac = points.get(0).getMac();
			String rssi = points.get(0).getRssi();
			WifiSpot result = new WifiSpot(id,mac,ssid,time,channel,rssi,null,null,null);
			return result;
		}
		Collections.sort(points, new Comparator<WifiSpot>() {
			@Override
			public int compare(WifiSpot o1, WifiSpot o2) {
				return o1.getRssi().compareTo(o2.getRssi());
			}
		});
		int index =points.size()-1;
		while(index >=0 && points.get(index).getRssi().contains("E")){
			index--;
		}
		List <WifiSpot> result =new ArrayList<WifiSpot>(); 
		if(m_Accuracy > 10){
			m_Accuracy = 5;
		}
		for (int i = 0; i < m_Accuracy; i++) {
//			if we have  more regular numbers that not written with expo than than numbers we equals with
			if(index> m_Accuracy) {
				result.add(points.get(index-i));
			}
			else
				result.add(points.get(i));
		}
		AveragingElaborateCoordinate AV = new AveragingElaborateCoordinate();
		return AV.centerWeightOfPoints(result);
	}
	
	//Check if there is a mac in database that equals one mac in the row in the noGPS file
	private boolean ifAnyNoMacInDB(List<WifiSpot>	points) {
		for(int i=1; i < points.size(); i++) {
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
//				clacPercentage(record.get("Signal"+String.valueOf(i)),"-120");
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
		if(y !=NO_SIG){
			diff = Math.abs(Math.abs(x)-Math.abs(y));
		}else{
			diff = DIFF_NO_SIG;
		}
		
		if(diff < MIN_DIF){
			diff = MIN_DIF;
		}
		return NORM/(Math.pow(diff, SIG_DIF)*Math.pow(x,POWER));
	}
}
