package filter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

/** 
 * This is a Interface that is responsible to the filters in the program.
 * @author Yair Ivgi
 */

public interface IFilter {

	public List<CSVRecord> getFiltered(Iterable<CSVRecord> records) throws Exception;
	
}