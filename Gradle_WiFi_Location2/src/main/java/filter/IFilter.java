package filter;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

/** 
 * This is a Interface that is responsible to the filters in the program.
 * @author Yair Ivgi
 */

public interface IFilter extends Serializable{

	public List<CSVRecord> getFiltered(Iterable<CSVRecord> records,boolean isNot) throws Exception;
	
}