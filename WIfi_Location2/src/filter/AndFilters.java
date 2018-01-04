package filter;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class AndFilters implements IOperationFilter {
	
	public AndFilters(){
		
	}
	
	@Override
	public List<CSVRecord> getFiltered(List<CSVRecord> records, IFilter filter1,boolean isNot1, IFilter filter2,boolean isNot2) throws Exception {
		List<CSVRecord> filteredRecords1 = filter1.getFiltered(records,isNot1);
		List<CSVRecord> filteredRecords2 = filter2.getFiltered(filteredRecords1,isNot2);
		return filteredRecords2;
	}
}
