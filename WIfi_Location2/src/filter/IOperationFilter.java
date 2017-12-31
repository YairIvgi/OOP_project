package filter;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface IOperationFilter {
	
	public List<CSVRecord> getFiltered(IFilter filter1, IFilter filter2) throws Exception;
	
}
