import copy

def final_value(coll):
    '''Returns a final total for VP based on 
    resources remaining at the end of the game.
    Takes in the resource pool.'''

    sum = 0
    for item in coll:
        if item == "VP":
            sum += 6*coll[item]
        elif item == "octagon":
            sum += 3*coll[item]
        elif item == "large" or item == "wild large" or item == "yellow" or item == "black" or item == "blue":
            sum += 1.5*coll[item]
        elif item == "small" or item == "wild small" or item == "brown" or item == "green" or item == "white" or item == "ship":
            sum += 1*coll[item]
    return sum/6

def trade_value(coll):
    '''Returns the current total resource 
    evaluation pre-end game calculations.
    Takes in the resource pool.'''

    sum = 0
    for item in coll:
        if item == "VP":
            sum += 3*coll[item]
        elif item == "octagon":
            sum += 3*coll[item]
        elif item == "large" or item == "wild large" or item == "yellow" or item == "black" or item == "blue":
            sum += 1.5*coll[item]
        elif item == "small" or item == "wild small" or item == "brown" or item == "green" or item == "white" or item == "ship":
            sum += 1*coll[item]
    return sum/6


def wrap_clear(rscs):
    '''Remove any resources with  a count of 0'''
    
    removes = []
    for item in rscs:
        if rscs[item] == 0:
            removes.append(item)
    
    for rem in removes:
         del rscs[rem]


def add(orig, amt):
    '''Takes in the resources and the 
    amount to be added, then adds them.'''
    for item in amt:
        if item in orig:
            orig[item] = orig[item] + amt[item]
        else:
            orig[item] = amt[item]
    
    wrap_clear(orig)

def can_be_subbed(orig, amt):
    '''Returns true if orig has sufficient resources to satisfy
    amt, false if not. Does not account for subtyping (i.e. the
    resources have to be the exact type to be satisfied)'''
    for item in amt:
        if item not in orig or orig[item] < amt[item]:
            return False
    return True
    

def sub(orig, amt):
    '''Takes in the resources and the amount 
    to be subtracted, then subtracts them.
    Raises a ValueError if the amount cannot be subtracted.'''
    
    if not can_be_subbed(orig,amt):
        raise ValueError("Insufficient amounts")
    
    for item in amt:
        if item in orig:
            orig[item] = orig[item] - amt[item]

    wrap_clear(orig)
        



def max_round(convs, need_inplay):
    '''Calculates the result if every resource-generating converter was run. Takes the converters and 
    whether the converters need to be in play to be calculated, and returns the trade value of the input and output'''
    begin_value = 0
    end_value = 0
    for conv in convs:
        if (not need_inplay) or conv.inplay:
            for con in conv.converters:
                b = trade_value(con[0])
                e = trade_value(con[1])
                if b <= e:
                    begin_value += b
                    end_value += e
    return begin_value,end_value


def isIn(cube,category):
    '''Returns True if the cube input 
    corresponds with the resource type 
    specified in the category input.'''
    
    return cube in subcategories(category)

def subcategories(category):
    ''' Returns the types that are subcategories of the given category'''
    if category == "large":
        return ["yellow","black","blue","wild large"]
    if category == "small":
        return ["green","brown","white","wild small"]
    if category == "colony":
        return ["jungle","ocean","ice","desert"]
    return []


def categoryType(cube):
    if cube == "yellow" or cube == "black" or cube == "blue" or cube == "wild large":
        return "large"
    if cube == "green" or cube == "brown" or cube == "white" or cube == "wild small":
        return "small"
    if cube == "jungle" or cube == "ice" or cube == "desert" or cube == "ocean":
        return "colony"
    else:
        return ""


def wildType(category):
    '''Returns the wild type of the given category.
    Guarantees giving the correct wild type if one exists,
    has undefined behavior if none exist'''
    
    return "wild " + category
    
    
    
def sub_with_wilds(orig, amt):
    '''Takes in the resources and the amount 
    to be subtracted, then subtracts them.
    Substitutes wilds for normal resources if necessary.
    Raises a ValueError if the amount cannot be subtracted.
    Orig is modified to give the result, and may be modified
    even if a value error is raised.'''
    
    
    for item in amt:
        short = amt.get(item) - orig.get(item,0)
        if short > 0: # Takes wilds after exhausting normal possibilities
            wtp = wildType(categoryType(item))
            if orig.get(wtp,0) >= short:
                orig[wtp] -= short
                orig[item] = orig.get(item,0) + short
            else:
                raise ValueError("Insufficient amounts of " + item)

    sub(orig,amt)
    
    
def get_primary_type(rscs):
    '''Returns the first non-wild resource in resource list'''

    for key in rscs:
        if key != wildType(categoryType(key)):
            return key