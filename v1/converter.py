from helpers import *
import math
import copy

class Converter(object):
    def __init__(self,info):
        '''Takes in info, a dictionary originating
        as a single row of a csv.'''
        
        self.info = info
        self.inplay = info["Type"] == "Converter" and info['Cost of Entering Play'] == {}
        self.flipped = False
        self.hasrun = False
        self.converters = []
        conv1 = []
        conv1.append(self.info["Input"])
        conv1.append(self.info["Output"])
        self.converters.append(conv1)
        if "Alternate Output" in self.info and self.info["Alternate Output"] != {}:
            conv2 = []
            conv2.append(self.info["Alternate Input"])
            conv2.append(self.info["Alternate Output"])
            self.converters.append(conv2)

    def colony_double(self):
        add(self.info["Input"],self.info["Input"])
        add(self.info["Output"],self.info["Output"])
        add(self.info["Upgraded Input"],self.info["Upgraded Input"])
        add(self.info["Upgraded Output"],self.info["Upgraded Output"])

    def flip(self):
        '''Flips the converter, changing the converter(s)
        available to the upgraded versions.'''
        
        self.flipped = True
        self.converters = []
        conv1 = []
        conv1.append(self.info["Upgraded Input"])
        conv1.append(self.info["Upgraded Output"])
        self.converters.append(conv1)
        if "Alternate Upgraded Output" in self.info and self.info["Alternate Upgraded Output"] != {}:
            conv2 = []
            conv2.append(self.info["Alternate Upgraded Input"])
            conv2.append(self.info["Alternate Upgraded Output"])
            self.converters.append(conv2)
        if "Third Upgraded Output" in self.info and self.info["Third Upgraded Output"] != {}:
            conv3 = []
            conv3.append(self.info["Third Upgraded Input"])
            conv3.append(self.info["Third Upgraded Output"])
            self.converters.append(conv3)

    def conprod(self, consume, produce, resources, choices):
        # Simulates adding and subracting the given resources from the initial list, and returns the cubes produced (not added to initial)
        # resources = Initial resources
        # consume = Resources used (subtracted)
        # produce = Resources gained (added)
        # choices = If given a choice of different types of cubes to enter, determine which one to use
        
        rcopy = copy.deepcopy(resources)
        add_amt = {}
        
        for cubetype in choices:
            total = 0
            for cubechoice in choices[cubetype]:
                total += choices[cubetype][cubechoice]
            sub(rcopy, choices[cubetype])
            add(rcopy, {cubetype: total})
        
        try:
            sub_with_wilds(rcopy, consume)
        except ValueError:
            return None
            
        # Once we know it is good, repeat with actual resources (so changes can persist beyond function)
        for cubetype in choices:
            total = 0
            for cubechoice in choices[cubetype]:
                total += choices[cubetype][cubechoice]
            sub(resources, choices[cubetype])
            add(resources, {cubetype: total})
        sub_with_wilds(resources, consume)
        
        add(add_amt, produce)
        for ctype in produce:
            if ctype in choices:
                actual_color = get_primary_type(choices[ctype])
                wild_color = wildType(categoryType(actual_color))
                sub(add_amt, {ctype: produce[ctype]})
                add(add_amt, {actual_color: produce[ctype]})
                sub(add_amt, {actual_color: choices.get(wild_color,0)})
                add(add_amt, {wild_color: choices.get(wild_color,0)})
        return add_amt
        

    def run(self,resources,index,choices):
        '''Checks to see if a converter can be run. 
        If it can, then it runs it. Takes in the 
        resource pool, the specific converter (0, 1, 
        or 2), and the choice of input being checked.'''
        return self.conprod(self.converters[index][0],self.converters[index][1],resources,choices)

    def ratio(self,index):
        inval = trade_value(self.converters[index][0])
        outval = trade_value(self.converters[index][1])
        if inval == 0:
            return math.inf
        return outval/inval