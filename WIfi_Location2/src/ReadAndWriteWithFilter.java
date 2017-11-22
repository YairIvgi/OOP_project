import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.boehn.kmlframework.kml.Document;
import org.boehn.kmlframework.kml.Kml;
import org.boehn.kmlframework.kml.KmlException;
import org.boehn.kmlframework.kml.Placemark;
import org.boehn.kmlframework.kml.TimePrimitive;
import org.boehn.kmlframework.kml.TimeStamp;
public class ReadAndWriteWithFilter {
	/**
	 * @author Yair Ivgi and Idan Holander
	 * This class reads form .csv and writes to .kml .
	 * The method readCsv reads the modified .csv file and sends the data to the filters.
	 * The method write writes the data to .kml file using javaApiforKml-2.2.1 .
	 */
	public List<CSVRecord> readCsv(String fileName, IFilter filter) throws Exception{
		try {
			File file = new File(fileName);
			Reader in = new FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			List<CSVRecord> filteredRecords = filter.getFiltered(records);
			return filteredRecords;
		} catch (Exception e) {
			throw new Exception("Error reading file\n" + e);		
		}
	}
	/**
	*Check if the name is recognized in kml format
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
	
	//Takes the strongest signal in each time place
	public int highestSignalIndex(CSVRecord record) {
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
	
	public void write(String outputPath,List<CSVRecord> records) throws KmlException, IOException {

			//create a new KML Document
		Kml kml = new Kml();
			//add a document to the kml
					Document document = new Document();
					kml.setFeature(document);
		for(CSVRecord record : records) {
			//create a Place mark for each WiFi point.
			int BSignal=highestSignalIndex(record);
			String ssid = chackSsid(record.get("SSID"+BSignal));
			Placemark ifi = new Placemark(ssid);
			ifi.setDescription("MAC: "+record.get("MAC"+BSignal)+"\n"+" Frequncy: "+record.get("Frequncy"+BSignal)+"\n"+" Signal: "+record.get("Signal"+BSignal)+"\n");
			ifi.setLocation(Double.parseDouble(record.get("Lon")), Double.parseDouble(record.get("Lat")));
			//ifi.setId(record.get("Time"));
			String time = record.get("Time");
			time = time.substring(0, 10)+"T"+time.substring(11, 19)+"-01:00";
			TimePrimitive timeAtPoint = new TimeStamp(time);
			ifi.setTimePrimitive(timeAtPoint);
			//add the place mark to the Document
			document.addFeature(ifi);
		}

		//generate the kml file
		outputPath += "\\NewData\\DATA.kml";
		kml.createKml(outputPath);
		System.out.println("The kml file was generated.");
	}
}
