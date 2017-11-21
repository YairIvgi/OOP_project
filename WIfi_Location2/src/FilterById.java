import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class FilterById implements IFilter {
	/**
	 * @author Yair Ivgi 
	 * This class filters the data by the name of the WiFi sample. 
	 */
	private String m_id;
	
	public FilterById(String id) {
		m_id=id;
	}
	
	@Override
	public List<CSVRecord> getFiltered(Iterable<CSVRecord> records) {
		List<CSVRecord> result = new ArrayList<CSVRecord>();
		for (CSVRecord record : records) {
			if(record.get("ID").equals(m_id)){
				result.add(record);
			}
		}
		return result;
	}

}
