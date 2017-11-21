public class WifiSpot {

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

	public String getFirstSeen() {
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