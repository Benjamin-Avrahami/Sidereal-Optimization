import java.util.*;

public class GenericConverterCard extends ConverterCard {

	private String unupgradedConverterName;
	private String upgradedConverterName;
	private boolean inPlay;
	private ArrayList<ArrayList<ConverterFeatures>> cardConverters; // 1st set of converters is unupgraded, 2nd is upgraded
	private boolean upgraded;
	private ArrayList<ConverterFeatures> entryResources; // costs and benefits to putting the card in play
	private ArrayList<ConverterFeatures> upgradeResources; // costs and benefits to upgrading the card
	
	
	// Assumes 1 converter on front and back side run during economy, no cost to enter play, (up to) 2 upgrades run during trade
	public GenericConverterCard(String unupgradedName, Converter unupgradedConverter, String upgradedName, Converter upgradedConverter, ConverterCard upgrade1Cost, ConverterCard upgrade2Cost) {
		ResourceCollection card1Cost = new BasicResourceCollection();
		ResourceCollection card2Cost = new BasicResourceCollection();
		card1Cost.add(upgrade1Cost);
		card2Cost.add(upgrade2Cost);
		Converter upgrade1Transaction = new Converter(card1Cost, new BasicResourceCollection());
		Converter upgrade2Transaction = new Converter(card2Cost, new BasicResourceCollection());
		ArrayList<Converter> upgradeCosts = new ArrayList<Converter>();
		upgradeCosts.add(upgrade1Transaction);
		upgradeCosts.add(upgrade2Transaction);
		ArrayList<Converter> frontRun = new ArrayList<Converter>();
		ArrayList<Converter> backRun = new ArrayList<Converter>();
		frontRun.add(unupgradedConverter);
		backRun.add(upgradedConverter);
		constructFeatures(unupgradedName, upgradedName, frontRun, backRun, new ArrayList<Converter>(), upgradeCosts, false);
	}
	

	public GenericConverterCard(String unupgradedConvName, String upgradedConvName, ArrayList<Converter> unupgradedConverters, ArrayList<Converter> upgradedConverters, ArrayList<Converter> entries, ArrayList<Converter> upgrades, boolean tradeRun) {
		constructFeatures(unupgradedConvName, upgradedConvName, unupgradedConverters, upgradedConverters, entries, upgrades, tradeRun);
	}
	
	public void constructFeatures(String unupgradedConvName, String upgradedConvName, ArrayList<Converter> unupgradedConverters, ArrayList<Converter> upgradedConverters, ArrayList<Converter> entries, ArrayList<Converter> upgrades, boolean tradeRun) {
		ArrayList<ConverterFeatures> firstConverters = arrayConverterToFeatures(unupgradedConverters, unupgradedConvName, tradeRun);
		ArrayList<ConverterFeatures> secondConverters = arrayConverterToFeatures(upgradedConverters, upgradedConvName, tradeRun); //Assumes both sides are run at the same time
		cardConverters = new ArrayList<ArrayList<ConverterFeatures>>();
		cardConverters.add(firstConverters);
		cardConverters.add(secondConverters);
		entryResources = arrayConverterToFeatures(entries, unupgradedConvName + "_entry", true); // Assumes entry during trade
		upgradeResources = arrayConverterToFeatures(upgrades, unupgradedConvName + "_upgrade", true); // Assumes upgrade during trade
		inPlay = entryResources.isEmpty();
		unupgradedConverterName = unupgradedConvName;
		upgradedConverterName = upgradedConvName;
		upgraded = false;
	}

	private ArrayList<ConverterFeatures> arrayConverterToFeatures(ArrayList<Converter> convs, String convName, boolean instRun) {
		ArrayList<ConverterFeatures> features = new ArrayList<ConverterFeatures>();
		for (int i = 0; i < convs.size(); ++i) {
			String currConvName = convName;
			if (convs.size() > 1) {
				currConvName = currConvName + "_" + i;
			}
			features.add(new ConverterFeatures(convs.get(i), currConvName, instRun));
		}
		return features;
	}


	// Get the name of the resource
	public String getName() {
		if (isUpgraded()) {
			return upgradedConverterName;
		}
		else {
			return unupgradedConverterName;
		}
	}

	// See whether the other resource provided is the same resource as this
	public boolean equals(Object otherResource) {
		return otherResource instanceof GenericConverterCard && this.getName().equals(((GenericConverterCard)otherResource).getName());
	}

	// Allowing for hashing
	public int hashCode() {
		return getName().hashCode();
	}


	public boolean isUpgraded() {
		return upgraded;
	}

	public boolean isInPlay() {
		return inPlay;
	}

	private boolean runConverterFeatureFromArray(ArrayList<ConverterFeatures> convarry, int index, ResourceCollection startingResources, ResourceCollection futureResources) {
		if (index >= convarry.size()) return false;
		return convarry.get(index).runConverter(startingResources, futureResources);
	}

	// Runs the first legally available (not taking into account resources) converter on the card
	public boolean run(ResourceCollection initialResources, ResourceCollection futureResources) {
		return run(0, initialResources, futureResources);
	}

	public boolean run(int index, ResourceCollection initialResources, ResourceCollection futureResources) {
		if (!isInPlay()) return false;
		if (isUpgraded()) {
			return runConverterFeatureFromArray(cardConverters.get(1), index, initialResources, futureResources);
		}
		else {
			return runConverterFeatureFromArray(cardConverters.get(0), index, initialResources, futureResources);
		}
	}

	public boolean enterPlay(ResourceCollection initialResources, ResourceCollection futureResources) {
		return enterPlay(0, initialResources, futureResources);
	}

	public boolean enterPlay(int index, ResourceCollection initialResources, ResourceCollection futureResources) {
		if (isInPlay()) return false;
		return runConverterFeatureFromArray(entryResources, index, initialResources, futureResources);
	}

	public boolean upgrade(ResourceCollection initialResources, ResourceCollection futureResources) {
		return upgrade(0, initialResources, futureResources);
	}

	public boolean upgrade(int index, ResourceCollection initialResources, ResourceCollection futureResources) {
		if (!isInPlay()) return false;
		if (isUpgraded()) return false;
		return runConverterFeatureFromArray(upgradeResources, index, initialResources, futureResources);
	}
	
	
	public void display() {
		System.out.println("Entry costs:");
		for (int i = 0; i < entryResources.size(); ++i) {
			System.out.println(i+1);
			entryResources.get(i).display();
		}
		if (inPlay) {
			System.out.print("This card is in play. ");
			if (!upgraded) {
				System.out.println("On Side 1.");
			}
			else {
				System.out.println("On Side 2.");
			}
		}
		else {
			System.out.println("This card is not in play.");
		}
		System.out.println("Side 1 - " + unupgradedConverterName);
		for (int i = 0; i < cardConverters.get(0).size(); ++i) {
			System.out.println(i+1);
			cardConverters.get(0).get(i).display();
		}
		System.out.println("Upgrade costs:");
		for (int i = 0; i < upgradeResources.size(); ++i) {
			System.out.println(i+1);
			upgradeResources.get(i).display();
		}
		System.out.println("Side 2 - " + upgradedConverterName);
		for (int i = 0; i < cardConverters.get(1).size(); ++i) {
			System.out.println(i+1);
			cardConverters.get(1).get(i).display();
		}
		System.out.println("--------- End Card ----------");
	}

}
