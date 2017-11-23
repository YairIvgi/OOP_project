import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

public class FilterByLocationTest {

	@Test
	public void test() {
		CsvReader cr=new CsvReader();
		try {
			cr.readFolder("C:\\Users\\user\\Documents\\read");
			File file = new File(cr.getOutputFile());
			Reader in;
			in = new FileReader(file);

			FilterByLocation location=new FilterByLocation(34.400, 35.400, 32.100, 32.400);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			List<CSVRecord> filteredRecords = location.getFiltered(records);
			double currentLon;
			double currentLat;
			for(CSVRecord record:records) {
				currentLat = Double.parseDouble(record.get("Lat"));
				 currentLon = Double.parseDouble(record.get("Lon"));
				 if(location.getLatMin()<=currentLat && currentLat <=location.getLatMax()){
						if(location.getLonMin()<=currentLon && currentLon <=location.getLonMax()){
							assertTrue(filteredRecords.contains(record));
						}
						else
							assertFalse(filteredRecords.contains(record));
					}
				 else
					 assertFalse(filteredRecords.contains(record));
			}
		} catch (Exception e) {
			fail("file canot be read: "+e.getMessage());
		}
	}
}
