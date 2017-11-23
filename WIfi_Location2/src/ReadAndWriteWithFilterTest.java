import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

public class ReadAndWriteWithFilterTest {

	@Test
	public void testHighestSignalIndex() {
		ReadAndWriteWithFilter rw=new ReadAndWriteWithFilter();
		CsvReader cr=new CsvReader();
		try {
		cr.readFolder("C:\\Users\\user\\Documents\\read");
		IFilter filterId = new FilterById("SHIELD Tablet");
		
		List<CSVRecord> records=rw.readCsv(cr.getOutputFile(), filterId);
		for(CSVRecord record:records) {
			int BSignal=rw.highestSignalIndex(record);
			for(int i=1;i<=Integer.parseInt(record.get("WiFi networks"));i++) {
				assertFalse(Integer.parseInt(record.get("Signal"+i))>Integer.parseInt(record.get("Signal"+BSignal)));
			}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
