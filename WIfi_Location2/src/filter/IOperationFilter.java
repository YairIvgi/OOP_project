package filter;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface IOperationFilter {
	
	public List<CSVRecord> getFiltered(List<CSVRecord> records, IFilter filter1,boolean isNot1, IFilter filter2,boolean isNot2) throws Exception;
	
}
