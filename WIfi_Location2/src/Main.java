import java.util.List;
import java.util.Scanner;

import org.apache.commons.csv.CSVRecord;

public class Main {

	/**
	 * @author Yair Ivgi and Idan Holander
	 * the purpose of this program is to receive .csv files that samples WiFi points and then to modify the files 
	 * in to one file in .kml format.
	 */
	
	public static void main(String[] args) throws Exception{
		String folderPath = "C://temp";					//in my computer:  C://Users//יאיר//desktop//new//try
		// Question 2- write the csv file
		CsvReader folder=new CsvReader();
		try {
			folder.readFolder(folderPath);
		} catch (Exception e) {
			System.err.println("faild: "+e.toString());
		}
		// Question 3 - filter and write the kml file
		ReadAndWriteWithFilter rw = new ReadAndWriteWithFilter();
		IFilter filterId = new FilterById("SHIELD Tablet");
		IFilter filterTime = new FilterByTime("2017-10-27  16:16:45", "2017-10-27  16:25:14");
		IFilter filterLocation = new FilterByLocation(34.806, 32.165, 0.022);
		//List<CSVRecord> records1 = rw.readCsv(folder.getOutputFile(), filterId);			//filter by id
		//List<CSVRecord> records2 = rw.readCsv(folder.getOutputFile(), filterTime);			//filter by time frame
		//List<CSVRecord> records3 = rw.readCsv(folder.getOutputFile(), filterLocation);		//filter by coordinates
		List<CSVRecord> records4 = rw.andFilter(folder.getOutputFile(), filterId, filterTime);
		rw.write(folderPath,records4);
		System.out.println("success The kml file was generated");
	}
}
