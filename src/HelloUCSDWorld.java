import de.fhpotsdam.unfolding.providers.Microsoft;
import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello World!
 * 
 * This is the basic stub to start creating interactive maps.
 */
public class HelloUCSDWorld extends PApplet {

	UnfoldingMap map;

	public void setup() {
		size(800, 600, OPENGL);

		//map = new UnfoldingMap(this, new Google.GoogleTerrainProvider());
		map = new UnfoldingMap(this, new Microsoft.HybridProvider());

		map.zoomAndPanTo(14, new Location(32.881, -117.238)); // UCSD

		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
		background(0);
		map.draw();
	}

	public static void main (String[] args) {
		//Add main method for running as application
		PApplet.main(new String(module1.HelloWorld.class.getName()));

	}

}