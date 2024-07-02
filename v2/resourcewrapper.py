


def change_to_new_resource_name(old_resource_name):
    if old_resource_name == 'wild small':
        return 'small grey'
    elif old_resource_name == 'wild large':
        return 'large grey'
    elif old_resource_name == 'VP':
        return 'victory point'
    else:
        return old_resource_name


# Creates the appropriate subclass of Resource
# Accepts brown, white, green, small grey, yellow, black, blue, large grey, ship, octagon, victory point, wild small, wild large, and VP
def create_resource(resource_name):
    new_resource_name = change_to_new_resource_name(resource_name)

    if new_resource_name in ['brown', 'white', 'green', 'small grey']:
        return SmallCube(new_resource_name)
    elif new_resource_name in ['yellow', 'black', 'blue', 'large grey']:
        return LargeCube(new_resource_name)
    elif new_resource_name in ['ship', 'octagon', 'victory point']:
        return OtherScorable(new_resource_name)
    else:
        return None