import java.util.*;


// Undifferentiated resource collection
public class BasicResourceCollection extends ResourceCollection {
	private Map<Resource, Integer> collection;

	public BasicResourceCollection() {
		collection = new HashMap<Resource, Integer>();
	}

	// Copy constructor of otherCollection
	public BasicResourceCollection(ResourceCollection otherCollection) {
		collection = new HashMap<Resource, Integer>();
		Iterator<Resource> rscItr = otherCollection.resourceIterator();
		while (rscItr.hasNext()) {
			Resource r = rscItr.next();
			add(r,otherCollection.getAmount(r));
		}
	}

	// Copies the map representation into the internal representation
	public BasicResourceCollection(Map<Resource, Integer> mapRepresentation) {
		this.collection = new HashMap<Resource, Integer>(mapRepresentation);
	}
	
	// Returns whether there are any resources in the collection
	public boolean isEmpty() {
		return collection.size() == 0;
	}

	// Returns an iterator over the (distinct) resources in the collection
	public Iterator<Resource> resourceIterator() {
		return collection.keySet().iterator();
	}


	// Adds n >= 0 copies of the resource into the resource collection
	public void add(Resource res, int n) {
		collection.put(res, collection.getOrDefault(res, 0) + n);
	}
	

	// Returns true if the collection has at least n copies of the resource, false otherwise
	public boolean hasResource(Resource res, int n) {
		return collection.containsKey(res) && collection.get(res) >= n;
	}


	// Returns the amount of the resource contained in the collection
	// Returns 0 if not present
	public int getAmount(Resource res) {
		if (!hasResource(res)) {
			return 0;
		}
		return collection.get(res);
	}

	// Removes from internal representation if no copies are in collection
    // Must already be in internal representation
	private void cleanup(Resource res) {
		collection.remove(res, 0);
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
}
