

// Most common virtual converter, since most converters with generic inputs do not have generic outputs
public class VirtualConverterInputsOnly implements VirtualConverter {
	ResourceCollection inputConcreteResources;
	ResourceCollection inputConverterResources;
	
	public VirtualConverter() {
		inputConcreteResources = new ResourceCollection();
		inputConverterResources = new ResourceCollection();
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
	
	
	// Substitutes the player's choices for input resources provided within the ResourceCollection
	// Returns true if successful, false (and unchanged) if not
	public boolean executeOnInputs(ResourceCollection beginningResources) {
		return ConverterUtils.execute(inputConcreteResources, inputConverterResources, beginningResources, beginningResources);
	}
	
	// Base virtual converter has no output choices, so conversion is identity function
	public boolean executeOnOutputs(ResourceCollection endingResources) {
		return true;
	}
	
}