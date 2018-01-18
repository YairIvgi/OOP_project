import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.boehn.kmlframework.kml.KmlException;
import org.junit.Test;

import readAndWrite.DataBaseIO;
import readAndWrite.RawCsvReader;

public class DataBaseIOTest {

	@Test
	public void test() {
		DataBaseIO rw=new DataBaseIO();
		 		RawCsvReader cr=new RawCsvReader();
		 		try {
		 		cr.readFolder("data");
		 		List<CSVRecord> records=rw.readData(cr.getOutputFile());
		 		
		 			rw.writeKML("data", records);
		 		} catch (KmlException e) {
		 			// TODO Auto-generated catch block
		 			fail("no kml file in folder "+e.getMessage());
		 		} catch (Exception e) {
		 			// TODO Auto-generated catch block
		 			fail("file cannot be read: "+e.getMessage());
		 		}
		 		
	}
}
