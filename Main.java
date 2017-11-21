import java.util.List;
import java.util.Scanner;

import org.apache.commons.csv.CSVRecord;

public class Main {

	public static void main(String[] args) throws Exception{
		String folderPath;
		//="C:/Users/éàéø/Desktop/new/try";
		System.out.println("please enter folder path");
		Scanner scanInput = new Scanner(System.in);
		folderPath= scanInput.nextLine();
		scanInput.close();    
		CsvReader folder=new CsvReader();
		try {
			folder.readFolder(folderPath);
		} catch (Exception e) {
			System.err.println("faild: "+e.toString());
		}
		ReadAndWriteWithFilter rw = new ReadAndWriteWithFilter();
		IFilter filterId = new FilterById("GT-N7100");
		IFilter filterTime = new FilterByTime("2017-10-27  16:16:45", "2017-10-27  16:19:14");
		List<CSVRecord> records = rw.readCsv(folder.getOutputFile(), filterId);
		//List<CSVRecord> records2 = rw.readCsv(folder.getOutputFile(), filterTime);
		int k=0;
		rw.write(records);
		
	}

}
