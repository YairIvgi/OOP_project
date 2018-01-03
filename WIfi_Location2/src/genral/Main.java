package genral;

import java.util.ArrayList;
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
import readAndWrite.UnionRecords;

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
				FilterData fd = new FilterData();
				IFilter filterId = new FilterById("Lenovo PB2-690Y");
				IFilter filterTime = new FilterByTime("2016-12-1  10:43:00", "2017-12-1  20:50:14");
				IFilter filterLocation = new FilterByLocation(34.806, 32.165, 0.022);
				List<CSVRecord> records1 = rw.readData(folder.getOutputFile());			
				records1 = fd.filterData(records1, filterId);							//filter by id
				List<CSVRecord> records2 = rw.readData(folder.getOutputFile());			
				records2 = fd.filterData(records2, filterTime);		//filter by time frame
				List<CSVRecord> records3 = rw.readData(folder.getOutputFile());		
				records3 = fd.filterData(records3, filterLocation);		//filter by coordinates

		 		IOperationFilter andfilter = new AndFilters(folder.getOutputFile());
		 		IOperationFilter orfilter = new OrFilters(folder.getOutputFile());
				List<CSVRecord> records4 = andfilter.getFiltered (filterId, filterTime);
				List<CSVRecord> records5 = orfilter.getFiltered (filterId, filterTime);
				rw.writeKML(folderPath,records5);
				UnionRecords un = new UnionRecords(records5);
				un.addDataFromFolder("C:\\temp\\scanes\\BM2");
				records5 = un.get_records();
				rw.writeCSV(folderPath, records5);
				rw.writeKML(folderPath, records5);

		//// part 2 - Algo1 and Algo2////

		//FindMacLoc fM2 = new FindMacLoc();
		FindMacLoc fM2 = new FindMacLoc("C:\\temp\\scanes\\BM3.csv",4);
		fM2.locateMac_FromExistingMac("1c:b9:c4:15:ec:3c");
		//fM2.locateMac_FromFolder("C:\\temp\\scanes\\BM3",5);
		

		//FindLocByMac fL =new FindLocByMac("C:\\temp\\scanes\\BM3\\newData\\DATA.csv", 5); 
		//fL.estimatedLoc_FromFile("C:\\temp\\scanes\\_comb_no_gps_ts1.csv");
		//fL.estimatedLoc_FromMacs("1c:b9:c4:14:2f:68", "-50", "00:1d:aa:81:d8:3c", "-60", "3c:52:82:ef:c4:3c", "-70");
		//fL.estimatedLoc_FromString("12/05/2017 11:48,model=SM-G950F_device=dreamlte,?,?,?,6, ,8c:0c:90:ae:16:83,11,-69,Ariel_University,1c:b9:c4:15:1c:b8,1,-70,Ariel_University,8c:0c:90:2e:16:88,11,-70, ,8c:0c:90:6e:16:88,11,-71,Ariel_University,1c:b9:c4:15:1c:bc,36,-77,DIRECT-61-HP DeskJet 3630 series,30:e1:71:0d:e0:63,6,-80,,,,,,,,,,,,,,,,");


		//FindMacLoc fM2 = new FindMacLoc();
		//fM2.locateMac_FromFolder("C:\\temp\\scanes\\BM3",5);
		

		//FindLocByMac fL =new FindLocByMac("C:\\temp\\scanes\\newData\\DATA.csv", 5); 
		//fL.estimatedLoc_FromFile("C:\\temp\\scanes\\_comb_no_gps_ts1.csv");
	}
}
