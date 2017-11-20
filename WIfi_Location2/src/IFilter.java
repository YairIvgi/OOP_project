import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface IFilter {

	public List<CSVRecord> getFiltered(Iterable<CSVRecord> records) throws Exception;
	
//	public List<String[]> byID(String ID,  String filePath) throws Exception;
//	
//	public List<String[]> sortByLocation(double lonMin,double lonMax,double latMin,double latMax, File filePath);
//	
//	public List<String[]> sortByTime(String mintime,String maxtime,  File filePath);

}