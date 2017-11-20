import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.PolyStyle;
import de.micromata.opengis.kml.v_2_2_0.Style;

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
	
	public void write(List<CSVRecord> records){
		final Kml kml = new Kml();
		Document doc = kml.createAndSetDocument().withName("JAK Example1").withOpen(true);

		// create a Folder
		Folder folder = doc.createAndAddFolder();
		folder.withName("Continents with Earth's surface").withOpen(true);

		// create Placemark elements
		createPlacemarkWithChart(doc, folder, 93.24607775062842, 47.49808862281773, "Asia", 30);
		createPlacemarkWithChart(doc, folder, 19.44601806124206, 10.13133611111111, "Africa", 20);
		createPlacemarkWithChart(doc, folder, -103.5286299241638, 41.26035225962401, "North America", 17);
		createPlacemarkWithChart(doc, folder, -59.96161780270248, -13.27347674076888, "South America", 12);
		createPlacemarkWithChart(doc, folder, 14.45531426360271, 47.26208181151567, "Europe", 7);
		createPlacemarkWithChart(doc, folder, 135.0555272486322, -26.23824399654937, "Australia", 6);

		// print and save
		try {
			kml.marshal(new File("advancedexample1.kml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	    // Style
	    PolyStyle polystyle = KmlFactory.createPolyStyle();
	    polystyle.setColor("#EABCFF");
	    // polystyle.setFill(true);
	    polystyle.setOutline(false);
	    //
	    Kml kml = KmlFactory.createKml();
	    Document document = kml.createAndSetDocument();
	    Placemark pm = document.createAndAddPlacemark();
	    LinearRing linearRing = pm.createAndSetPolygon().createAndAddInnerBoundaryIs().createAndSetLinearRing();
	    linearRing.addToCoordinates(9.184254, 45.443636, 0);
	    linearRing.addToCoordinates(9.183379, 45.434288, 0);
	    linearRing.addToCoordinates(9.224836, 45.431499, 0);
	    linearRing.addToCoordinates(9.184254, 45.443636, 0);
	    pm.createAndAddStyle().setPolyStyle(polystyle);
	    //
	    kml.marshal(new FileWriter("D:/prova.kml"));
	}
	
}
