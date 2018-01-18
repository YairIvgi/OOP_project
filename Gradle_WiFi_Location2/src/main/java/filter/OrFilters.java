package filter;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import readAndWrite.UnionRecords;

/** 
 * This class allow you to perform the OR operation on the filter.
 * @throws Exception
 * @author Yair Ivgi 
 */

public class OrFilters implements IOperationFilter {
	
	public OrFilters(){
	}
	
	@Override
	public List<CSVRecord> getFiltered(List<CSVRecord> records, IFilter filter1,boolean isNot1, IFilter filter2,boolean isNot2) throws Exception {
		List<CSVRecord> filteredRecords1 = filter1.getFiltered(records,isNot1);
		List<CSVRecord> filteredRecords2 = filter2.getFiltered(records,isNot2);
		UnionRecords unite = new UnionRecords(filteredRecords1);
		unite.combainData(filteredRecords2);
		return unite.get_records();
	}

}
