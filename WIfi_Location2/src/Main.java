import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class Main {

	/**
	 * @information The purpose of this program is to receive .csv files that samples WiFi points and then to modify the files 
	 * in to one file in .kml format.
	 * @author Yair Ivgi and Idan Holander
	 */
	
	public static void main(String[] args) throws Exception{

		String folderPath = "C://Users//יאיר//desktop//new//try";					//in my computer: return result;
		// part 0- write the csv file
		RawCsvReader folder=new RawCsvReader();
		try {
			folder.readFolder(folderPath);
		} catch (Exception e) {
			System.err.println("faild: "+e.toString());
		}
		// part 1 - filter and write the kml file
		ReadAndWriteWithFilter rw = new ReadAndWriteWithFilter();

		IFilter filterId = new FilterById("ONEPLUS A3003");
		IFilter filterTime = new FilterByTime("2017-10-27  16:15:45", "2017-10-27  16:23:14");
 		IFilter filterLocation = new FilterByLocation(34.806, 32.165, 0.022);
		//List<CSVRecord> records1 = rw.readCsv(folder.getOutputFile(), filterId);			//filter by id
 		//List<CSVRecord> records2 = rw.readCsv(folder.getOutputFile(), filterTime);			//filter by time frame
 		//List<CSVRecord> records3 = rw.readCsv(folder.getOutputFile(), filterLocation);		//filter by coordinates
 		List<CSVRecord> records4 = rw.andFilter(folder.getOutputFile(), filterId, filterTime);
 		rw.writeKML(folderPath,records4);
 		rw.writeCSV(folderPath, records4);
 		// part 2 - Algo1 and Algo2
//		String filePath = "C://Users//יאיר//desktop//new//try//newData//DATA.csv";
// 		FindLocByMac f1 = new FindLocByMac(folderPath,filePath);
// 		WifiSpot point = f1.locate("aa:6b:ad:04:c6:a8");
	}
}
