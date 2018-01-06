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
	private JLabel m_labelFilter1;
	private JLabel m_labelFilter2;

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

	public JLabel getM_labelFilter1() {
		return m_labelFilter1;
	}

	public void setM_labelFilter1(JLabel m_labelFilter1) {
		this.m_labelFilter1 = m_labelFilter1;
	}

	public JLabel getM_labelFilter2() {
		return m_labelFilter2;
	}

	public void setM_labelFilter2(JLabel m_labelFilter2) {
		this.m_labelFilter2 = m_labelFilter2;
	}

	
	
//	public static void main(String[] args) {
//		FiltersSelections obj = new FiltersSelections();
//		
//		String folder = System.getProperty("user.dir");
//		File file = new File(folder,"FiltersSelections.obj");
//		try {
//			FileOutputStream fos = new FileOutputStream(file);
//			ObjectOutputStream oos = new ObjectOutputStream(fos);
//			oos.writeObject(obj);
//			oos.close();
//			fos.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		FiltersSelections obj2 = null;
//		try {
//			File file1 = new File(folder,"FiltersSelections.obj");
//			FileInputStream fis = new FileInputStream(file1);
//			ObjectInputStream ois = new ObjectInputStream(fis);
//			obj2 = (FiltersSelections)ois.readObject();
//			ois.close();
//			fis.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		int k = 0;
//	}
	
	
}
