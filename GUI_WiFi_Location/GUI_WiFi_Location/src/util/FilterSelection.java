package util;

public class FilterSelection {

	
	private FilterType type;
	private boolean isNot;
	private String from;
	private String to;
	
	public FilterType getType() {
		return type;
	}
	
	public void setType(FilterType type) {
		this.type = type;
	}
	
	public boolean isNot() {
		return isNot;
	}
	public void setNot(boolean isNot) {
		this.isNot = isNot;
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

	
}
