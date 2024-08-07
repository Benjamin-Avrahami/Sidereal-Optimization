
import java.util.*;

// Resource collection separated out into different (persistent) groups
// Uses a Rule to differentiate groups
public class SeparatedResourceCollection extends ResourceCollection {
	// Each collection in the array must have a different group, and each only has members of one group
	private ArrayList<ResourceCollection> groups;
	// Rule defining the groups
	private GroupRule gr;

	public SeparatedResourceCollection() {
		groups = new ArrayList<ResourceCollection>();
		gr = null;
	}
	
	public SeparatedResourceCollection(GroupRule definingRule) {
		groups = new ArrayList<ResourceCollection>();
		gr = definingRule;
	}
	

	public void setRule(GroupRule grule) {
		gr = grule;
	}
	
	// Returns whether there are any resources in the collection
	public boolean isEmpty() {
		return groups.size() == 0;
	}


	// Returns an iterator over the (distinct) resources in the collection
	public Iterator<Resource> resourceIterator() {
		Set<Resource> allResources = new HashSet<Resource>();
		Iterator<ResourceCollection> groupItr = groups.iterator();
		// Go through each element in each contained ResourceCollection to add
		while (groupItr.hasNext()) {
			ResourceCollection rc = groupItr.next();
			Iterator<Resource> rscItr = rc.resourceIterator();
			while (rscItr.hasNext()) {
				Resource res = rscItr.next();
				allResources.add(res);
			}
		}
		return allResources.iterator();
	}
	
	// Returns the index of the groups array that is the group as the resource
	// The resource may or may not be in the collection in that index
	// If no collection in the array has the same group as the resource, returns -1
	private int groupIndex(Resource res) {
		for (int i = 0; i < groups.size(); ++i) {
			Resource sampleFromGroup = groups.get(i).resourceIterator().next();
			if (gr.inSameGroup(res, sampleFromGroup)) {
				return i;
			}
		}
		return -1;
	}


	// Adds n >= 0 copies of the resource into the resource collection
	public void add(Resource res, int n) {
		int index = groupIndex(res);
		if (index >= 0) {
			ResourceCollection rc = groups.get(index);
			rc.add(res, n);
		}
		else {
			ResourceCollection rc = new BasicResourceCollection();
			rc.add(res, n);
			groups.add(rc);
		}
	}
	

	// Returns true if the collection has at least n copies of the resource, false otherwise
	public boolean hasResource(Resource res, int n) {
		int index = groupIndex(res);
		if (index == -1) return false;
		ResourceCollection rc = groups.get(index);
		return rc.hasResource(res, n);
	}


	// Returns the amount of the resource contained in the collection
	// Returns 0 if not present
	public int getAmount(Resource res) {
		if (!hasResource(res)) {
			return 0;
		}
		int index = groupIndex(res);
		ResourceCollection rc = groups.get(index);
		return rc.getAmount(res);
	}


	// Removes n >= 0 copies of the resource from the resource collection
    // Returns true if successful, false if not (not enough of the resource in the collection)
    public boolean remove(Resource res, int n) {
        if (!hasResource(res, n)) {
            return false;
		}
		int index = groupIndex(res);
		ResourceCollection rc = groups.get(index);
		rc.remove(res, n);
        if (rc.isEmpty()) {
			groups.remove(index);
		}
		return true;
	}
	
	// Returns the (copy of the) ResourceCollection whose group is the same as the given resource (which does not have to be present in the collection)
	// If no groups are the same, returns empty collection
	public ResourceCollection getCollectionOfType(Resource res) {
		int index = groupIndex(res);
		if (index == -1) return new BasicResourceCollection();
		return new BasicResourceCollection(groups.get(index));
	}
	
}
