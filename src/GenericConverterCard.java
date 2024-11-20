import java.util.*;
import java.io.*;

public class GenericConverterCard implements Consumable {
	
	// Each subarray of actions gives a class of converters/actions available at a given time
	// actions[0] is the list of options for the card to enter play, actions[1] is the list of front-side converters,
	// actions[2] is the list of upgrades, actions[3] is the list of back-side converters
	private ArrayList<ArrayList<ConverterFeatures>> actions;
	// The side of the converter that is currently accessible
	// 1 is front side, 2 is back side, 0 is not in play
	// Gives ability to access indices 2*currentSide - 1 (converters) and 2*currentSide of actions (entry/upgrade) if in bounds
	private int currentSide;
	// Name of each side of the card
	// Should be of length actions.size()/2
	private ArrayList<String> names;
	
	
	public GenericConverterCard(ArrayList<ArrayList<ConverterFeatures>> allCardConverters, ArrayList<String> cardNames) {
		// Invalid converter (must have at least an entry cost (can be null) and a normal conversion)
		if (allCardConverters.size() < 2) {
			return;
		}
		
		// Copying allCardConverters into actions
		actions = new ArrayList<ArrayList<ConverterFeatures>>();
		for (int i = 0; i < allCardConverters.size(); i++) {
			subactions = new ArrayList<ConverterFeatures>();
			for (int j = 0; j < allCardConverters.get(i).size(); j++) {
				subactions.add(allCardConverters.get(i).get(j).getCopy());
			}
			actions.add(subactions);
		}
		
		// If no converters to enter play, goes directly to the front side
		if (allCardConverters.get(0).size() == 0) {
			currentSide = 1;
		}
		// Otherwise, only can enter play
		else {
			currentSide = 0;
		}
		
		names = new ArrayList<String>(cardNames);
	}


	// Returns the name of the side that the card is on
	// If not in play, returns the name of the front side
	public String getName() {
		if (currentSide == 0) {
			return names.get(0);
			else {
				return names.get(currentSide-1);
			}
		}
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
		return names.get(0).hashCode();
	}

	// Checks whether the card has been upgraded at least once
	public boolean isUpgraded() {
		return currentSide > 1;
	}
	
	// Checks whether the card cannot be upgraded any more
	// Since the basic card is only upgraded once, this is the same as isUpgraded
	public boolean isFullyUpgraded() {
		return isUpgraded();
	}

	public boolean isInPlay() {
		return currentSide > 0;
	}
	
	// Returns a list of all the converters that could be used to enter play
	// All functions giving converters back intentionally allows them to be mutable within the card
	public ArrayList<ConverterFeatures> enterPlayOptions() {
		return new ArrayList<ConverterFeatures>(actions.get(0));
	}
	
	// Returns a list of all the converters that can be run on the front side
	public ArrayList<ConverterFeatures> frontSideOptions() {
		return new ArrayList<ConverterFeatures>(actions.get(1));
	}
	
	// Returns a list of all the converters that could be used to upgrade to the back side
	public ArrayList<ConverterFeatures> upgradeOptions() {
		return new ArrayList<ConverterFeatures>(actions.get(2));
	}
	
	// Returns a list of all the converters that can be run on the back side
	public ArrayList<ConverterFeatures> backSideOptions() {
		return new ArrayList<ConverterFeatures>(actions.get(3));
	}
	
	
	// Returns a list of all the converters (not upgrade/entry) currently available to run
	// If not in play, returns empty (since no converters can be run)
	public ArrayList<ConverterFeatures> currentSideOptions() {
		if (!isInPlay) {
			return new ArrayList<ConverterFeatures>();
		}
		return new ArrayList<ConverterFeatures>(actions.get(2*currentSide-1));
	}
	
	
	// Returns the number of converters available on the card
	// If not in play, returns 0
	public int currentNumberOfOptions() {
		return currentSideOptions().size();
	}
	
	
	// Are there conditions on the converter (the player running it, game turn, etc.) that render it ineligible to be run?
	// If yes, return false (ineligible), otherwise return true (eligible)
	// Can be expanded by subclasses for further conditions
	public boolean isEligibleToRun(ConverterUseRequest req) {
		return true;
	}
	
	// Get the converter pointed to by the request, if valid and eligible to be run
	// If not eligible/valid, returns null
	private ConverterFeatures getRequestConverter(ConverterUseRequest req) {
		if (req.getRequestType().equals("Enter Play")) {
			if (isInPlay()) {
				return null;
			}
			ArrayList<ConverterFeatures> enters = enterPlayOptions();
			if (req.getConverterChoice() > enters.size()) {
				return null;
			}
			return enters.get(req.getConverterChoice());
		}
		else if (req.getRequestType().equals("Upgrade")) {
			if (isFullyUpgraded()) {
				return null;
			}
			ArrayList<ConverterFeatures> upgrades = upgradeOptions();
			if (req.getConverterChoice() > upgrades.size()) {
				return null;
			}
			return upgrades.get(req.getConverterChoice());
		}
		else if (req.getRequestType().equals("Convert")) {
			if (!isInPlay()) {
				return null;
			}
			ArrayList<ConverterFeatures> converts = currentSideOptions();
			if (req.getConverterChoice() > converts.size()) {
				return null;
			}
			return converts.get(req.getConverterChoice());
		}
		else {
			return null;
		}
	}
	
	// Attempts to fulfill the request sent to the card by the player to run/upgrade/enter play with one of the converters
	// If successful, modifies the player's resources and returns true; if failed for any reason, returns false and does not change
	public boolean useConverter(ConverterUseRequest req) {
		// Ineligible to run for some reason
		if (!isEligibleToRun(req)) {
			return false;
		}
		ConverterFeatures conv = getRequestConverter(req);
		// Bad request
		if (conv == null) {
			return false;
		}
		// Wrong phase
		if (!(req.getGame().currentPhase().equals(conv.getPhaseRun()))) {
			return false;
		}
		// Check resource choice
		
		// conv.execute()
	}

	
	// Prints information about one collection of converters
	public void displayConverterSide(ArrayList<ConverterFeatures> converterSide) {
		for (int i = 0; i < converterSide.size(); i++) {
			System.out.print(i+1);
			System.out.print(": ");
			converterSide.get(i).display();
		}
	
	// Prints information about the converter
	public void display() {
		System.out.println("Entry costs:");
		displayConverterSide(enterPlayOptions());
		if (isInPlay()) {
			System.out.print("This card is in play ");
			if (!isUpgraded) {
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
		displayConverterSide(frontSideOptions());
		System.out.println("Upgrade costs:");
		displayConverterSide(upgradeOptions());
		System.out.println("Side 2 - ");
		displayConverterSide(backSideOptions());
		System.out.println("--------- End Card ----------");
	}

}
