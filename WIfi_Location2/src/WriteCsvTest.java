import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

public class WriteCsvTest {
	@Test
	public void test() throws Exception {
		CsvReader cr = new CsvReader();
		cr.readFolder("C:\\Users\\user\\Documents\\read");// reading and writing in csv
		try {
			File file = new File(cr.getOutputFile());
			Reader in = new FileReader(file);
			Iterable<CSVRecord>records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			int countRecord=0;
			for(CSVRecord record: records) {
				countRecord++;
			}
			assertTrue(countRecord>1);
		} catch (Exception e) {
			throw new Exception("Error reading file\n" + e);		
		}
	}

}
