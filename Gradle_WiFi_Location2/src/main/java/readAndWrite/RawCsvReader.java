package readAndWrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import genral.RawData;
import genral.WifiSpot;

/** 
 * @information This class reads all the .csv files using commons-csv-1.5.jar .
 * the method readCsv reads each file and organize all the data by time. 
 * @author Yair Ivgi
 */

public class RawCsvReader {

	private String outputFile;
	/** 
	 * Reads all the files in the specific folder and writs all the file names into an array.
	 * @throws Exception
	 * @author Yair Ivgi
	 */

	public  void readFolder(String folderPath) throws Exception{
		File dir = new File(folderPath+"\\newData");
		 dir.mkdir();
		outputFile= dir.getPath()+"\\DATA.csv";
		List<RawData> data;
		File folder = new File(folderPath);	
		File[] listOfFiles = folder.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String filename){
				return filename.endsWith(".csv"); 
			}
		} );		
		if( listOfFiles == null ){
			throw new Exception("The folder "+folderPath+" does not exist");
		}
		WriteCsv writer = new WriteCsv(outputFile);
		for (int i = 0; i < listOfFiles.length; i++) {
			data = new ArrayList<RawData>();
			readCsv(listOfFiles[i],data);
			data = RawData.organizeData(data);
			writer.dataBaseFormat(data);
		}
		writer.close();
	}

	/** 
	 * Reads each file and organize all the data by time order.
	 * @throws Exception 
	 * @author Yair Ivgi
	 */

	public List<RawData> readCsv(File filePath,List<RawData> data) throws Exception{
		FileReader in;
		try {			
			in = new FileReader(filePath);
			BufferedReader br =new BufferedReader(in);
			String line=br.readLine();
			String value=null;
			int start = line.indexOf("model=");
			if(start>=0){
				start+="model=".length();
				int end = line.indexOf(",",start);
				if(end>0){
					value= line.substring(start, end);
				}
			}
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(br);	
			String lastTime=null;
			RawData r=null;
			for (CSVRecord record : records) {
				String currentTime = record.get("FirstSeen");
				if(currentTime == null || !record.get("Type").equals("WIFI")){
					continue;
				}
				if(lastTime==null || !lastTime.equals(currentTime)){
					lastTime=currentTime;
					r= new RawData();
					data.add(r);
				}
				WifiSpot point=new WifiSpot(value , record.get("MAC"), record.get("SSID"), currentTime, record.get("Channel"), record.get("RSSI"), record.get("CurrentLatitude"), record.get("CurrentLongitude"), record.get("AltitudeMeters"));
				r.add(point);
			}
			br.close();
		} catch (FileNotFoundException e) {
			throw new Exception("Error reading file\n" + e);
		} catch (IOException ex) {
			throw new Exception("Error reading file\n" + ex);
		}
		return data;
	}

	/** 
	 * Returns the output file name.
	 * @author Yair Ivgi
	 */

	public String getOutputFile() {
		return outputFile;
	}
}
