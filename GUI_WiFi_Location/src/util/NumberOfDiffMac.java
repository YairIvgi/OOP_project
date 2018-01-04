package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVRecord;

public class NumberOfDiffMac {

	private List<CSVRecord> m_records;

	public NumberOfDiffMac(List<CSVRecord> records){
		m_records = new ArrayList<CSVRecord>();	
		m_records.addAll(records);
	}
	
	public int getNum(){
		List <String> points =new ArrayList<String>();
		for(CSVRecord record : m_records){
			int numOfSamples = Integer.parseInt(record.get("WiFi networks"));
			for (int i = 1; i <= numOfSamples ; i++) {
				String index = String.valueOf(i);
				points.add(record.get("MAC"+index));
				}
			}
		Set<String> uniqueNumbers = new HashSet<String>(points);
		return uniqueNumbers.size();
	}
}
