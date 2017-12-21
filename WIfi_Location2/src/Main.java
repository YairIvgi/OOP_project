public class Main {

	/**
	 * @information The purpose of this program is to receive .csv files that samples WiFi points and then to modify the files 
	 * in to one file in .kml format.
	 * @author Yair Ivgi and Idan Holander
	 */

	public static void main(String[] args) throws Exception{
		//// part 0- write the csv file////

		//		String folderPath = "C://Users//יאיר//desktop//new//try";	
		//		RawCsvReader folder=new RawCsvReader();
		//		try {
		//			folder.readFolder(folderPath);
		//		} catch (Exception e) {
		//			System.err.println("faild: "+e.toString());
		//		}

		//// part 1 - filter and write the kml file////

		//		ReadAndWriteWithFilter rw = new ReadAndWriteWithFilter();
		//		IFilter filterId = new FilterById("ONEPLUS A3003");
		//		IFilter filterTime = new FilterByTime("2017-10-27  16:13:00", "2017-10-27  16:25:14");
		//		IFilter filterLocation = new FilterByLocation(34.806, 32.165, 0.022);
		//		List<CSVRecord> records1 = rw.readCsv(folder.getOutputFile(), filterId);			//filter by id
		//		List<CSVRecord> records2 = rw.readCsv(folder.getOutputFile(), filterTime);			//filter by time frame
		//		List<CSVRecord> records3 = rw.readCsv(folder.getOutputFile(), filterLocation);		//filter by coordinates
		// 		List<CSVRecord> records4 = rw.andFilter(folder.getOutputFile(), filterId, filterTime);
		//		rw.writeKML(folderPath,records4);
		//		rw.writeCSV(folderPath, records4);

		//// part 2 - Algo1 and Algo2////

		FindMacLoc fM2 = new FindMacLoc();
		fM2.locateMac_FromFolder("c://temp//scanes",3);
		
		FindLocByMac fL =new FindLocByMac("c://temp//DATA.csv", 3); 
		fL.estimatedLoc_FromFile("c://temp//no_gps.csv");
	}
}
