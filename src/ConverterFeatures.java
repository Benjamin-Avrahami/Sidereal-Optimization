// A converter, but with the additional game feature of when the converter is allowed to run
public class ConverterFeatures extends Converter {
	
	private String phase; // trade or economy
	
	public void setPhaseRun(String phaseRun) {
		phase = phaseRun;
	}
	
	public String getPhaseRun() {
		return phase;
	}
	
	public boolean isEligibleToRun(String currentPhase) {
		return phase.equals(currentPhase);
	}
	
	public boolean instantExecution(ResourceCollection startingResources, ResourceCollection futureResources, String currentPhase) {
		if (isEligibleToRun(currentPhase)) {
			return instantExecution(startingResources, futureResources);
		}
		else {
			return false;
		}
	}
	
	public boolean delayedExecution(ResourceCollection startingResources, ResourceCollection futureResources, String currentPhase) {
		if (isEligibleToRun(currentPhase)) {
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