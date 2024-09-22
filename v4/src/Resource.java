public interface Resource {

	// Get the name of the resource
	public String getName();

	// See whether the other resource provided is the same resource as this
	public boolean equals(Object otherResource);
	
	// Allowing for hashing
	public int hashCode();

}
