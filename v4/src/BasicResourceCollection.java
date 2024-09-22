import java.util.*;


// Undifferentiated resource collection
// The elements/resources should not be modified while in the collection
public class BasicResourceCollection extends ResourceCollection {
	private Map<GameObject, Integer> collection;

	public BasicResourceCollection() {
		collection = new HashMap<GameObject, Integer>();
	}

	// Copy constructor of otherCollection
	public BasicResourceCollection(ResourceCollection otherCollection) {
		collection = new HashMap<GameObject, Integer>();
		Iterator<GameObject> rscItr = otherCollection.resourceIterator();
		while (rscItr.hasNext()) {
			GameObject r = rscItr.next();
			add(r,otherCollection.getAmount(r));
		}
	}

	// Copies the map representation into the internal representation
	public BasicResourceCollection(Map<GameObject, Integer> mapRepresentation) {
		this.collection = new HashMap<GameObject, Integer>(mapRepresentation);
	}

	// Returns whether there are any resources in the collection
	public boolean isEmpty() {
		return collection.size() == 0;
	}

	// Returns an iterator over the (distinct) resources in the collection
	public Iterator<GameObject> resourceIterator() {
		return collection.keySet().iterator();
	}


	// Adds n >= 0 copies of the resource into the resource collection
	public void add(GameObject res, int n) {
		collection.put(res, collection.getOrDefault(res, 0) + n);
	}


	// Returns true if the collection has at least n copies of the resource, false otherwise
	public boolean hasResource(GameObject res, int n) {
		return collection.containsKey(res) && collection.get(res) >= n;
	}


	// Returns the amount of the resource contained in the collection
	// Returns 0 if not present
	public int getAmount(GameObject res) {
		if (!hasResource(res)) {
			return 0;
		}
		return collection.get(res);
	}

	// Removes from internal representation if no copies are in collection
  // Must already be in internal representation
	private void cleanup(GameObject res) {
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
