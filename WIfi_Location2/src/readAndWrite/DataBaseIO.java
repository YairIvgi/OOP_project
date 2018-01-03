package readAndWrite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.boehn.kmlframework.kml.Document;
import org.boehn.kmlframework.kml.Kml;
import org.boehn.kmlframework.kml.KmlException;
import org.boehn.kmlframework.kml.Placemark;
import org.boehn.kmlframework.kml.TimePrimitive;
import org.boehn.kmlframework.kml.TimeStamp;



/**
 * @information This class reads form .csv and writes to .kml .
 * @author Yair Ivgi and Idan Holander
 */


public class DataBaseIO {

	/** 
	 * The method readCsv reads the modified .csv file and sends the data to the filters.
	 * @throws Exception
	 * @author Yair Ivgi
	 */

	public List<CSVRecord> readData(String fileName) throws Exception{
		try {
			File file = new File(fileName);
			Reader in = new FileReader(file);
			List<CSVRecord> result = new ArrayList<CSVRecord>();
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				result.add(record);
			}
			return	result;
		} catch (Exception e) {
			throw new Exception("Error reading file\n" + e);		
		}
	}	

	/**
	 * Check if the name is recognized in kml format.
	 * @author Yair Ivgi
	 */

	private String chackSsid(String name){
		StringBuffer validSsid = new StringBuffer();
		char c;
		for (int i = 0; i < name.length(); i++) {
			c=name.charAt(i);
			if(Character.isAlphabetic(c) || Character.isDigit(c)){  
				validSsid.append(c);
			}else{ 
				validSsid.append('_');
			}
		}
		return validSsid.toString();
	}

	/**
	 * Takes the strongest signal in each time timeStamp.
	 * @author Idan Holander
	 */

	private int highestSignalIndex(CSVRecord record) {
		int maxS=Integer.MIN_VALUE;
		int maxIndex=1;
		for(int i=1;i<=Integer.parseInt(record.get("WiFi networks"));i++) {
			if(Integer.parseInt(record.get("Signal"+i))>maxS) {
				maxS=Integer.parseInt(record.get("Signal"+i));
				maxIndex=i;
			}
		}
		return maxIndex;
	}

	/**
	 * The method write writes the data to .kml file using kmlframework.
	 * @throws KmlException IOException
	 * @author Yair Ivgi and Idan Holander
	 */

	public void writeKML(String outputPath,List<CSVRecord> records) throws KmlException, IOException {
		Kml kml = new Kml();							//create a new KML Document
		Document document = new Document();				//add a document to the kml
		kml.setFeature(document);
		for(CSVRecord record : records) {
			int BSignal=highestSignalIndex(record);		//create a Place mark for each WiFi point.
			String ssid = chackSsid(record.get("SSID"+BSignal));
			Placemark ifi = new Placemark(ssid);
			ifi.setDescription("Name of device: "+record.get("ID")+"\n"+"MAC: "+record.get("MAC"+BSignal)+"\n"+" Frequncy: "+record.get("Frequncy"+BSignal)+"\n"+" Signal: "+record.get("Signal"+BSignal)+"\n");
			ifi.setLocation(Double.parseDouble(record.get("Lon")), Double.parseDouble(record.get("Lat")));
			String time = record.get("Time");			//ifi.setId(record.get("Time"));
			if(time.length()<19){						//check if the time string is in format
				continue;
			}
			time = time.substring(0, 10)+"T"+time.substring(11, 19)+"-00:01";
			TimePrimitive timeAtPoint = new TimeStamp(time);
			ifi.setTimePrimitive(timeAtPoint);
			document.addFeature(ifi);					//add the place mark to the Document
		}
		if(!outputPath.contains("\\newData")){
			File dir = new File(outputPath+"\\newData");
			if (dir.mkdir()) {
				System.out.println("directory " + dir.getAbsolutePath() + " created");
			} else {
				System.out.println("directory " + dir.getAbsolutePath() + " failed");			
			}
		}
		outputPath += "\\newData\\DATA.kml";			//generate the kml file
		kml.createKml(outputPath);
	}

	/**
	 * The method write writes the data to .csv file in data format.
	 * @throws Exception
	 * @author Yair Ivgi Idan Holander
	 */

	public void writeCSV(String outputPath,List<CSVRecord> records) throws Exception {
		if(!outputPath.contains("\\newData")){
			File dir = new File(outputPath+"\\newData");
			if (dir.mkdir()) {
				System.out.println("directory " + dir.getAbsolutePath() + " created");
			} else {
				System.out.println("directory " + dir.getAbsolutePath() + " failed");			
			}
		}
		WriteCsv wc=new WriteCsv(outputPath+"\\newData\\DATA.csv");
		for(CSVRecord record : records) {
			int namberOfWifi=Integer.parseInt(record.get("WiFi networks"));
			String line = null;
			line=record.get("Time");						//"Time"
			line+=","+record.get("ID");						//"ID"
			line+=","+record.get("Lat");					//"Lat"
			line+=","+record.get("Lon");					//"Lon"
			line+=","+record.get("Alt");					//"Alt"
			line+=","+String.valueOf(namberOfWifi);			//"WiFi networks"

			for (int i = 1; i <= namberOfWifi; i++) {
				line+=","+record.get("SSID"+i);				//"SSID"
				line+=","+record.get("MAC"+i);				//"MAC"
				line+=","+record.get("Frequncy"+i);			//"Frequency/channel"
				line+=","+record.get("Signal"+i);			//"Signal/RSSI"			
			}
			if(namberOfWifi<10){
				for (int q = namberOfWifi; q < 10; q++) {
					line+=",,,";
				}
			}
			wc.writeLine(line);
		}
		wc.close();
	}
}
