package guiFilter;

import java.io.Serializable;

/** 
 * This class define the filter by id. 
 * @author Yair Ivgi
 */

public class FilterSelectionID implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private FilterType type;
	private boolean m_not;
	private String id;

	public boolean isNot() {
		return m_not;
	}

	public void setNot(boolean not) {
		m_not = not;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FilterType getType() {
		return type;
	}

	public void setType(FilterType type) {
		this.type = type;
	}

	public String toString(){
		String not = m_not?" ! ":"";
		String msg = String.format("ID: %s %s",not,getId());	
		return msg;
	}

}
