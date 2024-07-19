
public class Scorable extends Resource {
	private double tradeVal;
	private String scorableType; // Narrow(er) category it belongs to
	private String coveredWild; // Wild sube that could substitute for this
	
	// Setting constants for each scorable type
	public Resource(String resourceName) {
		name = resourceName;
		if (name.equals("brown") || name.equals("white") || name.equals("green") || name.equals("small grey")) {
			tradeVal = 1.0/3.0;
			scorableType = "small";
			coveredWild = "small grey";
		}
		else if (name.equals("yellow") || name.equals("black") || name.equals("blue") || name.equals("large grey")) {
			tradeVal = 1.0/2.0;
			scorableType = "large";
			coveredWild = "large grey";
		}
		else {
			scorableType = "scorable";
			coveredWild = ""; // no wilds
			if (name.equals("ship")) {
				tradeVal = 1.0/3.0;
			}
			else if (name.equals("octagon") || name.equals("victory point")) {
				tradeVal = 1.0;
			}
			else {
				tradeVal = 0.0;
			}
		}
	}
	
	
	
	// Suggested trade value (anchored to 1 victory point = 1)
	public abstract double tradeValue() {
		return tradeVal;
	}
	
	// Victory point total of the resource at the end of the game
	public double scoringValue() {
		if (getName().equals("victory point")) {
			return tradeValue();
		}
		else {
			return tradeValue()/2;
		}
	}
	
	// The wild type for the class of scorable
	public String wildType() {
		return coveredWild;
	}
	
	// The overarching name for any element of this class
	public String genericName() {
		return "scorable";
	}
	
	public boolean isWild() {
		return getName().equals(wildType());
	}
	
}