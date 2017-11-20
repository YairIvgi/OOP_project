import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class FilterByTime implements IFilter {

	private Date m_minTime;
	private Date m_maxTime;

	
	public FilterByTime(String minTime,String maxTime) throws Exception {
		SimpleDateFormat minMaxDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			m_minTime = minMaxDateFormat.parse(minTime);
			m_maxTime = minMaxDateFormat.parse(maxTime);
		} catch (ParseException e) {
			throw new Exception("fail : "+e.toString());
		}
	}
	
	@Override
	public List<CSVRecord> getFiltered(Iterable<CSVRecord> records) throws Exception {
		List<CSVRecord> result = new ArrayList<CSVRecord>();
		SimpleDateFormat currentDateFormat;
		currentDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime;
		for(CSVRecord record : records){
			try {
				currentTime = currentDateFormat.parse("Time");
				if(m_minTime.before(currentTime) && m_maxTime.after(currentTime)){
					result.add(record);
				}
			} catch (ParseException e) {
				throw new Exception("time format is invalid");
			}
		}
		return result;
	}

}
