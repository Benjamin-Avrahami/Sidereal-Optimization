import java.util.*;
import java.io.*;


public class ResourceCollection {
	private Map<Resource, Integer> collection;

	public ResourceCollection() {
		collection = new HashMap<Resource, Integer>();
	}

	// Copy constructor of otherCollection
	public ResourceCollection(ResourceCollection otherCollection) {
		this.collection = new HashMap<Resource, Integer>(otherCollection.collection);
	}

	// Copies the map representation into the internal representation
	public ResourceCollection(Map<Resource, Integer> mapRepresentation) {
		this.collection = new HashMap<Resource, Integer>(mapRepresentation);
	}

	// Returns an iterator over the (distinct) resources in the collection
	private Iterator<Resource> resourceIterator() {
		return collection.keySet().iterator();
	}

	// Adds the resource into the resource collection
	public void add(Resource res) {
		add(res, 1);
	}

	// Adds n >= 0 copies of the resource into the resource collection
	public void add(Resource res, int n) {
		collection.put(res, collection.getOrDefault(res, 0) + n);
	}

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
	public boolean hasResource(Resource res, int n) {
		return collection.containsKey(res) && collection.get(res) >= n;
	}

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

	// Removes from internal representation if no copies are in collection
    // Must already be in internal representation
	private void cleanup(Resource res) {
		collection.remove(res, 0);
	}

	// Removes the resource from the resource collection
    // Returns true if successful, false if not (resource not in the collection)
    public boolean remove(Resource res) {
        return this.remove(res, 1);
	}

	// Removes n >= 0 copies of the resource from the resource collection
    // Returns true if successful, false if not (not enough of the resource in the collection)
    public boolean remove(Resource res, int n) {
        if (!hasResource(res, n)) {
            return false;
		}
        collection.put(res, collection.get(res) - n);
        cleanup(res);
		return true;
	}

	// Tries to remove every resource from the other resource collection from this one
    // Returns true if successful, false if not (and does not change)
	public boolean removeAll(ResourceCollection other) {
		if (!hasAllResources(other)) {
			return false;
		}
		Iterator<Resource> itr = other.resourceIterator();
		while (itr.hasNext()) {
			Resource r = itr.next();
			this.remove(r, other.collection.get(r)); // Ends up checking twice for ability to remove, but does not change functionality
		}
		return true;
	}

	// Prints the collection
	public void display() {
		System.out.println("{");
		Iterator<Resource> itr = resourceIterator();
		while (itr.hasNext()) {
			Resource r = itr.next();
			int amt = collection.get(r);
			System.out.println("\t" + r.getName() + ": " + amt);
		}
		System.out.println("}");
	}
}
