import java.io.File;
import java.io.FileNotFoundException;
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
public class ReadAndWriteWithFilter {
	/**
	 * @author Yair Ivgi 
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
	
	public void write(List<CSVRecord> records) throws KmlException, IOException {

		// We create a new KML Document
		Kml kml = new Kml();
		// We add a document to the kml
					Document document = new Document();
					kml.setFeature(document);
		for(CSVRecord record : records) {
			// We create a Placemark for the Department of Informatics at the university of Oslo
			int BSignal=highestSignalIndex(record);
			Placemark ifi = new Placemark(record.get("SSID"+BSignal));
			ifi.setDescription("MAC: "+record.get("MAC"+BSignal)+"\n"+" Frequncy: "+record.get("Frequncy"+BSignal)+"\n"+" Signal: "+record.get("Signal"+BSignal)+"\n");
			ifi.setLocation(Double.parseDouble(record.get("Lon")), Double.parseDouble(record.get("Lat")));

			

			// We add the placemark to the Document
			document.addFeature(ifi);
		}

		// We generate the kml file
		kml.createKml("C:\\temp\\Ifi.kml");

		// We are done
		System.out.println("The kml file was generated.");
	}
}
