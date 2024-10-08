import java.util.*;
import java.io.*;

public class GenericConverterCard extends ConverterCard {
	
	private boolean inPlay;
	private boolean upgraded;
	
	private ConverterSide frontSide;
	private ConverterSide backSide;
	
	// costs for entry/upgrading are not actual sides of the converter, but have similar behavior of running one out of several converters
	private ConverterSide entrySide; // costs and benefits to putting the card in play
	private ConverterSide upgradeSide; // costs and benefits to upgrading the card
	
	
	

	public GenericConverterCard(ConverterSide side1, ConverterSide side2, ArrayList<Converter> entries, ArrayList<Converter> upgrades) {
		frontSide = side1;
		backSide = side2;
		entrySide = new ConverterSide(side1.getName() + "_entry", entries, true); // Assumes entry during trade
		upgradeSide = new ConverterSide(side1.getName() + "_upgrade", upgrades, true); // Assumes upgrade during trade
		inPlay = entrySide.numConverterOptions() == 0;
		upgraded = false;
	}
	
	
	// Returns current side that the card is on
	// Will still return front side if card is not in play
	private ConverterSide currentSide() {
		if (!isUpgraded()) return frontSide;
		else return backSide;
	}


	// Get the name of the resource
	public String getName() {
		return currentSide().getName();
	}
	
	public String objectType() {
		return "Converter";
	}

	// See whether the other resource provided is the same resource as this
	// If on opposite sides of the same card, will return unequal
	public boolean equals(Object otherResource) {
		return otherResource instanceof GenericConverterCard && this.getName().equals(((GenericConverterCard)otherResource).getName());
	}

	// Allowing for hashing
	// Only checks initial name so that hash code does not change while in a resource collection
	public int hashCode() {
		return frontSide.getName().hashCode();
	}


	public boolean isUpgraded() {
		return upgraded;
	}

	public boolean isInPlay() {
		return inPlay;
	}
	
	// Returns the number of converters available on the card
	// If not in play, returns 0
	public int numConverterOptions() {
		if (!isInPlay()) return 0;
		return currentSide().numConverterOptions();
	}


	// Runs the first legally available (not taking into account resources) converter on the card
	// Returns whether running the converter was successful
	public boolean run(ResourceCollection initialResources, ResourceCollection futureResources) {
		return run(0, initialResources, futureResources);
	}

	// Runs the indexth available converter (0-indexed)
	// Returns whether running the converter was successful
	public boolean run(int index, ResourceCollection initialResources, ResourceCollection futureResources) {
		if (!isInPlay()) return false;
		return currentSide().run(index, initialResources, futureResources);
	}

	// Attempts to enter play using the first method available (if there are several)
	// Returns whether it was successful
	public boolean enterPlay(ResourceCollection initialResources, ResourceCollection futureResources) {
		return enterPlay(0, initialResources, futureResources);
	}

	// Attempts to enter play using the indexth method (0-indexed)
	// Returns whether it was successful
	public boolean enterPlay(int index, ResourceCollection initialResources, ResourceCollection futureResources) {
		if (isInPlay()) return false;
		inPlay = entrySide.run(index, initialResources, futureResources);
		return inPlay;
	}

	// Attempts to upgrade the converter using the first method available (if there are several)
	// Returns whether it was successful
	public boolean upgrade(ResourceCollection initialResources, ResourceCollection futureResources) {
		return upgrade(0, initialResources, futureResources);
	}

	// Attempts to upgrade the converter using the indexth method (0-indexed)
	// Returns whether it was successful
	public boolean upgrade(int index, ResourceCollection initialResources, ResourceCollection futureResources) {
		if (!isInPlay()) return false;
		if (isUpgraded()) return false;
		upgraded = upgradeSide.run(index, initialResources, futureResources);
		return upgraded;
	}
	
	// Prints information about the converter
	public void display() {
		System.out.println("Entry costs:");
		entrySide.display();
		if (inPlay) {
			System.out.print("This card is in play ");
			if (!upgraded) {
				System.out.println("on Side 1.");
			}
			else {
				System.out.println("on Side 2.");
			}
		}
		else {
			System.out.println("This card is not in play.");
		}
		System.out.println("Side 1 - ");
		frontSide.display();
		System.out.println("Upgrade costs:");
		upgradeSide.display();
		System.out.println("Side 2 - ");
		backSide.display();
		System.out.println("--------- End Card ----------");
	}

}
