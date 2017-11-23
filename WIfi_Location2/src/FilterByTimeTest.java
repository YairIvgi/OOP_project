import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

public class FilterByTimeTest {
	/**
	 * @author Yair Ivgi and Idan Holander
	 * test the getFiltered method 
	 */
	@Test
	public void test() {
		CsvReader cr=new CsvReader();
		try {
			cr.readFolder("data");
			File file = new File(cr.getOutputFile());
			Reader in;
			in = new FileReader(file);

			FilterByTime time=new FilterByTime("2017-10-31 07:00:00", "2020-10-27  16:19:14");
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			List<CSVRecord> filteredRecords = time.getFiltered(records);
			SimpleDateFormat currentDateFormat;
			Date currentTime;
			for(CSVRecord record:records) {
				//check if the time format is legal 
				if(record.get("Time").charAt(2)=='/'){		
					continue;
				}else{
					currentDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}				
				currentTime = currentDateFormat.parse(record.get("Time"));
				//check if the current time is in range
				if((time.getMinTime().before(currentTime) && time.getMaxTime().after(currentTime) )|| time.getMinTime().equals(currentTime) || time.getMaxTime().equals(currentTime)) {
					assertTrue(filteredRecords.contains(record));
				}
				else
					assertFalse(filteredRecords.contains(record));
			}
		} catch (Exception e) {
			fail("file canot be read: "+e.getMessage());
		}
	}
}
