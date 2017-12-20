import java.util.ArrayList;
import java.util.List;

/**
 * @Description The class calculating center weight of points that the algorithms let
 * @author Yair Ivgi
 */

public class AveragingElaborateCoordinate {

	//for algo1
	public WifiSpot centerOfPoints(List <WifiSpot> points){
		List <WifiSpot> wpoints = new  ArrayList<WifiSpot>();
		String id = points.get(0).getId();
		String ssid = points.get(0).getSsid();
		String time = points.get(0).getTime();
		String channel = points.get(0).getChannel();
		String mac = points.get(0).getMac();
		String rssi = points.get(0).getRssi();
		for(int i=0; i<points.size(); i++){//calculate weight
			double tempWeigth = Double.parseDouble(points.get(i).getRssi());
			double sigWeight = 1/Math.pow(tempWeigth, 2);
			double wLat = sigWeight * Double.parseDouble(points.get(i).getCurrentLatitude());
			double wLon = sigWeight * Double.parseDouble(points.get(i).getCurrentLongitude());
			double wAlt = sigWeight * Double.parseDouble(points.get(i).getAltitudeMeters());
			WifiSpot p = new WifiSpot(id,mac,ssid,time,channel,String.valueOf(sigWeight),String.valueOf(wLat),String.valueOf(wLon),String.valueOf(wAlt));
			wpoints.add(p);
		}
		//sum weights
		double sigSWeight = 0, swLat = 0, swLon = 0, swAlt = 0;
		for (int i = 0; i < wpoints.size(); i++) {
			sigSWeight +=Double.parseDouble(wpoints.get(i).getRssi());
			swLat += Double.parseDouble(wpoints.get(i).getCurrentLatitude());
			swLon += Double.parseDouble(wpoints.get(i).getCurrentLongitude());
			swAlt += Double.parseDouble(wpoints.get(i).getAltitudeMeters());
		}
		if(sigSWeight != 0){//almost always the statement is true
			swLat = swLat / sigSWeight;
			swLon = swLon / sigSWeight;
			swAlt = swAlt / sigSWeight;
		} 
		WifiSpot result = new WifiSpot(id,mac,ssid,time,channel,rssi, String.valueOf(swLat), String.valueOf(swLon),  String.valueOf(swAlt));
		return result;
	}
	//for algo2
	public WifiSpot centerWeightOfPoints(List <WifiSpot> points){
		List <WifiSpot> wpoints = new  ArrayList<WifiSpot>();
		String id = points.get(0).getId();
		String ssid = points.get(0).getSsid();
		String time = points.get(0).getTime();
		String channel = points.get(0).getChannel();
		String mac = points.get(0).getMac();
		String rssi = points.get(0).getRssi();
		for(int i=0; i<points.size(); i++){//calculate weight
			double sigWeight = Double.parseDouble(points.get(i).getRssi());
			double wLat = sigWeight * Double.parseDouble(points.get(i).getCurrentLatitude());
			double wLon = sigWeight * Double.parseDouble(points.get(i).getCurrentLongitude());
			double wAlt = sigWeight * Double.parseDouble(points.get(i).getAltitudeMeters());
			WifiSpot p = new WifiSpot(id,mac,ssid,time,channel,String.valueOf(sigWeight),String.valueOf(wLat),String.valueOf(wLon),String.valueOf(wAlt));
			wpoints.add(p);
		}
		//sum weights
		double sigSWeight = 0, swLat = 0, swLon = 0, swAlt = 0;
		for (int i = 0; i < wpoints.size(); i++) {
			sigSWeight +=Double.parseDouble(wpoints.get(i).getRssi());
			swLat += Double.parseDouble(wpoints.get(i).getCurrentLatitude());
			swLon += Double.parseDouble(wpoints.get(i).getCurrentLongitude());
			swAlt += Double.parseDouble(wpoints.get(i).getAltitudeMeters());
		}
		if(sigSWeight != 0){//almost always the statement is true
			swLat = swLat / sigSWeight;
			swLon = swLon / sigSWeight;
			swAlt = swAlt / sigSWeight;
		} 
		WifiSpot result = new WifiSpot(id,mac,ssid,time,channel,rssi, String.valueOf(swLat), String.valueOf(swLon),  String.valueOf(swAlt));
		return result;
	}
}
