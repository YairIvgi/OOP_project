package filter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

/** 
 * This class filters the data by the name of the WiFi sample.
 * @author Yair Ivgi 
 */

public class FilterById implements IFilter {
	private static final long serialVersionUID = 1L;

	private String m_id;

	public FilterById(String id) {
		m_id=id;
	}

	@Override
	public List<CSVRecord> getFiltered(Iterable<CSVRecord> records,boolean isNot) throws Exception {
		List<CSVRecord> result = new ArrayList<CSVRecord>();
		for (CSVRecord record : records) {
			if(record.get("ID").equals(m_id)){
				result.add(record);
			}
		}
		System.out.println("getNot result size = "+result.size() +" isNot= "+isNot);
		if(isNot){
			try {
				result = getNotFiltered((List<CSVRecord>)records ,result);
			} catch (Exception e) {
				throw new Exception("Not Filter failed: "+e.getMessage());
			}
		}
		return result;
	}
	public String getID() {
		return this.m_id;
	}

	public static List<CSVRecord> getNotFiltered(List<CSVRecord> records,List<CSVRecord> filterRecords) throws Exception {
		System.out.println("getNot size = "+records.size()+" filtered size = "+filterRecords.size());
		List<CSVRecord> result = new ArrayList<CSVRecord>();
		boolean contain;
		for(CSVRecord record: records) {
			contain = false;
			for(CSVRecord record2: filterRecords) {
				if(record.get("Time").equals(record2.get("Time")) && record.get("ID").equals(record2.get("ID"))){
					contain = true;
					break;
				}
			}
			if(!contain) {
				result.add(record);
			}
		}
		System.out.println("getNot result size = "+result.size());
		return result;
	}
}

