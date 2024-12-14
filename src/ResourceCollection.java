
import java.util.*;
import java.io.*;

// Like normal hashmaps, the hashcode of any object within the resource collection should not change while in it
public class ResourceCollection {
	private Map<Consumable, Integer> collection;
	
	public ResourceCollection() {
		collection = new HashMap<Consumable, Integer>();
	}
	
	// Returns a copy of this collection
	public ResourceCollection getCopy() {
		ResourceCollection collection_copy = new ResourceCollection();
		Iterator<Consumable> rscItr = resourceIterator();
		while (rscItr.hasNext()) {
			Consumable r = rscItr.next();
			collection_copy.add(r,getAmount(r));
		}
		return collection_copy;
	}
	
	// Returns whether there are any resources in the collection
	public boolean isEmpty() {
		return collection.size() == 0;
	}
	
	// Returns an iterator over the (distinct) resources in the collection
	public Iterator<Consumable> resourceIterator() {
		return collection.keySet().iterator();
	}

	public boolean equals(ResourceCollection otherCollection) {
		Iterator<Consumable> itr = resourceIterator();
		while (itr.hasNext()) {
			Consumable r = itr.next();
			if (this.getAmount(r) != otherCollection.getAmount(r)) {
				return false;
			}
		}
		return true;
	}
	
	
	// Returns true if the resources in this collection are fully substitible for the resources in otherCollection (and vice versa)
	public boolean hasEquivalentResources(ResourceCollection otherCollection) {
		return this.equals(otherCollection);
	}



	// Adds the resource into the resource collection
	public void add(Consumable res) {
		add(res, 1);
	}

	// Adds n >= 0 copies of the resource into the resource collection
	public void add(Consumable res, int n) {
		collection.put(res, collection.getOrDefault(res, 0) + n);
	}

	// Adds every resource in the other resource collection to this one
	public void addAll(ResourceCollection other) {
		Iterator<Consumable> itr = other.resourceIterator();
		while (itr.hasNext()) {
			Consumable r = itr.next();
			int amt = other.getAmount(r);
			add(r, amt);
		}
	}

	// Returns true if the collection has the resource, false otherwise
	public boolean hasResource(Consumable res) {
		return hasResource(res, 1);
	}


	// Returns true if the collection has at least n copies of the resource, false otherwise
	public boolean hasResource(Consumable res, int n) {
		return collection.containsKey(res) && collection.get(res) >= n;
	}

    // Returns true if this collection has at least as many resources of each type as the other collection
    // Returns false if there is any resource that this collection has fewer of than the other collection
	public boolean hasAllResources(ResourceCollection other) {
		Iterator<Consumable> itr = other.resourceIterator();
		while (itr.hasNext()) {
			Consumable r = itr.next();
			if (this.getAmount(r) < other.getAmount(r)) {
				return false;
			}
		}
		return true;
	}


	// Returns the amount of the resource contained in the collection
	// Returns 0 if not present
	public int getAmount(Consumable res) {
		if (!hasResource(res)) {
			return 0;
		}
		return collection.get(res);
	}
	
	
	
	// Removes from internal representation if no copies are in collection
	// Must already be in internal representation
	private void cleanup(Consumable res) {
		collection.remove(res, 0);
	}


	// Removes the resource from the resource collection
    // Returns true if successful, false if not (resource not in the collection)
    public boolean remove(Consumable res) {
        return remove(res, 1);
	}

	// Removes n >= 0 copies of the resource from the resource collection
	// Returns true if successful, false if not (not enough of the resource in the collection)
	public boolean remove(Consumable res, int n) {
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
		Iterator<Consumable> itr = other.resourceIterator();
		while (itr.hasNext()) {
			Consumable r = itr.next();
			this.remove(r, other.getAmount(r)); // Ends up checking twice for ability to remove, but does not change functionality
		}
		return true;
	}

	// Counts the total number of objects in the collection
	public int totalObjects() {
		int count = 0;
		Iterator<Consumable> itr = resourceIterator();
		while (itr.hasNext()) {
			Consumable r = itr.next();
			count += getAmount(r);
		}
		return count;
	}

	// Counts the total number of distinct objects in the collection
	public int distinctObjects() {
		return collection.size();
	}
	
	
	// Returns a new resource collection containing all objects in this collection that have the same type as objType
	public ResourceCollection getObjectsOfType(String objType) {
		Iterator<Consumable> itr = resourceIterator();
		ResourceCollection typedCollection = new ResourceCollection();
		while (itr.hasNext()) {
			Consumable r = itr.next();
			if (objType.equals(r.objectType())) {
				typedCollection.add(r,getAmount(r));
			}
		}
		return typedCollection;
	}

	// Prints the collection
	public void display() {
		System.out.println("{");
		Iterator<Consumable> itr = resourceIterator();
		while (itr.hasNext()) {
			Consumable r = itr.next();
			int amt = getAmount(r);
			System.out.println("\t" + r.getName() + ": " + amt);
		}
		System.out.println("}");
	}
}
