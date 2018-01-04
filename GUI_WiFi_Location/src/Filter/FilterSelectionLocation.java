package Filter;

import java.io.Serializable;

public class FilterSelectionLocation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private FilterType type;
	private boolean m_not;
	private String lat;
	private String lon;
	private String radius;

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public FilterType getType() {
		return type;
	}

	public void setType(FilterType type) {
		this.type = type;
	}

	public boolean isNot() {
		return m_not;
	}

	public void setNot(boolean locationNot) {
		m_not = locationNot;
	}

	public String toString(){
		String not = m_not?" ! ":"";
		String msg = String.format("Location: %s %s, %s, %s",not,getLon(),getLat(),getRadius());	
		return msg;
	}
}
