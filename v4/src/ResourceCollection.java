
import java.util.*;
import java.io.*;


public abstract class ResourceCollection {

	public boolean equals(ResourceCollection otherCollection) {
		Iterator<GameObject> itr = resourceIterator();
		while (itr.hasNext()) {
			GameObject r = itr.next();
			if (this.getAmount(r) != otherCollection.getAmount(r)) {
				return false;
			}
		}
		return true;
	}

	// Returns a copy of the collection
	public ResourceCollection getCopy();

	// Returns an iterator over the (distinct) resources in the collection
	public abstract Iterator<GameObject> resourceIterator();

	// Returns whether there are any resources in the collection
	public abstract boolean isEmpty();

	// Adds the resource into the resource collection
	public void add(GameObject res) {
		add(res, 1);
	}

	// Adds n >= 0 copies of the resource into the resource collection
	public abstract void add(GameObject res, int n);

	// Adds every resource in the other resource collection to this one
	public void addAll(ResourceCollection other) {
		Iterator<GameObject> itr = other.resourceIterator();
		while (itr.hasNext()) {
			GameObject r = itr.next();
			int amt = other.getAmount(r);
			add(r, amt);
		}
	}

	// Returns true if the collection has the resource, false otherwise
	public boolean hasResource(GameObject res) {
		return hasResource(res, 1);
	}

	// Returns true if the collection has at least n copies of the resource, false otherwise
	public abstract boolean hasResource(GameObject res, int n);


    // Returns true if this collection has at least as many resources of each type as the other collection
    // Returns false if there is any resource that this collection has fewer of than the other collection
	public boolean hasAllResources(ResourceCollection other) {
		Iterator<GameObject> itr = other.resourceIterator();
		while (itr.hasNext()) {
			GameObject r = itr.next();
			if (this.getAmount(r) < other.getAmount(r)) {
				return false;
			}
		}
		return true;
	}

	// Returns the amount of the resource contained in the collection
	// Returns 0 if not present
	public abstract int getAmount(GameObject res);


	// Removes the resource from the resource collection
    // Returns true if successful, false if not (resource not in the collection)
    public boolean remove(GameObject res) {
        return remove(res, 1);
	}

	// Removes n >= 0 copies of the resource from the resource collection
    // Returns true if successful, false if not (not enough of the resource in the collection)
    public abstract boolean remove(GameObject res, int n);


	// Tries to remove every resource from the other resource collection from this one
    // Returns true if successful, false if not (and does not change)
	public boolean removeAll(ResourceCollection other) {
		if (!hasAllResources(other)) {
			return false;
		}
		Iterator<GameObject> itr = other.resourceIterator();
		while (itr.hasNext()) {
			GameObject r = itr.next();
			this.remove(r, other.getAmount(r)); // Ends up checking twice for ability to remove, but does not change functionality
		}
		return true;
	}

	// Counts the total number of objects in the collection
	public int totalObjects() {
		int count = 0;
		Iterator<GameObject> itr = resourceIterator();
		while (itr.hasNext()) {
			GameObject r = itr.next();
			count += getAmount(r);
		}
		return count;
	}

	// Counts the total number of distinct objects in the collection
	// O(n) default behavior, likely to be overridden in concrete classes if they can get better performance
	public int distinctObjects() {
		int count = 0;
		Iterator<GameObject> itr = resourceIterator();
		while (itr.hasNext()) {
			GameObject r = itr.next();
			count += 1;
		}
		return count;
	}

	// Prints the collection
	public void display() {
		System.out.println("{");
		Iterator<GameObject> itr = resourceIterator();
		while (itr.hasNext()) {
			GameObject r = itr.next();
			int amt = getAmount(r);
			System.out.println("\t" + r.getName() + ": " + amt);
		}
		System.out.println("}");
	}
}
