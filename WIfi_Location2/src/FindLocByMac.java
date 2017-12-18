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
 * @author Yair Ivgi
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
		for(CSVRecord record : records){
			WifiSpot point = findInDataBase(record);
			CsvRecordPoint crp = new CsvRecordPoint(record, point);
			dataList.add(crp);		
		}
		printCsv(dataList);
	}

	private WifiSpot findInDataBase(CSVRecord record) throws IOException{
		File file = new File(m_DataBaseFilePath);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List <WifiSpot> points = new ArrayList<WifiSpot>();
		for(CSVRecord DBrecord : records){
			double PI=lineResemblance(DBrecord,record);
			WifiSpot point = new WifiSpot(String.valueOf(PI), DBrecord.get("Lat"), DBrecord.get("Lon"), DBrecord.get("Alt"));
			points.add(point);
		}
		Collections.sort(points, new Comparator<WifiSpot>() {
			@Override
			public int compare(WifiSpot o1, WifiSpot o2) {
				return o1.getRssi().compareTo(o2.getRssi());
			}
		});
		List <WifiSpot> result =new ArrayList<WifiSpot>(); 
		for (int i = 0; i < m_Accuracy; i++) {
			result.add(points.get(i));
		}
		AveragingElaborateCoordinate AV = new AveragingElaborateCoordinate();
		return AV.centerOfPoints(result);
	}

	private Double lineResemblance(CSVRecord DBrecord,CSVRecord record){
		int rNumOfSamples = Integer.parseInt(record.get("WiFi networks"));
		int dbNumOfSamples = Integer.parseInt(record.get("WiFi networks"));
		double arr[] =new double[rNumOfSamples];
		boolean existsSuchMac;
		for (int i = 1; i <= rNumOfSamples; i++){
			existsSuchMac= false;
			for (int j = 1; j <= dbNumOfSamples; j++){
				if(record.get("MAC"+String.valueOf(i)).equals(DBrecord.get("MAC"+String.valueOf(j)))){
					arr[i-1] = clacPercentage(record.get("Signal"+String.valueOf(i)),DBrecord.get("Signal"+String.valueOf(j)));
					existsSuchMac= true;
				}
			}
			if(!existsSuchMac){
				arr[i-1] = 0.1;
			}
		}
		double resemblance=0;
		for (int i = 0; i < arr.length; i++) {
			resemblance*=arr[i];
		}
		return resemblance;
	}

	private void printCsv(List<CsvRecordPoint> dataList) throws Exception{
		String outputPath = m_filePath.replace(".csv", "Estimated_Location.csv");
		WriteCsv wc = new WriteCsv(outputPath);
		wc.estimatedLocationFormat(dataList);
		wc.close();
	}
	
	private static double clacPercentage(String strA,String strB){
		double x = Double.parseDouble(strA);
		double y;
		if(!strB.equals("") ){
			y = Double.parseDouble(strB);
		}else{
			y=-120;
		}
		double result = Math.abs(Math.abs(x)-Math.abs(y));
		if(result==0){
			result=3;
		}
		return result/Math.max(x, y);
	}
}
