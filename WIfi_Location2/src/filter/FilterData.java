package filter;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class FilterData {

	/** 
	 * 
	 * @throws Exception
	 * @author Yair Ivgi
	 */

	public List<CSVRecord> filterData(List<CSVRecord> records, IFilter filter) throws Exception{
		try {
			List<CSVRecord> filteredRecords = filter.getFiltered(records);
			return filteredRecords;
		} catch (Exception e) {
			throw new Exception("Error reading file\n" + e);		
		}
	}	
}
