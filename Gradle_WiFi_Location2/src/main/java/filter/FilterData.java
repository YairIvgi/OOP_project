package filter;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

/** 
 * This class execute the filter.
 * @throws Exception
 * @author Yair Ivgi
 */

public class FilterData {

	public List<CSVRecord> filterData(List<CSVRecord> records, IFilter filter,boolean isNot) throws Exception{
		try {
			List<CSVRecord> filteredRecords = filter.getFiltered(records,isNot);
			return filteredRecords;
		} catch (Exception e) {
			throw new Exception("Error reading file\n" + e);		
		}
	}	
}
