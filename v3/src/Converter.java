public class Converter {
	private ResourceCollection resourceInput;
	private ResourceCollection resourceOutput;

	public Converter() {
		resourceInput = new ResourceCollection();
		resourceOutput = new ResourceCollection();
	}
	
	public Converter(ResourceCollection inputResources, ResourceCollection outputResources) {
		resourceInput = new ResourceCollection(inputResources);
		resourceOutput = new ResourceCollection(outputResources);
	}
	
	public Converter getCopy() {
		Converter conv = new Converter(this.resourceInput, this.resourceOutput);
		return conv;
	}

	// Tries to run the converter (using the resources for inputs), then add the outputs back to the given resource set
    // Returns True if successfully executed, False if not (not enough starting resources)
	public boolean instantExecution(ResourceCollection startingResources) {
		boolean result = startingResources.removeAll(resourceInput);
		if (!result) {
			return false;
		}
		startingResources.addAll(resourceOutput);
		return true;
	}


	// Tries to run the converter (using the resources for inputs), then returns the outputs (to be added next round)
	// If not successfully executed (not enough starting resources), returns a blank ResourceCollection
	public ResourceCollection delayedExecution(ResourceCollection startingResources) {
		boolean result = startingResources.removeAll(resourceInput);
		if (!result) {
			return new ResourceCollection();
		}
		return new ResourceCollection(resourceOutput);
	}


	// Doubles the inputs and outputs of the converter
	public void doubleConverter() {
		ResourceCollection inputCopy = new ResourceCollection(resourceInput);
		ResourceCollection outputCopy = new ResourceCollection(resourceOutput);
		resourceInput.addAll(inputCopy);
		resourceOutput.addAll(outputCopy);
	}
}
