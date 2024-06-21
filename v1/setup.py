from loading import *
from converter import *
import random

def start_converters(converter_data):
    # Randomize converter order, put them into a list, and return it
    k = list(converter_data.items())
    random.shuffle(k)
    converter_data = dict(k)

    all_convs = []
    for con in converter_data:
        conv = Converter(make_info(converter_data[con]))
        all_convs.append(conv)
    return all_convs

def share_technology(invented, all_convs):
    # Do not call during confluence if Yengii invented since they license rather than share
    for tech in invented:
        for conv in all_convs:
            # The species that invented it should already have it in play, but the rest need to be set
            if conv.info["Name"] == tech:
                conv.inplay = True

def start_colonies(colony_data, all_convs, col_num):
    # Randomize colony order
    k = list(colony_data.items())
    random.shuffle(k)
    colony_data = dict(k)

    for col in colony_data:
        all_convs.append(Converter(make_info(colony_data[col])))
    colony_fill(all_convs,col_num,True)

def colony_fill(all_convs, num, caylion):
    # Puts num colonies into play, or as many as are available. Returns number put into play.
    # Caylion = is caylion buying one of these colonies? If so, double it.
    count = 0
    for col in all_convs:
        if count >= num:
            return num
        if col.info["Type"] == "Colony" and not col.inplay and col.info["Owner"] == {}:
            col.inplay = True
            count = count + 1
            if caylion and count == 1:
                col.colony_double()
    return count

def start_tech(colony_data,num):
    # Separate the research teams into tiers, then randomize them
    tiered_list = []
    for i in range(1,5):
        tiered_list.append([x[1] for x in list(colony_data.items()) if x[1]["Tier"] == str(i)])
    for x in tiered_list:
        random.shuffle(x)

    # Put the research teams back into a single list and put the correct number in play
    all_teams = [team for tier in tiered_list for team in tier]
    tech_fill(all_teams,num)
    return all_teams

def tech_fill(all_teams,num):
    # Puts num research teams into play, or as many as are available. Returns number put into play.
    count = 0
    for team in all_teams:
        if count >= num:
            return num
        if "Inplay" not in team or team["Inplay"] == False:
            team["Inplay"] = count < num
            count = count + 1
    return count