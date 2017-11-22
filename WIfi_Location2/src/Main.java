import java.util.List;
import java.util.Scanner;

import org.apache.commons.csv.CSVRecord;

public class Main {

	/**
	 * @author Yair Ivgi and Idan Holander
	 * This is a project in OOP curse. 
	 * the purpose of this program is to receive .csv files that samples WiFi points and then to modify the files 
	 * in to one .kml file.
	 */
	
	public static void main(String[] args) throws Exception{
		String folderPath = "data";
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
		IFilter filterTime = new FilterByTime("2015-10-27  16:16:45", "2020-10-27  16:19:14");
		IFilter filterLocation = new FilterByLocation(34.400, 35.400, 32.100, 32.400);
		List<CSVRecord> records1 = rw.readCsv(folder.getOutputFile(), filterId);
		List<CSVRecord> records2 = rw.readCsv(folder.getOutputFile(), filterTime);	
		List<CSVRecord> records3 = rw.readCsv(folder.getOutputFile(), filterLocation);
		rw.write(folderPath,records2);
	}

}
