// A converter, but with additional game features determining ability to run
public class ConverterFeatures {
	
	private Converter conv;
	private String phase; // trade or economy
	private boolean hasBeenRun; // whether the converter has been run or not this turn
	
	public ConverterFeatures() {
		conv = new Converter();
		phase = "";
		hasBeenRun = false;
	}

	public ConverterFeatures(ResourceCollection inputResources, ResourceCollection outputResources) {
		conv = new Converter(inputResources, outputResources);
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
	
	// Resets the converter (at the start of a new turn)
	public boolean refresh() {
		hasBeenRun = false;
	}
	
	// Given the phase, runs the correct version of the converter
	public boolean execute(Player p, ConverterResourceChoice resourceChoice) {
		if (!isEligibleToRun()) {
			return false;
		}
		
		hasBeenRun = true;
		if (getPhaseRun().equals("Trade Phase")) {
			return conv.execute(p.getCurrentResources(), p.getCurrentResources(), resourceChoice);
		}
		else if (getPhaseRun().equals("Economy Phase")) {
			return conv.execute(p.getCurrentResources(), p.getFutureResources(), resourceChoice);
		}
		else {
			return false;
		}
	}
	
	public void display() {
		super.display();
		System.out.println("Runs during " + getPhaseRun());
		if (!isEligibleToRun()) {
			System.out.println("(Already been run this turn)");
		}
	}
}