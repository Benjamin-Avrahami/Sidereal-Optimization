
public class Resource {
	private final String name;
	
	public Resource(String resourceName) {
		name = resourceName;
	}
	
	// Get the name of the resource
	public String getName() {
		return name;
	}
	
	// See whether the other resource provided is the same resource as this
	public boolean equals(Resource otherResource) {
		return this.getName().equals(otherResource.getName());
	}
	
	// Allowing for hashing
	public int hashCode() {
		return name.hashCode();
	}

}