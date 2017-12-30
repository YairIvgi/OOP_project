package readAndWrite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class UnionRecords {

	private List<CSVRecord> m_records;

	public UnionRecords(List<CSVRecord> records){
		m_records.addAll(records);
	}
	
	public List<CSVRecord> addDataFromFile(String filePath) throws IOException{
		File file = new File(filePath);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		return compairOrgnaize(records);
	}

	public List<CSVRecord> addDataFromFolder(String folderPath) throws IOException{
		RawCsvReader folder=new RawCsvReader();
		try {
			folder.readFolder(folderPath);
		} catch (Exception e) {
			System.err.println("faild: "+e.toString());
		}
		return addDataFromFile(folder.getOutputFile());
	}
	
	private List<CSVRecord> compairOrgnaize(Iterable<CSVRecord> records){
		List<CSVRecord> combined = new ArrayList<CSVRecord>(m_records);
		combined.addAll((Collection<? extends CSVRecord>) records);
		return combined;
	}

}
