from loading import *
from converter import *
from setup import *
from decisions import *
import copy


def begin():
    converter_data = read_file('Sidereal Tracker - Converters.csv',"Converter")
    all_convs = start_converters(converter_data)

    setup_data = MAXSCORE_readsetup()
    rscs = {}
    for species in setup_data[4]:
        add(rscs,species)

    colony_data = read_file('Sidereal Tracker - Colonies.csv',"Colony")
    start_colonies(colony_data,all_convs,rscs['colony'])
    del rscs['colony']

    tech_data = read_file('Sidereal Tracker - Technologies.csv',"Technology")
    all_teams = start_tech(tech_data,rscs['research team'])
    del rscs['research team']

    #for x in all_convs:
        #print(x.info)

    # 0/1 = Colony/Research Bid Track, 2/3 = Normal/Yengii Sharing Bonus per round
    costbonus = setup_data[0:4]
    print(costbonus)

    #print(rscs)

    # simulate_round(all_convs,rscs)

    # leftover, upcoming, r_first, r_last = simulate_round(all_convs,rscs)
    # consensus_r = (r_first + r_last)/2
    # print(leftover)
    # print(upcoming)
    # print(consensus_r)

    # leftover = copy.deepcopy(rscs)
    # print(leftover)
    # print(final_value(leftover),end="\n\n\n")

    # for rd in range(6):
    #     leftover, upcoming = run_converters(all_convs,leftover)
    #     print(leftover)
    #     print(upcoming)
    #     add(leftover,upcoming)
    #     print(leftover)
    #     print(final_value(leftover),end="\n\n\n")

    #     colony_bid(costbonus[0],all_convs,leftover,rd)
    #     tech_bid(costbonus[1],all_teams,leftover)
    
    play_round(rscs,all_convs,all_teams,costbonus[0],costbonus[1],costbonus[2],costbonus[3],0)
        

def play_round(resources,all_converters,all_teams,colony_bid_track,research_bid_track,normal_sharing_bonus,yengii_sharing_bonus,round_number):
    
    decide_what_to_invent_and_upgrade(resources,all_converters,all_teams,normal_sharing_bonus,yengii_sharing_bonus,round_number)
    leftover_rscs, upcoming_rscs = run_converters(all_converters,resources)
    add(upcoming_rscs,leftover_rscs)
    confluence(colony_bid_track,research_bid_track,resources,all_converters,all_teams,round_number)


    
def run_converters(all_convs, rscs):
    cubes = {}
    for item in rscs:
        cubes[item] = rscs[item]
    ord = order(all_convs)
    #print(ord,end="\n\n\n")

    #print("Trade\n")
    # Run the trade phase converters first
    # for tup in ord:
        # convs = tup[1]
        # for conv in convs:
            # if "Notes" in all_convs[conv[0]].info and all_convs[conv[0]].info["Notes"].lower().find("trade phase") != -1:
                # choices = make_choice(cubes,all_convs[conv[0]].converters[conv[1]][0],all_convs[conv[0]].info["Type"] == "Converter" and all_convs[conv[0]].info["Race"] == "Eni Et")
                # output = all_convs[conv[0]].run(cubes,conv[1],choices)
                # add(cubes,output)
                # #print(all_convs[conv[0]].info["Race"] if all_convs[conv[0]].info["Type"] == "Converter" else "--")
                # #print(all_convs[conv[0]].info["Name"])
                # #print(tup[0])
                # #print(all_convs[conv[0]].converters[conv[1]])
                # #print(choices)
                # #print(output)
                # #print(cubes, end="\n\n")
    # Remove trade converters from economy running
    orde = [(tup[0], [conv for conv in tup[1] if not ("Notes" in all_convs[conv[0]].info and all_convs[conv[0]].info["Notes"].lower().find("trade phase") != -1)]) for tup in ord]

    print(cubes)
    print(orde)
    

    #print("------\n\nEconomy")
    next_round = {}
    for tup in orde:
        convs = tup[1]
        for conv in convs:
            choices = spend_decisions(cubes,all_convs[conv[0]].converters[conv[1]][0],all_convs[conv[0]].info["Type"] == "Converter" and all_convs[conv[0]].info["Race"] == "Eni Et")
            output = all_convs[conv[0]].run(cubes,conv[1],choices)
            print(all_convs[conv[0]].info["Race"] if all_convs[conv[0]].info["Type"] == "Converter" else "Colony/Tech")
            print(all_convs[conv[0]].info["Name"])
            print(tup[0])
            print(all_convs[conv[0]].converters[conv[1]])
            print(choices)
            print(output)
            if output != None:
                add(next_round,output)
            print(cubes)
            print(next_round, end="\n\n")

    return (cubes, next_round)

def confluence(col_bid_track, res_bid_track, rscs, all_convs, all_teams, rd, invented):
    colony_bid(col_bid_track,all_convs,rscs,rd)
    tech_bid(res_bid_track,all_teams,rscs)
    share_technology(invented,all_convs)


def colony_bid(bid_track,all_convs,rscs,round):
    count = 0
    # Only buy the colonies that can break even
    for bid in bid_track:
        if bid >= 6 - round:
            break
        count = count + 1
    # Put the colonies into play
    num_bought = colony_fill(all_convs,count,True)
    print(num_bought)
    # Pay for the colonies
    for i in range(num_bought):
        sub(rscs,{'ship': bid_track[i]})
    # Caylion pays double for theirs
    sub(rscs,{'ship': bid_track[0]})

def tech_bid(bid_track,all_teams,rscs):
    num_bought = tech_fill(all_teams,len(bid_track))
    print(num_bought)
    for i in range(num_bought):
        sub(rscs,{'ship': bid_track[i]})
    


begin()