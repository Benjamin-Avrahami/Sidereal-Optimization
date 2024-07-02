from abc import ABC

class Resource(ABC):
    def __init__(self, resource_name):
        self.name = resource_name
    
    def get_name():
        return self.name
        
    def same_type(other_resource):
        return self.name == other_resource.name
        
        
        
class Scorable(Resource):
    def tradeValue():
        pass
        
    def scoringValue():
        if self.name == 'VP':
            return tradeValue()
        else:
            return tradeValue()/2
        
        
class Cube(Scorable):
    def isWild():
        pass




# brown, white, green, small grey
class SmallCube(Cube):
    def tradeValue():
        return 1/3
        
    def isWild():
        return self.name == 'small grey'
        

# yellow, black, blue, large grey
class LargeCube(Cube):
    def tradeValue():
        return 1/2
        
    def isWild():
        return self.name == 'large grey'
        

# ship, octagon, VP
class OtherScorable(Scorable):
    def tradeValue():
        if self.name == 'ship':
            return 1/3
        elif self.name == 'octagon' or self.name == 'VP':
            return 1
        else:
            return 0