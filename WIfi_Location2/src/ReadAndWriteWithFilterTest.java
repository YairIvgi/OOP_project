import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.boehn.kmlframework.kml.KmlException;
import org.junit.Test;

/**
 * Test the highestSignalIndex and the write methods.
 * @author Yair Ivgi and Idan Holander
 */

public class ReadAndWriteWithFilterTest {
	@Test
	public void testHighestSignalIndex() {
		ReadAndWriteWithFilter rw=new ReadAndWriteWithFilter();
		RawCsvReader cr=new RawCsvReader();
		try {
			cr.readFolder("data");
			IFilter filterId = new FilterById("GT-N7100");		
			List<CSVRecord> records=rw.readCsv(cr.getOutputFile(), filterId);
			for(CSVRecord record:records) {
				int BSignal=rw.highestSignalIndex(record);
				for(int i=1;i<=Integer.parseInt(record.get("WiFi networks"));i++) {
					assertFalse(Integer.parseInt(record.get("Signal"+i))>Integer.parseInt(record.get("Signal"+BSignal)));
				}
			}
		} catch (Exception e) {
			fail("file cannot be read: "+e.getMessage());
		}
	}
	@Test
	public void testKml() {
		ReadAndWriteWithFilter rw=new ReadAndWriteWithFilter();
		RawCsvReader cr=new RawCsvReader();
		try {
			cr.readFolder("data");
			IFilter filterId = new FilterByTime("2017-10-27  16:16:45","2017-11-01  16:16:45");
			List<CSVRecord> records=rw.readCsv(cr.getOutputFile(), filterId);
			rw.writeKML("data", records);
		} catch (KmlException | IOException e) {
			fail("no kml file in folder "+e.getMessage());
		} catch (Exception e) {
			fail("file cannot be read: "+e.getMessage());
		}
	}
}
