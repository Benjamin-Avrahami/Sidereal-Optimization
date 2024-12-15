

// Temporary structure used by players to make choices about resources to use in a converter
// Future note: if a player wants to remove a choice, deleting and reconctructing the virtual converter with the remaining 
// is likely better since, because implementations typically implement one-by-one comparison, 
// giving the option of which correspondence to remove can break the invariant (fully substitutable)
public interface VirtualConverter {
	// New choice, signifying that when the converter is run, concreteResource -> converterResource in the input
	// Will not work if the replacement (converter) resource is not substitutable for the original resource
	// Returns true if successful, false if not
	public boolean addNewInputChoice(Resource concreteResource, Resource converterResource);
	
	
	// Substitutes the player's choices for input resources provided within the ResourceCollection
	// Returns true if successful, false (and unchanged) if not
	public boolean executeOnInputs(ResourceCollection beginningResources);
	
	
	// Substitutes output resources for the player's choices provided within the ResourceCollection
	// Returns true if successful, false (and unchanged) if not
	public boolean executeOnOutputs(ResourceCollection endingResources);
	
}