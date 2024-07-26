
import java.util.*;
import java.io.*;


public abstract class ResourceCollection {

	public boolean equals(ResourceCollection otherCollection) {
		Iterator<Resource> itr = resourceIterator();
		while (itr.hasNext()) {
			Resource r = itr.next();
			if (this.getAmount(r) != otherCollection.getAmount(r)) {
				return false;
			}
		}
		return true;
	}

	// Returns an iterator over the (distinct) resources in the collection
	public Iterator<Resource> resourceIterator();
	
	// Returns whether there are any resources in the collection
	public boolean isEmpty();

	// Adds the resource into the resource collection
	public void add(Resource res) {
		add(res, 1);
	}

	// Adds n >= 0 copies of the resource into the resource collection
	public void add(Resource res, int n);

	// Adds every resource in the other resource collection to this one
	public void addAll(ResourceCollection other) {
		Iterator<Resource> itr = other.resourceIterator();
		while (itr.hasNext()) {
			Resource r = itr.next();
			int amt = other.collection.get(r);
			add(r, amt);
		}
	}

	// Returns true if the collection has the resource, false otherwise
	public boolean hasResource(Resource res) {
		return hasResource(res, 1);
	}

	// Returns true if the collection has at least n copies of the resource, false otherwise
	public boolean hasResource(Resource res, int n);


    // Returns true if this collection has at least as many resources of each type as the other collection
    // Returns false if there is any resource that this collection has fewer of than the other collection
	public boolean hasAllResources(ResourceCollection other) {
		Iterator<Resource> itr = other.resourceIterator();
		while (itr.hasNext()) {
			Resource r = itr.next();
			if (this.collection.getOrDefault(r,0) < other.collection.get(r)) {
				return false;
			}
		}
		return true;
	}

	// Returns the amount of the resource contained in the collection
	// Returns 0 if not present
	public int getAmount(Resource res);


	// Removes the resource from the resource collection
    // Returns true if successful, false if not (resource not in the collection)
    public boolean remove(Resource res) {
        return remove(res, 1);
	}

	// Removes n >= 0 copies of the resource from the resource collection
    // Returns true if successful, false if not (not enough of the resource in the collection)
    public boolean remove(Resource res, int n);
	

	// Tries to remove every resource from the other resource collection from this one
    // Returns true if successful, false if not (and does not change)
	public boolean removeAll(ResourceCollection other) {
		if (!hasAllResources(other)) {
			return false;
		}
		Iterator<Resource> itr = other.resourceIterator();
		while (itr.hasNext()) {
			Resource r = itr.next();
			this.remove(r, other.getAmount(r)); // Ends up checking twice for ability to remove, but does not change functionality
		}
		return true;
	}

	// Prints the collection
	public void display() {
		System.out.println("{");
		Iterator<Resource> itr = resourceIterator();
		while (itr.hasNext()) {
			Resource r = itr.next();
			int amt = getAmount(r);
			System.out.println("\t" + r.getName() + ": " + amt);
		}
		System.out.println("}");
	}
}
