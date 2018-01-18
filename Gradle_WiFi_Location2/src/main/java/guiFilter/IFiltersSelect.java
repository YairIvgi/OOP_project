package guiFilter;

/** 
 * This is a Interface that is responsible to the filters select in the program.
 * @author Yair Ivgi
 */


public interface IFiltersSelect {

	public FilterType getType1();
	
	public FilterType getType2();
	
	public void setType1(FilterType type1);
	
	public void setType2(FilterType type2);

	public String getFilter1();
	
	public String getFilter2();
	
	public void setFilter1(String filter1);
	
	public void setFilter2(String filter2);
	
	public void resetFilter1();
	
	public void resetFilter2();

}
