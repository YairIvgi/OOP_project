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
		String folderPath = "C:/Users/יאיר/Desktop/new/try";
		//System.out.println("please enter folder path");
	//	Scanner scanInput = new Scanner(System.in);
	//	folderPath= scanInput.nextLine();
	//	scanInput.close();    
		CsvReader folder=new CsvReader();
		try {
			folder.readFolder(folderPath);
		} catch (Exception e) {
			System.err.println("faild: "+e.toString());
		}
		ReadAndWriteWithFilter rw = new ReadAndWriteWithFilter();
		IFilter filterId = new FilterById("SHIELD Tablet");
		IFilter filterTime = new FilterByTime("2017-10-27  16:16:45", "2017-10-27  16:19:14");
		List<CSVRecord> records = rw.readCsv(folder.getOutputFile(), filterId);
		List<CSVRecord> records2 = rw.readCsv(folder.getOutputFile(), filterTime);	
		rw.write(records2);
	}

}
