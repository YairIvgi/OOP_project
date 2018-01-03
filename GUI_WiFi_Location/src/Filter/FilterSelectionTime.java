package Filter;

public class FilterSelectionTime {

	private FilterType type;
	private boolean Not;
	private String from;
	private String to;

	public FilterType getType() {
		return type;
	}
	
	public void setType(FilterType type) {
		this.type = type;
	}

	public boolean isNot() {
		return Not;
	}

	public void setNot(boolean timeNot) {
		Not = timeNot;
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
		String msg;
		if(!Not){
		msg = String.format("Time: %s , %s",getFrom(),getTo());	
		}else{
			msg = String.format("Time: ! %s , %s",getFrom(),getTo());	

		}
		return msg;
	}
	
}
