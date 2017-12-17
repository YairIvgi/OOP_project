import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

/** 
 * This class filters the data by location.
 * @author Yair Ivgi
 */

public class FilterByLocation implements IFilter {
	private double	m_lon;
	private double	m_lat;
	private double	m_radius;

	/** 
	 * The constructor gets the coordinates specified by the user.
	 * @author Yair Ivgi 
	 */

	public FilterByLocation(double lon,double lat,double radius) {
		m_lon=lon;
		m_lat=lat;
		m_radius=radius;
	}

	/** 
	 * Checks if the coordinates are valid.
	 * @throws Exception
	 * @author Yair Ivgi and Idan Hollander
	 */

	@Override
	public List<CSVRecord> getFiltered(Iterable<CSVRecord> records) throws Exception {
		if(m_radius<0){
			throw new Exception("radius must be positive");
		}
		List<CSVRecord> result = new ArrayList<CSVRecord>();
		double currentLon;
		double currentLat;
		double dist;
		for (CSVRecord record : records) {
			currentLat = Double.parseDouble(record.get("Lat"));
			currentLon = Double.parseDouble(record.get("Lon"));
			dist=Math.sqrt(Math.pow(currentLon-m_lon, 2)+Math.pow(currentLat-m_lat, 2));
			if(dist<=m_radius){
				result.add(record);
			}
		}
		return result;
	}

	public double getLon() {
		return this.m_lon;
	}
	public double getLat() {
		return this.m_lat;
	}
	public double getRadius() {
		return this.m_radius;
	}
}
