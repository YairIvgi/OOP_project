package filter;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface IOperationWithOneFilter {

	public List<CSVRecord> getFiltered(IFilter filter1) throws Exception;
}
