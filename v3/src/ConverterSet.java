import java.util.*;

public class ConverterSet implements Resource {
	
	String converterName;
	boolean inPlay;
	ArrayList<ConverterFeatures> activeConverters; // converters shown on the current side of the card (may not be usable even if nonempty if not in play)
	ArrayList<ConverterFeatures> inactiveConverters; // converters shown on the opposite side of the card (unable to be used)
	ArrayList<ConverterFeatures> entryResources; // costs and benefits to putting the card in play
	ArrayList<Converterfeatures> upgradeResources; // costs and benefits to upgrading the card
	
	
	public ConverterSet(String convName, Converter unupgradedConverter, Converter upgradedConverter, ArrayList<ConverterSet> upgradeCosts) {
		ArrayList<Converter> unupgradedConverters = new ArrayList<Converter>();
		unupgradedConverters.add(unupgradedConverter);
		ArrayList<Converter> upgradedConverters = new ArrayList<Converter>();
		unupgradedConverters.add(upgradedConverter);
		ArrayList<Converter> upgradeResources = new ArrayList<Converter>();
		for (int i = 0; i < upgradeCosts.size(); ++i) {
			ResourceCollection rsc = new ResourceCollection();
			rsc.add(upgradeCosts.get(i));
			Converter addConv = new Converter(rsc, new ResourceCollection());
			upgradeResources.add(addConv);
		}
		initializeFeatures(convName, unupgradedConverters, upgradedConverters, new ArrayList<Converter>(), upgradeResources, false);
	}
	
	public ConverterSet(String convName, ArrayList<Converter> unupgradedConverters, ArrayList<Converter> upgradedConverters, ArrayList<Converter> entries, ArrayList<Converter> upgrades, boolean tradeRun) {
		initializeFeatures(convName, unupgradedConverters, upgradedConverters, entries, upgrades, tradeRun);
	}
	
	private void initializeFeatures(String convName, ArrayList<Converter> unupgradedConverters, ArrayList<Converter> upgradedConverters, ArrayList<Converter> entries, ArrayList<Converter> upgrades, boolean tradeRun) {
		activeConverters = arrayConverterToFeatures(unupgradedConverters, convName, tradeRun);
		inactiveConverters = arrayConverterToFeatures(upgradedConverters, convName, tradeRun);
		entryResources = arrayConverterToFeatures(entries, convName, true);
		upgradeResources = arrayConverterToFeatures(upgrades, convName, true);
		inPlay = entryResources.isEmpty();
		
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
	
	
}