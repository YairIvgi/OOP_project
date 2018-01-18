package guiFilter;

import java.io.Serializable;

/** 
 * This class define the filter by time. 
 * @author Yair Ivgi
 */

public class FilterSelectionTime implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private FilterType type;
	private boolean m_not;
	private String from;
	private String to;

	public FilterType getType() {
		return type;
	}
	
	public void setType(FilterType type) {
		this.type = type;
	}

	public boolean isNot() {
		return m_not;
	}

	public void setNot(boolean timeNot) {
		m_not = timeNot;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String toString(){
		String not = m_not?" ! ":"";
		String msg = String.format("Time: %s %s, %s",not,getFrom(),getTo());	
		return msg;
	}
	
}
