package filter;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import readAndWrite.UnionRecords;

public class OrFilters implements IOperationFilter {

	private String m_fileName;
	
	public OrFilters(String fileName){
		m_fileName = fileName;
	}
	
	@Override
	public List<CSVRecord> getFiltered(IFilter filter1, IFilter filter2) throws Exception {
		File file = new File(m_fileName);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List<CSVRecord> filteredRecords1 = filter1.getFiltered(records);
		in.close();
		in = new FileReader(file);
		records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		List<CSVRecord> filteredRecords2 = filter2.getFiltered(records);
		UnionRecords unite = new UnionRecords(filteredRecords1);
		unite.combainData(filteredRecords2);
		return unite.getM_records();
	}

}
