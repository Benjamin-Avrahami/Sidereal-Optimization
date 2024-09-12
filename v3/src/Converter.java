public class Converter {
	private ResourceCollection resourceInput;
	private ResourceCollection resourceOutput;

	public Converter() {
		resourceInput = new BasicResourceCollection();
		resourceOutput = new BasicResourceCollection();
	}

	public Converter(ResourceCollection inputResources, ResourceCollection outputResources) {
		resourceInput = new BasicResourceCollection(inputResources);
		resourceOutput = new BasicResourceCollection(outputResources);
	}

	public Converter getCopy() {
		Converter conv = new Converter(this.resourceInput, this.resourceOutput);
		return conv;
	}

	// Tries to run the converter (using the resources for inputs), then add the outputs back to the given resource set
	// Returns True if successfully executed, False if not (not enough starting resources)
	public boolean instantExecution(ResourceCollection startingResources) {
		return instantExecution(startingResources, new BasicResourceCollection());
	}

	// Tries to run the converter (using the starting resources for inputs), then add the outputs back to the initial resource set
	// Does not use futureResources, only present to be compatible with delayedExecution
  // Returns True if successfully executed, False if not (not enough starting resources)
	public boolean instantExecution(ResourceCollection startingResources, ResourceCollection futureResources) {
		boolean result = startingResources.removeAll(resourceInput);
		if (!result) {
			return false;
		}
		startingResources.addAll(resourceOutput);
		return true;
	}


	// Tries to run the converter (using the starting resources for inputs), then add the outputs back to the future resource set
	// Returns True if successfully executed, False if not (not enough starting resources)
	public boolean delayedExecution(ResourceCollection startingResources, ResourceCollection futureResources) {
		boolean result = startingResources.removeAll(resourceInput);
		if (!result) {
			return false;
		}
		futureResources.addAll(resourceOutput);
		return true;
	}


	// Doubles the inputs and outputs of the converter
	public void doubleConverter() {
		ResourceCollection inputCopy = new BasicResourceCollection(resourceInput);
		ResourceCollection outputCopy = new BasicResourceCollection(resourceOutput);
		resourceInput.addAll(inputCopy);
		resourceOutput.addAll(outputCopy);
	}
	
	
	// Prints information on converter (input/output resources)
	public void display() {
		System.out.println("Converter Information:");
		System.out.println("Input:");
		resourceInput.display();
		System.out.println("Output:");
		resourceOutput.display();
	}
}
