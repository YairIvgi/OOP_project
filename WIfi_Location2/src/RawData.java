import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RawData {
	/**
	 * @author Yair Ivgi 
	 * this class represent a list of WiFi points.
	 * The  method organizeData sorts the information and  takes the 10 largest signals
	 */
	private List<WifiSpot> samples;
	/**
	 * @author Yair Ivgi 
	 * the constructor initialize the ArrayList.
	 */
	public RawData(){
		samples=new ArrayList<WifiSpot>();
	}
	/**
	 * @author Yair Ivgi 
	 * add a sample to the list.
	 */
	public void add(WifiSpot spot){
		getSamples().add(spot);
	}
	/**
	 * @author Yair Ivgi 
	 * The  method sorts the information and  takes the 10 largest signals
	 */
	public static List<RawData> organizeData(List<RawData> data){
		for (int i = 0; i < data.size(); i++) {
			RawData raw = data.get(i);
			if(raw.getSamples().size()>10){
				Collections.sort(raw.getSamples(), new Comparator<WifiSpot>() {
					@Override
					public int compare(WifiSpot o1, WifiSpot o2) {
						return o1.getRssi().compareTo(o2.getRssi());
					}
				});
			}
		} 
		List<RawData> organized = new ArrayList<RawData>();	
		organized.addAll(data);
		for (int i = 0; i < organized.size(); i++) {
			RawData raw = organized.get(i);
			if(raw.getSamples().size()>10){
				raw.setFirst10();
			}
		}
		return organized;
	}

	public List<WifiSpot> getSamples() {
		return samples;
	}
	
	private void setFirst10(){
		if(samples.size()<=10){
			return ;
		}
		List<WifiSpot> list = new ArrayList<WifiSpot>();
		for (int i = 0; i < 10; i++) {
			list.add(samples.get(i));
		}
		samples=list;
	}
}
