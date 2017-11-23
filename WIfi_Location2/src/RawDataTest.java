import static org.junit.Assert.*;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class RawDataTest {
	/**
	 * @author Yair Ivgi and Idan Holander
	 * test the organizeData method  
	 */
	@Test
	public void testOrganizeData() throws Exception {
		CsvReader cr = new CsvReader();
		String folderPath="data";
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
				assertTrue( "too many wifi spots",data.get(i).getSamples().size()<=10);
			}
		}
	}
}
