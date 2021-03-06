package module3;

//Java utilities libraries

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import de.fhpotsdam.unfolding.providers.Microsoft;
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/**
 * EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 *
 * @author Davide Nastri
 * Date: October 22, 2018
 */
public class EarthquakeCityMap extends PApplet {

    // Less than this threshold is a light earthquake
    public static final float THRESHOLD_MODERATE = 5;
    // Less than this threshold is a minor earthquake
    public static final float THRESHOLD_LIGHT = 4;
    public static final float SIZE_SEVERE_EARTHQUAKE = 15;
    public static final float SIZE_MODERATE_EARTHQUAKE = 10;
    public static final float SIZE_LIGHT_EARTHQUAKE = 5;
    // You can ignore this.  It's to keep eclipse from generating a warning.
    private static final long serialVersionUID = 1L;
    // IF YOU ARE WORKING OFFLINE, change the value of this variable to true
    private static final boolean offline = false;
    /**
     * This is where to find the local tiles, for working without an Internet connection
     */
    public static String mbTilesString = "blankLight-1-3.mbtiles";

    // The map
    private UnfoldingMap map;

    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

    public void setup() {
        size(950, 600, OPENGL);

        if (offline) {
            map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
            earthquakesURL = "2.5_week.atom";    // Same feed, saved Aug 7, 2015, for working offline
        } else {
            // Uncomment this row to use GoogleMapProvider
            //map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
            // Uncomment this row to use Microsoft's HybridProvider
            map = new UnfoldingMap(this, 200, 10, 700, 500, new Microsoft.HybridProvider());
            // IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
            //earthquakesURL = "2.5_week.atom";
        }

        map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this, map);

        // The List you will populate with new SimplePointMarkers
        List<Marker> markers = new ArrayList<Marker>();

        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);

        for (PointFeature eq : earthquakes) {
            SimplePointMarker spm = createMarker(eq);
            markers.add(spm);
        }
        // Add the markers to the map so that they are displayed
        map.addMarkers(markers);
    }

    /* createMarker: A suggested helper method that takes in an earthquake
     * feature and returns a SimplePointMarker for that earthquake
     *
     */
    private SimplePointMarker createMarker(PointFeature feature) {
        // To print all of the features in a PointFeature (so you can see what they are)
        // uncomment the line below.  Note this will only print if you call createMarker
        // from setup
        //System.out.println(feature.getProperties());

        // Create a new SimplePointMarker at the location given by the PointFeature
        SimplePointMarker marker = new SimplePointMarker(feature.getLocation());

        Object magObj = feature.getProperty("magnitude");
        float mag = Float.parseFloat(magObj.toString());

        int red = color(255, 0, 0);
        int yellow = color(255, 255, 0);
        int blue = color(0, 0, 255);

        if (mag > THRESHOLD_MODERATE) {
            marker.setColor(red);
            marker.setRadius(SIZE_SEVERE_EARTHQUAKE);
        } else if (mag > THRESHOLD_LIGHT) {
            marker.setColor(yellow);
            marker.setRadius(SIZE_MODERATE_EARTHQUAKE);
        } else if (mag < THRESHOLD_LIGHT) {
            marker.setColor(blue);
            marker.setRadius(SIZE_LIGHT_EARTHQUAKE);
        }

        return marker;
    }

    public void draw() {
        background(10);
        map.draw();
        addKey();
    }

    // helper method to draw key in GUI
    private void addKey() {
        fill(255, 250, 240);
        rect(10, 10, 175, 290, 7);

        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("Earthquake Key", 50, 75);

        fill(color(255, 0, 0));
        ellipse(50, 125, 15, SIZE_SEVERE_EARTHQUAKE);
        fill(color(255, 255, 0));
        ellipse(50, 175, 10, SIZE_MODERATE_EARTHQUAKE);
        fill(color(0, 0, 255));
        ellipse(50, 225, 5, SIZE_LIGHT_EARTHQUAKE);

        fill(0, 0, 0);
        text("5.0+ Magnitude", 75, 125);
        text("4.0+ Magnitude", 75, 175);
        text("Below 4.0", 75, 225);
    }

    public static void main(String[] args) {
        //Add main method for running as application
        PApplet.main(new String(module3.EarthquakeCityMap.class.getName()));
    }

}
