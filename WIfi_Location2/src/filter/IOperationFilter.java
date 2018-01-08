package filter;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

/** 
 * This is a Interface that is responsible to the operation performed on the filters in the program.
 * @author Yair Ivgi
 */

public interface IOperationFilter {
	
	public List<CSVRecord> getFiltered(List<CSVRecord> records, IFilter filter1,boolean isNot1, IFilter filter2,boolean isNot2) throws Exception;
	
}
