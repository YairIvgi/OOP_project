package filter;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class NotFilters implements IOperationWithOneFilter{

	private String m_fileName;
	
	public NotFilters(String fileName){
		m_fileName = fileName;
	}
	
	@Override
	public List<CSVRecord> getFiltered(IFilter filter) throws Exception {
		File file = new File(m_fileName);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List<CSVRecord> filteredRecords = filter.getFiltered(records);
		in.close();
		in = new FileReader(file);
		records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List<CSVRecord> result = new ArrayList<CSVRecord>();
		boolean contain;
		for(CSVRecord record: records) {
			contain = false;
			for(CSVRecord record2: filteredRecords) {
				if(record.get("Time").equals(record2.get("Time")) && record.get("ID").equals(record2.get("ID"))){
					contain = true;
				}
			}
			if(!contain) {
				result.add(record);
			}
		}
		return result;
	}

}
