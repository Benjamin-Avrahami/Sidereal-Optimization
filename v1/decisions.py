from loading import *
from converter import *
from setup import *
import copy

def decide_what_to_invent_and_upgrade(rscs,all_convs,all_teams,normal_sb,yengii_sb,rd):
    pass

def order(all_convs):
    mp = {}
    ktzrktrtl = [-1,-1,-1,-1,-1,-1]
    for i in range(len(all_convs)):
        if all_convs[i].info["Name"] == "Anarchic Sacrificial":
            ktzrktrtl[0] = i
        elif all_convs[i].info["Name"] == "High-Risk Laboratories":
            ktzrktrtl[1] = i
        elif all_convs[i].info["Name"] == "Hand Crafted":
            ktzrktrtl[2] = i
        elif all_convs[i].info["Name"] == "Polyutility Components":
            ktzrktrtl[3] = i
        elif all_convs[i].info["Name"] == "Expansive Social":
            ktzrktrtl[4] = i
        elif all_convs[i].info["Name"] == "Diffusion":
            ktzrktrtl[5] = i
        else:
            if all_convs[i].inplay:
                for j in range(len(all_convs[i].converters)):
                    rat = all_convs[i].ratio(j)
                    if rat < 1:
                        continue
                    if rat not in mp:
                        mp[rat] = []
                    mp[rat].append([i,j])
    for i in range(3):
        kinfo = copy.deepcopy(all_convs[ktzrktrtl[2*i]].info)
        kinfo["Name"] = "Kt'zr'kt'rtl Construct " + str(i+1)
        inp = {}
        add(inp,all_convs[ktzrktrtl[2*i]].converters[0][0])
        add(inp,all_convs[ktzrktrtl[2*i+1]].converters[0][0])
        kinfo["Input"] = inp
        outp = {}
        add(outp,all_convs[ktzrktrtl[2*i]].converters[0][1])
        add(outp,all_convs[ktzrktrtl[2*i+1]].converters[0][1])
        kinfo["Output"] = outp
        k = Converter(kinfo)
        all_convs.append(k)
        rat = k.ratio(0)
        if rat not in mp:
            mp[rat] = []
        mp[rat].append([len(all_convs)-1,0])
    return sorted(mp.items(),reverse=True)

def make_choice(rscs,input,same):
    # If given choices on which type of cube to enter, make the decision
    # rscs = Current resources
    # input = The input cost for the converter
    # same = Are all of the cubes in a type required to be the same color
    choices = {}
    for cubetype in input:
        ch = subcategories(cubetype)
        if ch != []:
            choices[cubetype] = {}
            if same:
                hinum = -1
                hi = ""
                for c in ch:
                    if c == 'wild small' or c == 'wild large':
                        continue
                    if c in rscs and rscs[c] > hinum:
                        hinum = rscs[c]
                        hi = c
                if hinum >= input[cubetype]:
                    choices[cubetype][hi] = input[cubetype]
                else:
                    ind = ch.find('wild small')
                    if ind == -1:
                        ind = ch.find('wild large')
                    if ind == -1:
                        return {}
                    if hinum + rscs[ch[ind]] < input[cubetype]:
                        return {}
                    else:
                        if hinum > 0:
                            choices[cubetype][hi] = hinum
                        choices[cubetype][ch[ind]] = input[cubetype] - hinum

            else:
                total = input[cubetype]
                for c in ch:
                    if c in rscs and rscs[c] > 0:
                        total -= rscs[c]
                        choices[cubetype][c] = rscs[c] + min(total,0)
                        if total <= 0:
                            break
    return choices
    
    
    
    
    
    

def spend_decisions(rscs, amt, same):
    
    norig = copy.deepcopy(rscs)
    
    spending = {}
    
    # Goes through the other resources needed because they will be used in the converter
    for item in amt:
        if item in ["large", "small", "colony"]:
            continue
        norig[item] = norig.get(item,0) - min(norig.get(item,0),amt.get(item))
        short = amt.get(item) - norig.get(item,0)
        if short > 0:
            wtp = wildType(categoryType(item))
            if norig.get(wtp,0) >= short:
                norig[wtp] -= short
            else:
                return {}
            
    subcats = [[cat,subcategories(cat)] for cat in amt]
    e_subcats = [s for s in subcats if s[1] != []]
            
    for esubcs in e_subcats:
        cat = esubcs[0]
        
        if norig.get(cat,0) >= amt.get(cat):
            continue
        spending[cat] = {}
        wtp = wildType(cat)
        subc_groups = [[subc, norig.get(subc,0)] for subc in esubcs[1] if subc != wildType(cat)]
            
        if same:
            top = max(subc_groups, key=lambda group: group[1])
            if top[1] + norig.get(wtp,0) < amt.get(cat):
                return {}
            spending[cat][top[0]] = min(amt[cat] - norig.get(cat,0), norig.get(top[0]))
            if top[1] < amt.get(cat):
                spending[cat][wtp] = amt.get(cat) - top[1]
            
        else:
            subc_sum = sum([group[1] for group in subc_groups])
            if subc_sum < amt.get(cat) and subc_sum + norig.get(wtp,0) >= amt.get(cat):
                subc_groups.append([wtp, amt[cat] - subc_sum])
            splits = split_among_several(subc_groups, amt.get(cat) - norig.get(cat,0))
            if splits == []:
                return {}
            for split in splits:
                spending[cat][split[0]] = split[1]

        
    return spending
    

def split_among_several(groups, total):
    '''Returns the amount to take from each group so that the total adds 
    up to total and the ending distribution is as even as possible.
    Does not take a negative amount from any group.
    Total is a number and groups is a list of lists, where the first value 
    is the name and the second is the amount initially had.'''

    if sum([group[1] for group in groups]) < total or total == 0:
        return []
    
    piles = sorted(groups, key=lambda group: group[1], reverse=True)
    piles.append(['',0]) # Append a dummy value to the end to make comparisons easier
    
    
    splits = [[pile[0],0] for pile in piles]
    
    last_add = 0
    add_tot = 0
    
    while add_tot < total:
        while piles[last_add][1] - splits[last_add][1] > piles[last_add+1][1] - splits[last_add+1][1] and add_tot < total:
            for i in range(0,last_add+1):
                splits[i][1] += 1
                add_tot += 1
                if add_tot >= total:
                    break
        last_add += 1
    
    while splits[-1][1] == 0:
        splits.pop()
    return splits
    

    
        


if __name__ == "__main__":
    ls = [['colony', 1], ['black', 2], ['green', 5], ['white', 4], ['brown', 2], ['ship', 1]]
    
    print(split_among_several(ls,8))
    print(ls)