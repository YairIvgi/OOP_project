package Filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import org.apache.commons.csv.CSVRecord;

public class FiltersSelections implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<CSVRecord> m_records = new ArrayList<CSVRecord>();

	private FilterSelectionID m_id;
	private FilterSelectionTime m_time;
	private FilterSelectionLocation m_location;
	private FilterOperation m_operation;
	private FilterType m_type1;
	private FilterType m_type2;
	private String m_folderPath;
	private int m_diffrentMac;

	public FiltersSelections(){
		m_id = new FilterSelectionID();
		m_time = new FilterSelectionTime();
		m_location = new FilterSelectionLocation();
	}
	
	public List<CSVRecord> getRecords() {
		return m_records;
	}
	
	public void setRecords(List<CSVRecord> records) {
		 m_records = records;
	}


	public FilterSelectionID getM_id() {
		return m_id;
	}
	
	public void setM_id(FilterSelectionID m_id) {
		this.m_id = m_id;
	}
	
	public FilterSelectionTime getM_time() {
		return m_time;
	}
	
	public void setM_time(FilterSelectionTime m_time) {
		this.m_time = m_time;
	}
	
	public FilterSelectionLocation getM_location() {
		return m_location;
	}
	
	public void setM_location(FilterSelectionLocation m_location) {
		this.m_location = m_location;
	}
	
	public FilterType getM_type1() {
		return m_type1;
	}
	
	public void setM_type1(FilterType m_type1) {
		this.m_type1 = m_type1;
	}
	
	public FilterType getM_type2() {
		return m_type2;
	}
	
	public void setM_type2(FilterType m_type2) {
		this.m_type2 = m_type2;
	}
	
	public FilterOperation getM_operation() {
		return m_operation;
	}
	
	public void setM_operation(FilterOperation m_operation) {
		this.m_operation = m_operation;
	}
	
	public String getM_folderPath() {
		return m_folderPath;
	}
	
	public void setM_folderPath(String m_folderPath) {
		this.m_folderPath = m_folderPath;
	}

	public int getDiffrentMac() {
		return m_diffrentMac;
	}

	public void setDiffrentMac(int diffrentMac) {
		this.m_diffrentMac = diffrentMac;
	}

}
