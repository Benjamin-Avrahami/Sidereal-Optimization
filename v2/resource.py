from abc import ABC

class Resource(ABC):
    def __init__(self, resource_name):
        self.name = resource_name
    
    # Get the name of the resource
    def get_name():
        return self.name
    
    # See whether the other resource provided is the same resource as this
    def same_type(other_resource):
        return self.name == other_resource.name
    
    
    # Functions to allow hashing
    def __hash__(self):
        return hash(self.name)
    
    def __eq__(self, other):
        return self.name == other.name
    
    def __ne__(self, other):
        return self.name != other.name
        
        
class Scorable(Resource):
    # Suggested trade value (anchored to 1 victory point = 1)
    def trade_value():
        pass
        
    # Victory point total of the resource at the end of the game
    def scoring_value():
        if self.name == 'victory point':
            return trade_value()
        else:
            return trade_value()/2

    def is_wild():
        pass
        



# brown, white, green, small grey
class SmallCube(Scorable):
    def trade_value():
        return 1/3
        
    def is_wild():
        return self.name == 'small grey'
        

# yellow, black, blue, large grey
class LargeCube(Scorable):
    def trade_value():
        return 1/2
        
    def is_wild():
        return self.name == 'large grey'
        

# ship, octagon, victory point
class OtherScorable(Scorable):
    def trade_value():
        if self.name == 'ship':
            return 1/3
        elif self.name == 'octagon' or self.name == 'victory point':
            return 1
        else:
            return 0
    
    def is_wild():
        return False