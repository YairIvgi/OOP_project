package genral;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import filter.AndFilters;
import filter.FilterById;
import filter.FilterByLocation;
import filter.FilterByTime;
import filter.FilterData;
import filter.IFilter;
import filter.IOperationFilter;
import filter.OrFilters;
import readAndWrite.DataBaseIO;
import readAndWrite.RawCsvReader;

public class Main {

	/**
	 * @information The purpose of this program is to receive .csv files that samples WiFi points and then to modify the files 
	 * in to one file in .kml format.
	 * @author Yair Ivgi and Idan Holander
	 */

	public static void main(String[] args) throws Exception{
		//// part 0- write the csv file////

				String folderPath = "C:\\Users\\יאיר\\desktop\\new\\try";	
				RawCsvReader folder=new RawCsvReader();
				try {
					folder.readFolder(folderPath);
				} catch (Exception e) {
					System.err.println("faild: "+e.toString());
				}

		//// part 1 - filter and write the kml file////

				DataBaseIO rw = new DataBaseIO();
				FilterData fd = new FilterData();
				
				IFilter filterId = new FilterById("Lenovo PB2-690Y");
				IFilter filterTime = new FilterByTime("2017-10-27  16:25:00", "2017-10-27  16:36:14");
				IFilter filterLocation = new FilterByLocation(34.806, 32.165, 0.022);
				
				List<CSVRecord> records1 = rw.readData(folder.getOutputFile());			
				records1 = fd.filterData(records1, filterId,false);							//filter by id without NOT
				List<CSVRecord> records2 = rw.readData(folder.getOutputFile());			
				records2 = fd.filterData(records2, filterTime,false);		//filter by time frame without NOT
				List<CSVRecord> records3 = rw.readData(folder.getOutputFile());		
				records3 = fd.filterData(records3, filterLocation,false);		//filter by coordinates without NOT
			
				List<CSVRecord> records10 = rw.readData(folder.getOutputFile());			
		 		IOperationFilter andfilter = new AndFilters();
		 		IOperationFilter orfilter = new OrFilters();
//				List<CSVRecord> records4 = andfilter.getFiltered (records10,filterId, filterTime);
//				List<CSVRecord> records5 = orfilter.getFiltered (records10,filterId,false filterTime);
//				rw.writeKML(folderPath,records5);
//				UnionRecords un = new UnionRecords(records5);
//				un.addDataFromFolder("C://temp//scanes//BM2");
//				records5 = un.get_records();
//				rw.writeCSV(folderPath, records5);
//				rw.writeKML(folderPath, records5);

		//// part 2 - Algo1 and Algo2////
//
//		FindMacLoc fM2 = new FindMacLoc();
//		fM2.locateMac_FromFolder("c://temp//scanes//BM3",5);
//		
//
//		FindLocByMac fL =new FindLocByMac("c://temp//scanes//BM3//NewData//DATA.csv", 5); 
//		fL.estimatedLoc_FromFile("c://temp//scanes//_comb_no_gps_ts1.csv");


		//FindMacLoc fM2 = new FindMacLoc();
		//fM2.locateMac_FromFolder("C:\\temp\\scanes\\BM3",5);
		

		//FindLocByMac fL =new FindLocByMac("C:\\temp\\scanes\\newData\\DATA.csv", 5); 
		//fL.estimatedLoc_FromFile("C:\\temp\\scanes\\_comb_no_gps_ts1.csv");
	}
}
