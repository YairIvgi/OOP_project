package filter;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class NotFilters implements IOperationFilter {

	private String m_fileName;
	
	public NotFilters(String fileName){
		m_fileName = fileName;
	}
	
	@Override
	public List<CSVRecord> getFiltered(IFilter filter1, IFilter filter2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
