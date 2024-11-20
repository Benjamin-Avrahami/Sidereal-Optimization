// A converter, but with additional game features determining ability to run
public class ConverterFeatures extends Converter {
	
	private String phase; // trade or economy
	private boolean hasBeenRun; // whether the converter has been run or not this turn
	
	public ConverterFeatures() {
		super();
		phase = "";
		hasBeenRun = false;
	}

	public ConverterFeatures(ResourceCollection inputResources, ResourceCollection outputResources) {
		super(inputResources, outputResources);
		phase = "";
		hasBeenRun = false;
	}
	
	public ConverterFeatures getCopy() {
		ConverterFeatures convFeat = new ConverterFeatures(getInputs(), getOutputs());
		convFeat.setPhaseRun(getPhaseRun());
		convFeat.hasBeenRun = this.hasBeenRun;
		return convFeat;
	}
	
	public void setPhaseRun(String phaseRun) {
		phase = phaseRun;
	}
	
	public String getPhaseRun() {
		return phase;
	}
	
	// Can only be run if it has not yet been run this turn
	public boolean isEligibleToRun() {
		return !hasBeenRun;
	}
	
	// Given the phase, runs the correct version of the converter
	public boolean execute(ResourceCollection startingResources, ResourceCollection futureResources) {
		if (!isEligibleToRun) {
			return false;
		}
		
		hasBeenRun = true;
		if (getPhaseRun().equals("Trade Phase")) {
			return instantExecution(startingResources, futureResources);
		}
		else if (getPhaseRun().equals("Economy Phase")) {
			return delayedExecution(startingResources, futureResources);
		}
		else {
			return false;
		}
	}
	
	
	public void display() {
		super.display();
		System.out.println("Runs during " + getPhaseRun());
	}
}