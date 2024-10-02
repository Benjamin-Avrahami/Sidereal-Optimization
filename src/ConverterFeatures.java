public class ConverterFeatures {
	private Converter conv;
	private String name;
	private boolean instantRun; // true if run in trade, false in economy
	
	ConverterFeatures(Converter con, String conName, boolean instRun) {
		conv = con.getCopy();
		name = conName;
		instantRun = instRun;
	}
	
	// Runs the converter according to its time
	public boolean runConverter(ResourceCollection startingResources, ResourceCollection futureResources) {
		if (instantRun) {
			return conv.instantExecution(startingResources, futureResources);
		}
		else {
			return conv.delayedExecution(startingResources, futureResources);
		}
	}
	
	public void display() {
		System.out.print("Converter " + name);
		if (instantRun) {
			System.out.println("(run in trade)");
		}
		else {
			System.out.println("(run in economy)");
		}
		conv.display();
	}
}