package genral;

import org.apache.commons.csv.CSVRecord;

/**
 * This class combines record and point as an object.
 * @author Yair Ivgi
 */

public class CsvRecordPoint {
	
	CSVRecord m_record;
	WifiSpot  m_point;
	
	public CsvRecordPoint(CSVRecord record, WifiSpot point){
		m_record = record;
		m_point = point;
	}

	public CSVRecord getM_record() {
		return m_record;
	}

	public WifiSpot getM_point() {
		return m_point;
	}	
}
