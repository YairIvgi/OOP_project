package filter;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class AndFilters implements IOperationFilter {

	private String m_fileName;
	
	public AndFilters(String fileName){
		m_fileName = fileName;
	}
	
	@Override
	public List<CSVRecord> getFiltered(IFilter filter1, IFilter filter2) throws Exception {
		File file = new File(m_fileName);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List<CSVRecord> filteredRecords1 = filter1.getFiltered(records);
		List<CSVRecord> filteredRecords2 = filter2.getFiltered(filteredRecords1);
		return filteredRecords2;
	}
}
