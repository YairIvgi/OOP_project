package genral;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import filter.AndFilters;
import filter.FilterById;
import filter.FilterByLocation;
import filter.FilterByTime;
import filter.IFilter;
import filter.IOperationFilter;
import filter.IOperationWithOneFilter;
import filter.NotFilters;
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

				String folderPath = "C:\\Users\\user\\Desktop\\try";	
				RawCsvReader folder=new RawCsvReader();
				try {
					folder.readFolder(folderPath);
				} catch (Exception e) {
					System.err.println("faild: "+e.toString());
				}

		//// part 1 - filter and write the kml file////

				DataBaseIO rw = new DataBaseIO();
				IFilter filterId = new FilterById("Lenovo PB2-690Y");
				IFilter filterTime = new FilterByTime("2017-12-1  10:43:00", "2017-12-1  10:50:14");
				IFilter filterLocation = new FilterByLocation(34.806, 32.165, 0.022);
				List<CSVRecord> records1 = rw.filterData(folder.getOutputFile(), filterId);			//filter by id
				List<CSVRecord> records2 = rw.filterData(folder.getOutputFile(), filterTime);			//filter by time frame
				List<CSVRecord> records3 = rw.filterData(folder.getOutputFile(), filterLocation);		//filter by coordinates
		 		IOperationFilter andfilter = new AndFilters(folder.getOutputFile());
		 		IOperationFilter orfilter = new OrFilters(folder.getOutputFile());
		 		IOperationWithOneFilter notfilter = new NotFilters(folder.getOutputFile());
				List<CSVRecord> records4 = andfilter.getFiltered (filterId, filterTime);
				List<CSVRecord> records5 = orfilter.getFiltered (filterId, filterTime);
				List<CSVRecord> records6 = notfilter.getFiltered(filterId);
				rw.writeKML(folderPath,records6);
				rw.writeCSV(folderPath, records6);

		//// part 2 - Algo1 and Algo2////

		//FindMacLoc fM2 = new FindMacLoc();
		//fM2.locateMac_FromFolder("C:\\temp\\scanes\\BM3",5);
		

		//FindLocByMac fL =new FindLocByMac("C:\\temp\\scanes\\newData\\DATA.csv", 5); 
		//fL.estimatedLoc_FromFile("C:\\temp\\scanes\\_comb_no_gps_ts1.csv");
	}
}
