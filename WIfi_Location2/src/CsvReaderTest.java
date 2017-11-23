import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CsvReaderTest {
	/**
	 * @author Yair Ivgi and Idan Holander
	 * test the readCsv and the readFolder methods 
	 */
	@Test
	public void testReadCsv() {
		CsvReader cr = new CsvReader();
		File f= new File("data\\WigleWifi_1.csv");
		List<RawData>data = new ArrayList<RawData>();
		try {
			data=cr.readCsv(f,data);
		} catch (Exception e) {
			fail("failed: "+e.toString());
		}
		assertTrue(data.size()>0);
		String lastTime=null;

		for(int i=0;i<data.size();i++) {
			String currentTime = null;
			currentTime=data.get(i).getSamples().get(0).getFirstSeen();
			assertFalse(lastTime!=null&&lastTime.equals(currentTime));
		}
	}
	@Test
	public void testReadFolder() {
		CsvReader cr = new CsvReader();
		String folderPath="data";
		try {
			cr.readFolder(folderPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Folder "+ folderPath +" does not exist");
		}
	}
}
