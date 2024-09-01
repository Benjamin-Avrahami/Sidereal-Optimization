

import java.util.*;
import java.io.*;

public class SetupMain {
	public static void main(String[] args) {
		ResourceCollection rscs = new BasicResourceCollection();
		
		TabCSVReader tcrdr = new TabCSVReader("src/Sidereal Tracker - Game Setup 1.tsv"); // Relative to where code is called from - currently called from bash script in parent directory

		while (tcrdr.hasNextLine()) {
			tcrdr.nextLine();
			String value = tcrdr.getLineValue("Starting Resources");
			Scanner s3 = new Scanner(value);
			s3.useDelimiter(", ");
			while (s3.hasNext()) {
				String resourceAdd = s3.next();
				int resourceAmount = Integer.parseInt(resourceAdd.substring(0, resourceAdd.indexOf(" ")));
				String resourceName = resourceAdd.substring(resourceAdd.indexOf(" ")+1);
				Resource testWhite = new Scorable("white");
				Resource rsc = new Scorable(resourceName);
				rscs.add(rsc, resourceAmount);
				System.out.println(resourceAdd);
				rscs.display();
			}
		}
	}
}
