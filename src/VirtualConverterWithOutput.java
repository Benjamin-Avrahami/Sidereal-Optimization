
// Eni Et version, since the player will have to choose (implicitly) output
// Input types will have to be equivalent to each other
public class VirtualConverterWithOutput implements VirtualConverter {
	VirtualConverterInputsOnly inputVC;
	private Resource converterResourceType;
	private Resource outputResourceType;
	// Eni et converters do not only produce resources of the output type, but return the original cubes as well
	private ResourceCollection outputResourcesWithOriginals;
	
	
	public VirtualConverterWithOutput() {
		inputVC = new VirtualConverterInputsOnly();
		converterResourceType = null;
		outputResourceType = null;
		outputResourcesWithOriginals = new ResourceCollection();
	}
	
	// Set the output resource (i.e. the shared input type)
	// Usually determined automatically, only time it is not is when all inputs are wild
	public boolean setOutputResourceType(Resource resource) {
		// Wild output types are not valid
		if (resource.isWild()) {
			return false;
		}
		if (outputResourceType == null || resource.isA(outputResourceType) {
			outputResourceType = resource;
			return true;
		}
		return false;
	}
	
	
	// New choice, signifying that when the converter is run, concreteResource -> converterResource in the input
	// Only works if the replacement (converter) resource is substitutable for the original resource
	// Returns true if successful, false if not
	public boolean addNewInputChoice(Resource concreteResource, Resource converterResource) {
		// The part that matters is the eni et input generic type, which is nonconcrete
		// Others can safely be multiple types since they are not restricted
		if (!converterResource.isConcrete()) {
			// Capture the generic type to be able to substitute for it on output
			converterResourceType = converterResource;
			if (outputResourceType != null) {
				// Check to make sure there is only one type of cube being passed into the converter
				// Wilds are also allowed,so using isA rather than equals
				if (!concreteResource.isA(outputResourceType) {
					return false;
				}
				// Attempt to discern the type of the output automatically (wild + non-wild = non-wild)
				else if (outputResourceType.isWild()) {
					outputResourceType = concreteResource;
				}
			}
			else {
				outputResourceType = concreteResource;
			}
			// Add to collection, to be restored on output
			outputResourcesWithOriginals.add(concreteResource);
		}
		return inputVC.addNewInputChoice(concreteResource, converterResource);
	}
	
	
	// Substitutes the player's choices for input resources provided within the ResourceCollection
	// Returns true if successful, false (and unchanged) if not
	public boolean executeOnInputs(ResourceCollection beginningResources) {
		// All-wild inputs should have a manually declared output type
		if (inputResourceType.isWild()) {
			return false;
		}
		return inputVC.executeOnInputs(beginningResources);
	}
	
	
	// Substitutes output resources for the player's choices provided within the ResourceCollection
	// Returns true if successful, false (and unchanged) if not - should always return true
	public boolean executeOnOutputs(ResourceCollection endingResources) {
		// Potentially sketchy, since we want the amount the converter produces rather than how many the player has temporarily
		// However, since the player should usually have all concrete resources all other times, this should be fine
		int amountProducedOfConverterType = endingResources.getAmount(converterResourceType);
		ResourceCollection converterOutputOfConverterType = new ResourceCollection();
		converterOutputOfConverterType.add(converterResourceType, amountProducedOfConverterType);
		int amountPutIn = outputResourcesWithOriginals.totalObjects();
		// Equalize to amount produced by converter for actual virtual conversion
		outputResourcesWithOriginals.add(outputResourceType, amountProducedOfConverterType - amountPutIn);
		return ConverterUtils.execute(converterOutputOfConverterType, outputResourcesWithOriginals, endingResources, endingResources);
	}
}