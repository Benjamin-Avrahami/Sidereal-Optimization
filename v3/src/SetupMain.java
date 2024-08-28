

import java.util.*;
import java.io.*;

public class SetupMain {
	public static void main(String[] args) {
		ResourceCollection rscs = new BasicResourceCollection();
		try {
			File f = new File("src/Sidereal Tracker - Game Setup.tsv"); // Relative to where code is called from - currently called from bash script in parent directory
			Scanner s1 = new Scanner(f);
			while (s1.hasNext()) {
				String line = s1.nextLine();
				if (line.length() == 0 || Character.isWhitespace(line.charAt(0))) continue;
				String headerString = "[HEADER]";
				if (line.length() >= headerString.length() && line.substring(0,headerString.length()).equals(headerString)) continue;
				
				if (Character.isDigit(line.charAt(0))) continue; //Bid tracks - to be added later
				Scanner s2 = new Scanner(line);
				s2.useDelimiter("\t");
				int index = 0;
				while (s2.hasNext()) {
					String value = s2.next();
					if (index == 1) {
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
					index += 1;
				}
			}
		}
		catch (FileNotFoundException fe) {
			System.out.println("File not found");
		}
		//System.out.println("Hello World!");
	}
}