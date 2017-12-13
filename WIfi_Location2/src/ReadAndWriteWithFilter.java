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
	 */
	
	/**
	 * @author Yair Ivgi 
	 *  The method readCsv reads the modified .csv file and sends the data to the filters.
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
 	 * @author Yair Ivgi and Idan Hollander
 	 *  TODO add description
 	 */
 	public List<CSVRecord> andFilter(String fileName, IFilter filter1,IFilter filter2) throws Exception{
 		try {
 			File file = new File(fileName);
 			Reader in = new FileReader(file);
 			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
 			List<CSVRecord> filteredRecords1 = filter1.getFiltered(records);
 			List<CSVRecord> filteredRecords2 = filter2.getFiltered(filteredRecords1);
 			return filteredRecords2;
 		} catch (Exception e) {
 			throw new Exception("Error reading file\n" + e);		
 		}
 	}
	/**
	 * @author Yair Ivgi
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
	/**
	 * @author Idan Holander
	 *Takes the strongest signal in each time timeStamp
	 */
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
	/**
	 * @author Yair Ivgi and Idan Holander
	 * The method write writes the data to .kml file using kmlframework.
	 */
	public void write(String outputPath,List<CSVRecord> records) throws KmlException, IOException {

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
		outputPath += "\\newData\\DATA.kml";			//generate the kml file
		kml.createKml(outputPath);
	}
	
	public void writeCSV(String outputPath,List<CSVRecord> records) throws Exception {
		WriteCsv wc=new WriteCsv(outputPath+"\\newData\\DATAWF.csv");
		for(CSVRecord record : records) {
			String line = null;
			line=record.get("Time");
			line+=","+record.get("ID");
			line+=","+record.get("Lat");
			line+=","+record.get("Lon");
			line+=","+record.get("Alt");
			line+=","+record.get("WiFi networks");
			int j;
			for(j=1;j<=10&&record!=null;j++) {
				if(record.size()<46)//Ilegal record
					continue;
				line+=","+record.get("SSID"+j);
				line+=","+record.get("MAC"+j);
				line+=","+record.get("Frequncy"+j);
				line+=","+record.get("Signal"+j);
			}
			while(j<=10) {
				line+=",,,";
				j++;
			}
			wc.writeLine(line);
		}
		wc.close();
}
	
}
