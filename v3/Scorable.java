
public abstract class Scorable extends Resource {
	// Suggested trade value (anchored to 1 victory point = 1)
	public abstract double tradeValue();
	
	// Victory point total of the resource at the end of the game
	public double scoringValue() {
		if (getName().equals("victory point")) {
			return tradeValue();
		}
		else {
			return tradeValue()/2;
		}
	}
	
	// The wild type in the class
	public abstract String wildType();
	
	// The overarching name for any element of this class
	public String genericName() {
		return "scorable";
	}
	
	public boolean isWild() {
		return getName().equals(wildType());
	}
	
}