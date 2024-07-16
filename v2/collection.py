from resource import *


class ResourceCollection():
    def __init__(self):
        self.collection = {}
        
    # Copies other_collection if not none, otherwise has its contents be dictionary_representation
    def __init__(self, other_collection=None, dictionary_representation={}):
        if other_collection != None:
            self.collection = other_collection.collection
        else:
            self.collection = dictionary_representation
    
    
    # Adds the resource into the resource collection
    def add(self, res: Resource):
        self.add(res, 1)
        
    # Adds n >= 0 copies of the resource into the resource collection
    def add(self, res: Resource, n: int):
        self.collection[res] = self.collection.get(res,0) + n
        
    # Adds every resource in the other resource collection to this one
    def add_all(self, other: ResourceCollection):
        for res in other.collection:
            add(res, other.collection[res])

           
    # Removes from internal representation if no copies are in collection
    # Must already be in internal representation
    def cleanup(self, res: Resource):
        if self.collection[res] == 0:
            del self.collection[res]   
    
    # Returns true if the collection has at least n copies of the resource, false otherwise
    def has_resource(self, res: Resource, n: int):
        return self.get(res, 0) >= n
    
    # Returns true if this collection has at least as many resources of each type as the other collection
    # Returns false if there is any resource that this collection has fewer of than the other collection
    def has_all_resources(self, other: ResourceCollection):
        for res in other.collection:
            if self.collection.get(res,0) < other.collection[res]:
                return False
        return True
            
    # Removes the resource from the resource collection
    # If the resource is not in the collection, throws a ValueError and does not change
    def remove(self, res: Resource):
        self.remove(res, 1)
            
    # Removes n >= 0 copies of the resource from the resource collection
    # If enough of the resource is not in the collection, throws a ValueError and does not change
    def remove(self, res: Resource, n: int):
        if not self.has_resource(res, n):
            raise ValueError("Not enough resources")
        self.collection[res] = self.collection.get(res,0) - n
        self.cleanup(res)
    
    # Removes every resource from the other resource collection from this one
    # If not possible, throws a ValueError and does not change
    def remove_all(self, other: ResourceCollection):
        if not self.has_all_resources(other):
            raise ValueError("Not enough resources")
        for res in other_collection:
            self.remove(res, other.collection[res]) # Ends up checking twice for presence in collection, but does not change execution