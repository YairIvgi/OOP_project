import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

public class FilterByIdTest {
	/**
	 * @author Yair Ivgi and Idan Holander
	 * test the getFiltered method 
	 */
	@Test
	public void test() throws IOException {
		CsvReader cr=new CsvReader();
		try {
			cr.readFolder("data");
			File file = new File(cr.getOutputFile());
			Reader in;
			in = new FileReader(file);

			FilterById id=new FilterById("GT-N7100");
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			List<CSVRecord> filteredRecords = id.getFiltered(records);
			for(CSVRecord record:records) {
				if(record.get("ID").equals(id.getID()))
					assertTrue(filteredRecords.contains(record));
				else
					assertFalse(filteredRecords.contains(record));
			}
		} catch (Exception e) {
			fail("file canot be read: "+e.getMessage());
		}
	}
}
