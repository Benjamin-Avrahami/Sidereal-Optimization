
import java.util.*;

public class ConverterSide {
	private String name;
	private ArrayList<ConverterFeatures> converters;
	
	
	// Initializes converter side
	// Assumes all options are run during the same phase (trade/economy)
	public ConverterSide(String sideName, ArrayList<Converter> sideConverters, boolean tradeRun) {
		name = sideName;
		converters = new ArrayList<ConverterFeatures>();
		for (int i = 0; i < sideConverters.size(); i++) {
			converters.add(new ConverterFeatures(sideConverters.get(i), sideName + "_" + (i+1), tradeRun));
		}
	}
	
	
	public String getName() {
		return name;
	}
	
	// Returns number of converters on the converter side
	public int numConverterOptions() {
		return converters.size();
	}
	
	// Runs the 0-th converter and returns the result (successful/unsuccessful)
	public boolean run(ResourceCollection initialResources, ResourceCollection futureResources) {
		return run(0, initialResources, futureResources);
	}
	
	// Runs the index-th converter and returns the result (successful/unsuccessful)
	public boolean run(int index, ResourceCollection initialResources, ResourceCollection futureResources) {
		return converters.get(index).runConverter(initialResources, futureResources);
	}
	
	
	// Prints information about the converter side
	public void display() {
		System.out.println(name);
		for (int i = 0; i < converters.size(); i++) {
			System.out.println(i+1);
			converters.get(i).display();
		}
	}
	

}