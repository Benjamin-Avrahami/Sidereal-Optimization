
public class Scorable implements Resource {
	private final String name;
	private final double tradeVal;
	private final String scorableType; // Narrow(er) category it belongs to
	private final String coveredWild; // Wild cube that could substitute for this

	// Setting constants for each scorable type
	public Scorable(String resourceName) {
		name = resourceName;
		if (getName().equals("brown") || getName().equals("white") || getName().equals("green") || getName().equals("small grey")) {
			tradeVal = 1.0/3.0;
			scorableType = "small";
			coveredWild = "small grey";
		}
		else if (getName().equals("yellow") || getName().equals("black") || getName().equals("blue") || getName().equals("large grey")) {
			tradeVal = 1.0/2.0;
			scorableType = "large";
			coveredWild = "large grey";
		}
		else {
			scorableType = "scorable";
			coveredWild = ""; // no wilds
			if (getName().equals("ship")) {
				tradeVal = 1.0/3.0;
			}
			else if (getName().equals("octagon") || getName().equals("victory point")) {
				tradeVal = 1.0;
			}
			else {
				tradeVal = 0.0;
			}
		}
	}

	// Get the name of the resource
	public String getName() {
		return name;
	}

	// See whether the other resource provided is the same resource as this
	public boolean equals(Object otherResource) {
		return otherResource instanceof Scorable && this.getName().equals(((Scorable)otherResource).getName());
	}

	// Allowing for hashing
	public int hashCode() {
		return name.hashCode();
	}


	// Suggested trade value (anchored to 1 victory point = 1)
	public double tradeValue() {
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

	public boolean isWild() {
		return getName().equals(wildType());
	}
	
	// Cubes are defined as objects that have intrinsic value
	public String objectType() {
		if (tradeValue() > 0) return "Cube";
		return "Other";
	}

}
