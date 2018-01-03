package Filter;

public class FilterSelectionID {

	private FilterType type;
	private boolean Not;
	private String id;

	public boolean isNot() {
		return Not;
	}

	public void setNot(boolean not) {
		Not = not;
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
		String msg;
		if(!Not){
			msg = String.format("ID: %s",getId());	
		} else{
			msg = String.format("ID: ! %s",getId());	
		}
		return msg;
	}

}
