import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import readAndWrite.RawCsvReader;

/**
 * Test the WriteCsv method.
 * @author Yair Ivgi and Idan Holander  
 */

public class WriteCsvTest {

	@Test
	public void test() throws Exception {
		RawCsvReader cr = new RawCsvReader();
		cr.readFolder("data");			// reading and writing in csv
		try {
			File file = new File(cr.getOutputFile());
			Reader in = new FileReader(file);
			Iterable<CSVRecord>records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			int countRecord=0;
			for(@SuppressWarnings("unused") CSVRecord record: records) {
				countRecord++;
			}
			assertTrue(countRecord>1);
			in.close();
		} catch (Exception e) {
			fail("file canot be read: "+e.getMessage());		
		}
	}
}
