
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import filter.FilterByLocation;
import readAndWrite.RawCsvReader;

/**
 * Test the getFiltered method.
 * @author Yair Ivgi and Idan Holander
 */

public class FilterByLocationTest {
	@Test
	public void test() {
		RawCsvReader cr=new RawCsvReader();
		try {
			cr.readFolder("data");
			File file = new File(cr.getOutputFile());
			Reader in;
			in = new FileReader(file);

			FilterByLocation location=new FilterByLocation(34.806, 32.165, 0.022);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			List<CSVRecord> filteredRecords = location.getFiltered(records, false);
			double currentLon;
			double currentLat;
			double dist;
			for(CSVRecord record:records) {
				currentLat = Double.parseDouble(record.get("Lat"));
				currentLon = Double.parseDouble(record.get("Lon"));
				dist=Math.sqrt(Math.pow(currentLon-location.getLon(), 2)+Math.pow(currentLat-location.getLat(), 2));
				if(dist<=location.getRadius()){
					assertTrue(filteredRecords.contains(record));
				}
				else
					assertFalse(filteredRecords.contains(record));
			}
			in.close();
		} catch (Exception e) {
			fail("file canot be read: "+e.getMessage());
		}
	}
}
