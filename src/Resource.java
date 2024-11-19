
public class Resource implements Consumable {
	private final String name;
	private final double tradeVal;
	private boolean donation;

	// Setting constants for each scorable type
	public Resource(String resourceName) {
		name = resourceName;
		if (getName().equals("brown") || getName().equals("white") || getName().equals("green") || getName().equals("small grey")) {
			tradeVal = 1.0/3.0;
		}
		else if (getName().equals("yellow") || getName().equals("black") || getName().equals("blue") || getName().equals("large grey")) {
			tradeVal = 1.0/2.0;
		}
		else {
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
		return otherResource instanceof Resource && this.getName().equals(((Resource)otherResource).getName());
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
	
	// Change whether the resource is a donation good or not
	public void setDonationValue(boolean donated) {
		donation = donated;
	}
	
	public boolean isDonation() {
		return donation;
	}
		
	
	// Cubes are defined as objects that have intrinsic value
	public String objectType() {
		if (tradeValue() > 0) return "Cube";
		return "Other";
	}

}
