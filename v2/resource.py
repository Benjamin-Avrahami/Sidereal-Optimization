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
        
        
        
class Scorable(Resource):
    # Suggested trade value (anchored to 1 victory point = 1)
    def tradeValue():
        pass
        
    # Victory point total of the resource at the end of the game
    def scoringValue():
        if self.name == 'victory point':
            return tradeValue()
        else:
            return tradeValue()/2

    def isWild():
        pass
        



# brown, white, green, small grey
class SmallCube(Scorable):
    def tradeValue():
        return 1/3
        
    def isWild():
        return self.name == 'small grey'
        

# yellow, black, blue, large grey
class LargeCube(Scorable):
    def tradeValue():
        return 1/2
        
    def isWild():
        return self.name == 'large grey'
        

# ship, octagon, victory point
class OtherScorable(Scorable):
    def tradeValue():
        if self.name == 'ship':
            return 1/3
        elif self.name == 'octagon' or self.name == 'victory point':
            return 1
        else:
            return 0
    
    def isWild():
        return False