import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class FilterByLocation implements IFilter {
	/**
	 * @author Yair Ivgi 
	 * This class filters the data by location.
	 */
	private double	m_lonMin;
	private double	m_lonMax;
	private double	m_latMin;
	private double	m_latMax;
	/**
	 * @author Yair Ivgi 
	 * the builder gets the coordinates specified by the user. 
	 */
	public FilterByLocation(double lonMin,double lonMax,double latMin,double latMax) {
		m_lonMin=lonMin;
		m_lonMax=lonMax;
		m_latMin=latMin;
		m_latMax=latMax;
	}
	/**
	 * @author Yair Ivgi 
	 * checks if the coordinates are valid.
	 */
	@Override
	public List<CSVRecord> getFiltered(Iterable<CSVRecord> records) throws Exception {
		if(m_lonMin > m_lonMax || m_latMin > m_latMax){
			throw new Exception("minimom cannot be bigger the maximum");
		}
		List<CSVRecord> result = new ArrayList<CSVRecord>();
		double currentLon;
		double currentLat;
		for (CSVRecord record : records) {
			 currentLat = Double.parseDouble(record.get("Lat"));
			 currentLon = Double.parseDouble(record.get("Lon"));
					if(m_latMin<=currentLat && currentLat <=m_latMax){
						if(m_lonMin<=currentLon && currentLon <=m_lonMax){
							result.add(record);
						}
					}
		}
		return result;
	}
}