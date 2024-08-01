import java.util.*;

public class ConverterSet extends ConverterCard {

	private final String unupgradedConverterName;
	private final String upgradedConverterName;
	private boolean inPlay;
	private ArrayList<ArrayList>> cardConverters; // 1st set of converters is unupgraded, 2nd is upgraded
	private boolean upgraded;
	private ArrayList<ConverterFeatures> entryResources; // costs and benefits to putting the card in play
	private ArrayList<Converterfeatures> upgradeResources; // costs and benefits to upgrading the card


	public ConverterSet(String unupgradedConvName, String upgradedConvName, ArrayList<Converter> unupgradedConverters, ArrayList<Converter> upgradedConverters, ArrayList<Converter> entries, ArrayList<Converter> upgrades, boolean tradeRun) {
		firstConverters = arrayConverterToFeatures(unupgradedConverters, unupgradedConvName, tradeRun);
		secondConverters = arrayConverterToFeatures(upgradedConverters, upgradedConvName, tradeRun); //Assumes both sides arun at the same time
		cardConverters = new ArrayList<ArrayList<ConverterFeatures>>;
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
	public String getName(); {
		return converterName;
	}

	// See whether the other resource provided is the same resource as this
	public boolean equals(Resource otherResource) {
		return otherResource instanceOf ConverterSet && this.getName().equals(other.getName());
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

	// Given a (wrapped) converter and resources, run the converter
	private boolean runConverter(ConverterFeatures convf, ResourceCollection startingResources, ResourceCollection futureResources) {
		if (convf.instantRun) {
			return convf.conv.instantExecution(startingResources, futureResources);
		}
		else {
			return convf.conv.delayedExecution(startingResources, futureResources);
		}
	}

	private boolean runConverterFeatureFromArray(ArrayList<ConverterFeatures> convarry, int index, ResourceCollection startingResources, ResourceCollection futureResources) {
		if (index >= convarry.size()) return false;
		return runConverter(convarry.get(index), startingResources, futureResources);
	}

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

}
