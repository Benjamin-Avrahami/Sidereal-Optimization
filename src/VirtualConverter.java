

// Temporary structure used by players to make choices 
public class VirtualConverter {
	ResourceCollection inputConcreteResources;
	ResourceCollection inputConverterResources;
	ResourceCollection outputConverterResources;
	ResourceCollection outputConcreteResources;
	
	public VirtualConverter() {
		inputConcreteResources = new ResourceCollection();
		inputConverterResources = new ResourceCollection();
		outputConverterResources = new ResourceCollection();
		outputConcreteResources = new ResourceCollection();
	}
	
	// New choice, signifying that when the converter is run, concreteResource -> converterResource in the input
	// Only works if the replacement (converter) resource is substitutable for the original resource
	// Returns true if successful, false if not
	public boolean addNewInputChoice(Resource concreteResource, Resource converterResource) {
		if (concreteResource.isA(converterResource)) {
			inputConcreteResources.add(concreteResource);
			inputConverterResources.add(converterResource);
			return true;
		}
		return false;
	}
	
	// New choice, signifying that when the converter is run, converterResource -> concreteResource in the output
	// Only works if the replacement (concrete) resource is substitutable for the original resource
	// Returns true if successful, false if not
	public boolean addNewOutputChoice(Resource converterResource, Resource concreteResource) {
		if (concreteResource.isA(converterResource)) {
			outputConverterResources.add(converterResource);
			outputConcreteResources.add(concreteResource);
			return true;
		}
		return false;
	}
	
	
	// Substitutes the player's choices for input resources provided within the ResourceCollection
	// Returns true if successful, false (and unchanged) if not
	public boolean executeOnInputs(ResourceCollection beginningResources) {
		return ConverterUtils.execute(inputConcreteResources, inputConverterResources, beginningResources, beginningResources);
	}
	
	// Substitutes output resources for the player's choices provided within the ResourceCollection
	// Returns true if successful, false (and unchanged) if not
	public boolean executeOnOutputs(ResourceCollection endingResources) {
		return ConverterUtils.execute(outputConcreteResources, outputConverterResources, endingResources, endingResources);
	}
	
}