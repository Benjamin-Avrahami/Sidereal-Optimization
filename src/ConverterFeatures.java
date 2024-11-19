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
	
	// Converter can only run if it the correct game phase to run it in and it hasn't been run yet this turn
	public boolean isEligibleToRun(String currentPhase) {
		return phase.equals(currentPhase) && !(hasBeenRun);
	}
	
	// Copy of parent function, but checks whether eligible to run (and then marks as run) first
	public boolean instantExecution(ResourceCollection startingResources, ResourceCollection futureResources, String currentPhase) {
		if (isEligibleToRun(currentPhase)) {
			hasNotBeenRun = true;
			return instantExecution(startingResources, futureResources);
		}
		else {
			return false;
		}
	}
	
	// Copy of parent function, but checks whether eligible to run (and then marks as run) first
	public boolean delayedExecution(ResourceCollection startingResources, ResourceCollection futureResources, String currentPhase) {
		if (isEligibleToRun(currentPhase)) {
			hasBeenRun = true;
			return delayedExecution(startingResources, futureResources);
		}
		else {
			return false;
		}
	}
	
	public void display() {
		super.display();
		System.out.println("Runs during " + phase);
	}
}