import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

class RawDataTest {

	@Test
	void testOrganizeData() throws Exception {
		CsvReader cr = new CsvReader();
		String folderPath="C:\\Users\\user\\Documents\\read";
		List<RawData> data;
		File folder = new File(folderPath);

		File[] listOfFiles = folder.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String filename)
			{ return filename.endsWith(".csv"); }
		} );		
		if(listOfFiles==null){
			throw new Exception("The folder "+folderPath+" does not exist");
		}
		for (int i = 0; i < listOfFiles.length; i++) {
			data = new ArrayList<RawData>();
			cr.readCsv(listOfFiles[i],data);
			data=RawData.organizeData(data);
			for(int j=0;j<data.size();j++) {
				assertTrue(data.get(j).getSamples());
			}
		}
		fail("Not yet implemented");
	}
	

}
