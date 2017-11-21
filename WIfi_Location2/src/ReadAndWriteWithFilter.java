import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.boehn.kmlframework.kml.Document;
import org.boehn.kmlframework.kml.Kml;
import org.boehn.kmlframework.kml.KmlException;
import org.boehn.kmlframework.kml.Placemark;

public class ReadAndWriteWithFilter {

	public List<CSVRecord> readCsv(String fileName, IFilter filter) throws Exception{
		try {
			File file = new File(fileName);
			Reader in = new FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			List<CSVRecord> filteredRecords = filter.getFiltered(records);
			return filteredRecords;
		} catch (Exception e) {
			throw new Exception("Error reading file\n" + e);		
		}
	}
	
	public void write(List<CSVRecord> records) throws KmlException, IOException {

		// We create a new KML Document
		Kml kml = new Kml();
		// We add a document to the kml
					Document document = new Document();
					kml.setFeature(document);
		for(CSVRecord record : records) {
			// We create a Placemark for the Department of Informatics at the university of Oslo
			int BSignal=highestSignalIndex(record);
			Placemark ifi = new Placemark(record.get("SSID"+BSignal));
			ifi.setDescription("MAC: "+record.get("MAC"+BSignal)+"\n"+" Frequncy: "+record.get("Frequncy"+BSignal)+"\n"+" Signal: "+record.get("Signal"+BSignal)+"\n");
			ifi.setLocation(Double.parseDouble(record.get("Lon")), Double.parseDouble(record.get("Lat")));

			

			// We add the placemark to the Document
			document.addFeature(ifi);
		}

		// We generate the kml file
		kml.createKml("C:\\Users\\user\\Documents\\write\\Ifi.kml");

		// We are done
		System.out.println("The kml file was generated.");
	}

	
	private static void createPlacemarkWithChart(Document document, Folder folder, double longitude, double latitude, 
		    String continentName, int coveredLandmass) {

			int remainingLand = 100 - coveredLandmass;
			Icon icon = new Icon()
			    .withHref("http://chart.apis.google.com/chart?chs=380x200&chd=t:" + coveredLandmass + "," + remainingLand + "&cht=p&chf=bg,s,ffffff00");
			Style style = document.createAndAddStyle();
			style.withId("style_" + continentName) // set the stylename to use this style from the placemark
			    .createAndSetIconStyle().withScale(5.0).withIcon(icon); // set size and icon
			style.createAndSetLabelStyle().withColor("ff43b3ff").withScale(5.0); // set color and size of the continent name

			Placemark placemark = folder.createAndAddPlacemark();
			// use the style for each continent
			placemark.withName(continentName)
			    .withStyleUrl("#style_" + continentName)
			    // 3D chart imgae
			    .withDescription(
			        "<![CDATA[<img src=\"http://chart.apis.google.com/chart?chs=430x200&chd=t:" + coveredLandmass + "," + remainingLand + "&cht=p3&chl=" + continentName + "|remaining&chtt=Earth's surface\" />")
			    // coordinates and distance (zoom level) of the viewer
			    .createAndSetLookAt().withLongitude(longitude).withLatitude(latitude).withAltitude(0).withRange(12000000);
			
			placemark.createAndSetPoint().addToCoordinates(longitude, latitude); // set coordinates
		}

	public static void main(String[] args) throws IOException {
//	    // Style
//	    PolyStyle polystyle = KmlFactory.createPolyStyle();
//	    polystyle.setColor("#EABCFF");
//	    // polystyle.setFill(true);
//	    polystyle.setOutline(false);
//	    //
//	    Kml kml = KmlFactory.createKml();
//	    Document document = kml.createAndSetDocument();
//	    Placemark pm = document.createAndAddPlacemark();
//	    LinearRing linearRing = pm.createAndSetPolygon().createAndAddInnerBoundaryIs().createAndSetLinearRing();
//	    linearRing.addToCoordinates(9.184254, 45.443636, 0);
//	    linearRing.addToCoordinates(9.183379, 45.434288, 0);
//	    linearRing.addToCoordinates(9.224836, 45.431499, 0);
//	    linearRing.addToCoordinates(9.184254, 45.443636, 0);
//	    pm.createAndAddStyle().setPolyStyle(polystyle);
//	    //
//	    kml.marshal(new FileWriter("D:/prova.kml"));
		// The all encapsulating kml element.
		Kml kml = KmlFactory.createKml();
		// Create <Placemark> and set values.
		Placemark placemark = KmlFactory.createPlacemark();
		placemark.setName("Java User Group Hessen - JUGH!");
		placemark.setVisibility(true);
		placemark.setOpen(false);
		placemark.setDescription("die Java User Group Hessen");
		placemark.setStyleUrl("styles.kml#jugh_style");
		 
		// Create <Point> and set values.
		Point point = KmlFactory.createPoint();
		point.setExtrude(false);
		point.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);
		// Add <coordinates>9.444652669565212,51.30473589438118,0<coordinates>.
		point.getCoordinates().add(new Coordinate("9.444652669565212,51.30473589438118,0"));
		 
		placemark.setGeometry(point);      // <-- point is registered at placemark ownership.
		kml.setFeature(placemark);         // <-- placemark is registered at kml ownership.
		kml.marshal(System.out);           // <-- Print the KML structure to the console.
	}
	
}
