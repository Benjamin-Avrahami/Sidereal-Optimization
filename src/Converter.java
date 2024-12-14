public class Converter {
	private ResourceCollection resourceInput;
	private ResourceCollection resourceOutput;

	public Converter(ResourceCollection inputResources, ResourceCollection outputResources) {
		resourceInput = inputResources.getCopy();
		resourceOutput = outputResources.getCopy();
	}

	public Converter getCopy() {
		Converter conv = new Converter(this.resourceInput, this.resourceOutput);
		return conv;
	}
	
	public ResourceCollection getInputs() {
		return inputResources.getCopy();
	}
	
	public ResourceCollection getOutputs() {
		return outputResources.getCopy();
	}
	
	
	// Actually executes the converter (assuming there are enough input resources)
	// Only should be called after passing through input checks on chosenResources
	// Attempts to run the converter using the inputs and outputs as chosen by chosenResources
	// beginningResources can be the same ResourceCollection as endingResources (in which case, the outputs
	// would be immediately added back to the same collection)
	// Returns True if successfully executed, False if not (not enough starting resources)
	private boolean executeConversion(ResourceCollection beginningResources, ResourceCollection endingResources, ConverterResourceChoice chosenResources) {
		ResourceCollection concreteResourceInput;
		ResourceCollection concreteResourceOutput;
		if (chosenResources.getInputReplacements().empty()) {
			// shallow copy (no need to change, so leave the same as member)
			concreteResourceInput = resourceInput;
		}
		else {
			// deep copy and change input as specified
			concreteResourceInput = resourceInput.getCopy();
			concreteResourceInput.removeAll(chosenResources.getInputReplacements().getInputs());
			concreteResourceInput.addAll(chosenResources.getInputReplacements().getOutputs());
		}
		if (chosenResources.getOutputReplacements().empty()) {
			// shallow copy (no need to change, so leave the same as member)
			concreteResourceOutput = resourceOutput;
		}
		else {
			// deep copy and change output as specified
			concreteResourceOutput = resourceOutput.getCopy();
			concreteResourceOutput.removeAll(chosenResources.getOutputReplacements().getInputs());
			concreteResourceOutput.addAll(chosenResources.getOutputReplacements().getOutputs());
		}
		boolean result = beginningResources.removeAll(concreteResourceInput);
		if (!result) {
			return false;
		}
		endingResources.addAll(concreteResourceOutput);
		return true;
	}
	
	
	// Returns true if all resources in the resource collection are concrete, false if otherwise
	// Move to resource collection? (maybe concrete wrapper of RC)
	public boolean isAllConcrete(ResourceCollection resources) {
		Iterator<Consumable> itr = resourceInput.resourceIterator();
		while (itr.hasNext()) {
			Consumable r = itr.next();
			if (!r.isConcrete()) {
				return false;
			}
		}
		return true;
	}
	
	
	// Whether the converter needs a specific choice of cubes from the player in order to be able to determine what to use
	// Returns false if there are any generic inputs (which would require a specific choice), true otherwise
	public boolean isAbleToAutoExecute() {
		return isAllConcrete(resourceInput);
	}
	
	// Attempts to run the converter using automatically calculated inputs from beginningResources, then adds the output to endingResources
	// beginningResources can be the same ResourceCollection as endingResources (in which case, the outputs
	// would be immediately added back to the same collection)
	// Returns True if successfully executed, False if not (not enough starting resources)
	public boolean autoExecute(ResourceCollection beginningResources, ResourceCollection endingResources) {
		if (!isAbleToAutoExecute()) {
			return false;
		}
		return executeConversion(beginningResources, endingResources, new ConverterResourceChoice());
	}
	
	
	// Returns true if the input resource choice can be substituted for the converter resources to yield a concrete converter, false otherwise
	public boolean isAcceptableResourceChoice(ConverterResourceChoice resourceChoice) {
		if ((!inputResources.hasEquivalentResources(resourceChoice.getInputReplacements())) || (!isAllConcrete(resourceChoice.getInputReplacements()))) {
			return false;
		}
		if ((!outputResources.hasEquivalentResources(resourceChoice.getOutputReplacements())) || (!isAllConcrete(resourceChoice.getOutputReplacements()))) {
			return false;
		}
		return true;
	}
	
	
	// Attempts to run the converter using the inputs and outputs as chosen by chosenResources
	// beginningResources can be the same ResourceCollection as endingResources (in which case, the outputs
	// would be immediately added back to the same collection)
	// Returns True if successfully executed, False if not (not enough starting resources)
	public boolean execute(ResourceCollection beginningResources, ResourceCollection endingResources, ConverterResourceChoice chosenResources) {
		if (!areAcceptableInputResources(chosenResources)) {
			return false;
		}
		return executeConversion(beginningResources, endingResources, chosenResources);
	}


	// Doubles the inputs and outputs of the converter
	public void doubleConverter() {
		ResourceCollection inputCopy = resourceInput.getCopy();
		ResourceCollection outputCopy = resourceOutput.getCopy();
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
