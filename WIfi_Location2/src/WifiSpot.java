
public class WifiSpot {
	/**
	 * @author Yair Ivgi 
	 * this class represent one sample of a WiFi point and all of its information.
	 */
	
	private String mac;
	private String ssid;
	private String firstSeen;
	private String channel;
	private String rssi;
	private String currentLatitude;
	private String currentLongitude;
	private String altitudeMeters;
	private String type;
	private String id;

	/**
	 * @author Yair Ivgi 
	 * the constructor of WiFi point 
	 */

	public WifiSpot(String id,String mac,String ssid,String firstSeen,String channel,String rssi,String currentLatitude,String currentLongitude,String altitudeMeters){
		this.id=id;
		this.mac=mac;
		this.ssid=ssid;
		this.firstSeen=firstSeen;
		this.channel=channel;
		this.rssi=rssi;
		this.currentLatitude=currentLatitude;
		this.currentLongitude=currentLongitude;
		this.altitudeMeters=altitudeMeters;
	}
	
	/**
	 * @author Yair Ivgi 
	 * constructor of WiFi point rssi,Lat,Lon,Alt
	 */
	
	public WifiSpot(String rssi,String currentLatitude,String currentLongitude,String altitudeMeters){
		this.rssi=rssi;
		this.currentLatitude=currentLatitude;
		this.currentLongitude=currentLongitude;
		this.altitudeMeters=altitudeMeters;
		this.ssid=null;
		this.firstSeen=null;
		this.mac=null;
		this.channel=null;
		this.id=null;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getMac() {
		return mac;
	}

	public String getSsid() {
		return ssid;
	}

	public String getTime() {
		return firstSeen;
	}

	public String getChannel() {
		return channel;
	}

	public String getRssi() {
		return rssi;
	}

	public String getCurrentLatitude() {
		return currentLatitude;
	}

	public String getCurrentLongitude() {
		return currentLongitude;
	}

	public String getAltitudeMeters() {
		return altitudeMeters;
	}
}
