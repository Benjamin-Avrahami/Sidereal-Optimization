

import java.util.*;
import java.io.*;

public class SetupMain {
	
	public static ResourceCollection combineCommaSeparatedStringToCollection(String value) {
		ResourceCollection rescoll = new BasicResourceCollection();
		if (value.equals("None")) {
			return rescoll;
		}
		Scanner s = new Scanner(value);
		s.useDelimiter(", ");
		while (s.hasNext()) {
			String resourceAdd = s.next();
			int resourceAmount = Integer.parseInt(resourceAdd.substring(0, resourceAdd.indexOf(" ")));
			String resourceName = resourceAdd.substring(resourceAdd.indexOf(" ")+1);
			Resource rsc = new Scorable(resourceName);
			rescoll.add(rsc, resourceAmount);
		}
		return rescoll;
	}
	
	// same as combineCommaSeparatedStringToCollection, but ignores anything in parentheses (donations/stealing)
	public static ResourceCollection combineCommaSeparatedStringToSoloCollection(String value) {
		ResourceCollection rescoll = new BasicResourceCollection();
		if (value.equals("None")) {
			return rescoll;
		}
		Scanner s = new Scanner(value);
		s.useDelimiter(", ");
		while (s.hasNext()) {
			String resourceAdd = s.next();
			if (resourceAdd.indexOf("(") != -1) {
				resourceAdd = resourceAdd.substring(0, resourceAdd.indexOf("(")); // Also ignores space before open paren
			}
			int resourceAmount = Integer.parseInt(resourceAdd.substring(0, resourceAdd.indexOf(" ")));
			String resourceName = resourceAdd.substring(resourceAdd.indexOf(" ")+1);
			Resource rsc = new Scorable(resourceName);
			rescoll.add(rsc, resourceAmount);
		}
		return rescoll;
	}
	
	
	public static void combineInitialResources() {
		ResourceCollection rscs = new BasicResourceCollection();
		
		TabCSVReader tcrdr = new TabCSVReader("src/Sidereal Tracker - Game Setup 1.tsv"); // Relative to where code is called from - currently called from bash script in parent directory

		while (tcrdr.hasNextLine()) {
			tcrdr.nextLine(); //first call skips header line
			String value = tcrdr.getLineValue("Starting Resources");
			
			ResourceCollection subcoll = combineCommaSeparatedStringToCollection(value);
			rscs.addAll(subcoll);
			
			subcoll.display();
		}
		rscs.display();
	}
	
	public static void createConverters() {
		TabCSVReader tcrdr = new TabCSVReader("src/Sidereal Tracker - Converters.tsv");
		
		while (tcrdr.hasNextLine()) {
			tcrdr.nextLine();
			
		}
	}
	
	
	public static void main(String[] args) {
		combineInitialResources();
	}
}
