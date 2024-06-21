import csv


def read_file(filename,typ):
    '''Returns the values in the input csv.'''

    with open(filename) as csvfile:
        headers = []
        isheader = True
        data = {}
        getdata = csv.reader(csvfile)
        for row in getdata:
            if isheader:
                for column in row:
                    headers.append(column)
                isheader = False
            else:
                index = 0
                if "Race" in headers:
                    name = row[1] + "_" + row[0]
                else:
                    name = row[0]
                data[name] = {}
                data[name]["Type"] = typ
                for column in row:
                    data[name][headers[index]] = column
                    index = index + 1
        return data


def MAXSCORE_readsetup():
    '''Returns the starting setup 
    with all relevant information.'''

    with open('Sidereal Tracker - Game Setup.csv') as csvfile:
        ret = []
        ret.append([])
        getdata = csv.reader(csvfile)
        rscs = []
        for row in getdata:
            if row[0] == "Species" or row[0] == "Players" or row[0] == "":
                continue
            elif row[0].isnumeric():
                if int(row[0]) == 9:
                    for i in range(2,5):
                        ret.append(list(map(int,row[i].split(','))))
                elif int(row[0]) == 10:
                    ret[0] = list(map(int,row[1].split(',')))
            else:
                rscs.append(MAXSCORE_parse(row[1]))
        ret.append(rscs)
        return ret





def MAXSCORE_parse(s):
    '''Returns the parsed version of the 
    particular input specified.'''

    if s == "None":
        return {}
    literals = ["octagon", "black", "blue", "yellow", "brown", "white", "green", "ship", "VP", "fleet", "donation", "stolen", "ice", "jungle", "desert", "ocean", "colony", "small", "large", "wild small", "wild large", "envoy", "research team"]
    
    inventory = {}

    str = s
    orsplit = s.split(" or ") # For Zeth choices to put in an envoy or not
    for choice in orsplit:
        if "envoy" not in choice:
            str = choice

    cubes = str.split(',')
    for cube in cubes:
        sides = cube.split('(')
        maxcutcube = sides[0].strip()
        words = maxcutcube.split(' ')
        if not words[0].isnumeric():
            return None
        cubetype = maxcutcube[len(words[0])+1:]
        if cubetype not in literals:
            return None
        sub = 0
        if len(sides) > 1:
            othercut = sides[1].split(')')[0]
            otherwords = othercut.split(' ')
            if otherwords[1] == "stolen":
                sub = int(otherwords[0])
        if sub < int(words[0]):
            inventory[cubetype] = int(words[0]) - sub
    inventory.pop("envoy",None)
    return inventory

def make_info(d):
    '''Creates an info dictionary consisting of
    parsed elements from MAXSCORE_parse.
    Takes in the overarching dictionary.'''

    info = {}
    for item in d:
        parsed = MAXSCORE_parse(d[item])
        if parsed == None:
            info[item] = d[item]
        else:
            info[item] = parsed
    return info


# print(MAXSCORE_parse(read_file('Sidereal Tracker - Converters.csv')["Caylion_Matter Generation"]["Upgraded Output"]))

# print(read_file('Sidereal Tracker - Converters.csv'))


if __name__ == "__main__":
    print(MAXSCORE_readsetup())

# print(make_info(read_file('Sidereal Tracker - Converters.csv')['Zeth_Black Market']))
