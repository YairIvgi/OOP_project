import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
/**
 * Test centerOfPoints and centerWeightOfPoints methods.
 * @author Idan Hollander
 */
public class AveragingElaborateCoordinateTest {

	@Test
	public void testCenterOfPoints() {
		WifiSpot w1=new WifiSpot("-30","32.103","35.208","650");
		WifiSpot w2=new WifiSpot("-80","32.105","35.205","660");
		WifiSpot w3=new WifiSpot("-90","32.103","35.307","680");
		List <WifiSpot> points = new ArrayList<WifiSpot>();
		points.add(w1);
		points.add(w2);
		points.add(w3);
		AveragingElaborateCoordinate AV=new AveragingElaborateCoordinate();
		WifiSpot result= null;
		result=AV.centerOfPoints(points);
		assertTrue(Double.parseDouble(result.getCurrentLatitude())-32.10322469<0.00001);
		assertTrue(Double.parseDouble(result.getCurrentLongitude())-35.21645076<0.00001);
		assertTrue(Double.parseDouble(result.getAltitudeMeters())-653.7864078<0.00001);
	}

	@Test
	public void testCenterWeightOfPoints() {
		WifiSpot w1=new WifiSpot("0.476988545","32.103","35.208","650");
		WifiSpot w2=new WifiSpot("0.17381326","32.105","35.205","660");
		WifiSpot w3=new WifiSpot("0.158379105","32.103","35.307","680");
		List <WifiSpot> points = new ArrayList<WifiSpot>();
		points.add(w1);
		points.add(w2);
		points.add(w3);
		AveragingElaborateCoordinate AV=new AveragingElaborateCoordinate();
		WifiSpot result= null;
		result=AV.centerWeightOfPoints(points);
		assertTrue("Lat is need to be "+32.1034296+" but it is "+Double.parseDouble(result.getCurrentLatitude()),Double.parseDouble(result.getCurrentLatitude())-32.1034296<0.00001);
		assertTrue("Lon is need to be "+35.22673264+" but it is "+Double.parseDouble(result.getCurrentLongitude()),Double.parseDouble(result.getCurrentLongitude())-35.22673264<0.00001);
		assertTrue("Alt is need to be "+658.0198453+" but it is "+Double.parseDouble(result.getAltitudeMeters()),Double.parseDouble(result.getCurrentLatitude())-658.0198453<0.00001);
	}
}
