
// ship, octagon, victory point
public class SmallCube extends Scorable {
	public double tradeValue() {
		if (getName().equals("ship")) {
			return 1.0/3.0;
		}
		else if (getName().equals("octagon") || getName().equals("victory point")) {
			return 1.0;
		}
		else {
			return 0.0;
		}
	}
	
	// No wild values for the other scorable options
	public String wildType() {
		return "";
	}
}