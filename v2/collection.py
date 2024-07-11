from resource import *


class ResourceCollection():
    def __init__(self):
        self.collection = {}
        
    def __init__(self, other_collection: dict):
        self.collection = other_collection
    
    
    # Adds the resource into the resource collection
    def add(self, res: Resource):
        self.collection[res] = self.collection.get(res,0) + 1
        
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
            
            
    # Removes the resource from the resource collection
    # If the resource is not in the collection, throws a ValueError and does not change
    def remove(self, res: Resource):
        if self.collection.get(res,0) < 1:
            throw ValueError("Not enough resources")
        self.collection[res] = self.collection.get(res,0) - 1
        cleanup(res)
            
    # Removes n >= 0 copies of the resource from the resource collection
    # If enough of the resource is not in the collection, throws a ValueError and does not change
    def remove(self, res: Resource, n: int):
        if self.collection.get(res,0) < n:
            throw ValueError("Not enough resources")
        self.collection[res] = self.collection.get(res,0) - n
        cleanup(res)
    
    # Removes every resource from the other resource collection from this one
    # If not possible, throws a ValueError and does not change
    def remove_all(self, other: ResourceCollection):
        for res in other.collection:
            if self.collection.get(res,0) < other.collection[res]:
                throw ValueError("Not enough of " + res.get_name())
        for res in other_collection:
            self.collection[res] = self.collection.get(res,0) - other.collection[res]
            cleanup(res)