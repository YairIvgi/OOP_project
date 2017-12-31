package readAndWrite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import genral.WifiSpot;


/**
 * @information The purpose of this class is to combine files 
 * @author Yair Ivgi 
 */

public class UnionRecords {

	private List<CSVRecord> m_records;

	public UnionRecords(List<CSVRecord> records){
		m_records = new ArrayList<CSVRecord>();
		m_records.addAll(records);
	}

	/**
	 * enable to add data from file to the data base 
	 * @author Yair Ivgi 
	 */

	public List<CSVRecord> addDataFromFile(String filePath) throws IOException{
		File file = new File(filePath);
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		return compairOrgnaize(records);
	}

	/**
	 * enable to add raw data from folder to the data base 
	 * @author Yair Ivgi 
	 */

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


	public void combainData(List<CSVRecord> records){
		List<CSVRecord> result = new ArrayList<CSVRecord>();
		result.addAll(m_records);
		boolean Contain;
		for(CSVRecord record : records){
			Contain = false;
			for(CSVRecord record2: m_records){
				if(record.get("Time").equals(record2.get("Time")) && record.get("ID").equals(record2.get("ID"))){
					Contain = true;
				}
			}
			if(!Contain){
				result.add(record);
			}
		}
		m_records = result;
	}

	public List<CSVRecord> getM_records() {
		return m_records;
	}
}
