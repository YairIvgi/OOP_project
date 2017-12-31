package filter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

/**
 * This class filters the data by time. 
 * @author Yair Ivgi 
 */

public class FilterByTime implements IFilter {
	private Date m_minTime;
	private Date m_maxTime;

	/**
	 * The builder gets the time frame specified by the user and initializing it.
	 * @throws Exception
	 * @author Yair Ivgi 
	 */

	public FilterByTime(String minTime,String maxTime) throws Exception {
		SimpleDateFormat minMaxDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			m_minTime = minMaxDateFormat.parse(minTime);
			m_maxTime = minMaxDateFormat.parse(maxTime);
		} catch (ParseException e) {
			throw new Exception("fail : "+e.toString());
		}
	}

	/**
	 * Filter the samples by time.
	 * @throws Exception
	 * @author Yair Ivgi 
	 */

	@Override
	public List<CSVRecord> getFiltered(Iterable<CSVRecord> records) throws Exception {
		List<CSVRecord> result = new ArrayList<CSVRecord>();
		SimpleDateFormat currentDateFormat;		
		Date currentTime;
		for(CSVRecord record : records){
			try {
				//check if the time format is legal 
				if(record.get("Time").charAt(2)=='/'){		
					continue;
				}else{
					currentDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}				
				currentTime = currentDateFormat.parse(record.get("Time"));
				//check if the current time is in range
				if((m_minTime.before(currentTime) && m_maxTime.after(currentTime) )|| m_minTime.equals(currentTime) || m_maxTime.equals(currentTime)){
					result.add(record);
				}
			} catch (ParseException e) {
				throw new Exception("time format is invalid");
			}
		}
		return result;
	}
	public Date getMinTime() {
		return this.m_minTime;
	}
	public Date getMaxTime() {
		return this.m_maxTime;
	}
}
