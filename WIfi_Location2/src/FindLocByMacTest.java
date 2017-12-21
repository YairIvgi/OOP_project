import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Idan Hollander
 * Test estimatedLoc_FromFile method
 */
public class FindLocByMacTest {

	@Test
	public void test() {
		FindLocByMac fL =new FindLocByMac("c://temp//scanes//BM3//NewData//DATA.csv", 5); 
		try {
			fL.estimatedLoc_FromFile("c://temp//scanes//_comb_no_gps_ts1.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("file cannot be read");
		}
	}

}
