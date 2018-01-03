package Filter;
public class FilterSelectionLocation {

	private FilterType type;
	private boolean Not;
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
		return Not;
	}

	public void setNot(boolean locationNot) {
		Not = locationNot;
	}

	public String toString(){
		String msg;
		if(!Not){
			msg = String.format("Location: %s , %s , %s",getLat(),getLon(),getRadius());
		}else{
			msg = String.format("Location: ! %s , %s , %s",getLat(),getLon(),getRadius());
		}
		return msg;
	}
}
