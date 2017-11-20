import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class Main {

	public static void main(String[] args) throws Exception{
		String folderPath="C:/Users/יאיר/Desktop/new/try";
//		System.out.println("please enter path");
//		Scanner scanInput = new Scanner(System.in);
//		folderPath= scanInput.nextLine();
//		scanInput.close();    
		CsvReader folder=new CsvReader();
		try {
			folder.readFolder(folderPath);
		} catch (Exception e) {
			System.err.println("faild: "+e.toString());
		}
		ReadAndWriteWithFilter rw = new ReadAndWriteWithFilter();
		IFilter filterId = new FilterById("SHIELD Tablet");
		List<CSVRecord> records = rw.readCsv(folder.getOutputFile(), filterId);
		rw.write(records);
		
	}

}
