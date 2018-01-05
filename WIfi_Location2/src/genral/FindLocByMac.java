package genral;

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

import readAndWrite.WriteCsv;

/**
 * @Description The class returns estimated location if you don't have GPS reception, from your previous data.
 * This is the second algorithm 
 * @author Yair Ivgi and Idan Hollander
 */


public class FindLocByMac {

	private int m_Accuracy;
	private List<CSVRecord> m_records;
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
	
	public FindLocByMac(List<CSVRecord> records,int accuracy){	
		m_Accuracy = accuracy;
		m_records = new ArrayList<CSVRecord>();
		m_records.addAll(records);
	}
	
	public void estimatedLoc_FromString(String line) throws Exception {
		String folder = System.getProperty("user.dir");
		String output=folder+"\\OneStringForAlgorithm2.csv";
		WriteCsv wc = new WriteCsv(output);
		wc.writeLine(line);
		wc.close();
		File file = new File(output);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List<CSVRecord> oneLineRecord  = new ArrayList<CSVRecord>();
		for(CSVRecord record: records){
			oneLineRecord.add(record);
		}
		estimatedLoc(oneLineRecord);
	}
	
	public void estimatedLoc_FromMacs(String mac1, String signal1, String mac2, String signal2, String mac3, String signal3) throws Exception {
		WifiSpot wf1=new WifiSpot("user", mac1, "Ariel_University", "2018-01-02 17:42:08", "15", signal1, "?", "?", "?");
		WifiSpot wf2=new WifiSpot("user", mac2, "Ariel_University", "2018-01-02 17:42:08", "11", signal2, "?", "?", "?");
		WifiSpot wf3=new WifiSpot("user", mac3, "Ariel_University", "2018-01-02 17:42:08", "10", signal3, "?", "?", "?");
		RawData rd= new RawData();
		rd.add(wf1);
		rd.add(wf2);
		rd.add(wf3);
		List<RawData> result = new ArrayList<RawData>();
		result.add(rd);
		String folder = System.getProperty("user.dir");
		String output=folder+"\\threeMacForAlgorithm2.csv";
		WriteCsv wc =new WriteCsv(output);
		wc.dataBaseFormat(result);
		wc.close();
		File file = new File(output);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List<CSVRecord> records2  = new ArrayList<CSVRecord>();
		for(CSVRecord record: records){
			records2.add(record);
		}
		estimatedLoc(records2);
	}

	public void estimatedLoc(List<CSVRecord> noGps) throws Exception{
		List<CsvRecordPoint> dataList = new ArrayList<CsvRecordPoint>(); 
		for(int i=0;i<noGps.size();i++){					
			WifiSpot point = findInDataBase(m_records,noGps.get(i));
			CsvRecordPoint crp = new CsvRecordPoint(noGps.get(i), point);
			dataList.add(crp);
		}
		String folder = System.getProperty("user.dir");
		String output=folder+"\\Algorithm2.csv";
		printCsv(dataList,output);			
	}
	
//searching in database file
	private WifiSpot findInDataBase(List<CSVRecord> records,CSVRecord record) throws IOException{
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
		//scanning samples in file
		for (int i = 1; i <= rNumOfSamples; i++){
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
				arr[i-1] = clacPercentage(record.get("Signal"+String.valueOf(i)),String.valueOf(NO_SIG));
			}
		}
		double resemblance=1;

		for (int i = 0; i < arr.length; i++) {
			resemblance*=arr[i];
		}
			return resemblance;
	}
	//writing csv
	private void printCsv(List<CsvRecordPoint> dataList,String filePath) throws Exception{
		String outputPath = filePath.replace(".csv", "Estimated_Location.csv");
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
