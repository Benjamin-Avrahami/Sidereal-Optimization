
// Anything a player physically interacts with
// Specifically, anything that can be an input/output to a converter, boradly defined (cubes/colonies/converters/research teams/...)
public interface GameObject {

	// Get the name of the resource
	public String getName();

	// See whether the other resource provided is the same resource as this
	public boolean equals(Object otherResource);

	// Allowing for hashing
	public int hashCode();
	
	// Gets the type of the object within the game (cube, converter, colony, etc.)
	public String objectType();

}
