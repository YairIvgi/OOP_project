import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

class CsvReaderTest {

	@Test
	void test() {
		CsvReader cr = new CsvReader();
		File f= new File("C:/Users/user/Documents/read/WigleWifi_20171030124738.csv");
		List<RawData>data = new ArrayList<RawData>();
		try {
			data=cr.readCsv(f,data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(data.size()>0);
	}

}
