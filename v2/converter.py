from collection import *



class Converter():
    def __init__(self, res_in: ResourceCollection, res_out: ResourceCollection):
        self.resource_input = res_in
        self.resource_output = res_out


    # Runs the converter (using the resources for inputs), adding the outputs back to the given resource set
    # If not possible to run the converter (not enough resources to input), raises a ValueError
    def instant_execution(self, starting_resources: ResourceCollection):
        starting_resources.remove_all(res_in)
        starting_resources.add_all(res_out)
    
    # Tries to run the converter (using the resources for inputs), then add the outputs back to the given resource set
    # Returns True if successfully executed, False if not (not enought starting resources)
    # Same as instant_execution except returns False instead of raising an error
    def instant_execution_if_possible(self, starting_resources: ResourceCollection):
        try:
            self.instant_execution(starting_resources)
            return True
        except:
            return False
    
    
    # Runs the converter (using the resources for inputs), then returns the output (to be used in the next round)
    # If not possible to run the converter (not enough resources to input), raises a ValueError
    def delayed_execution(self, starting_resources: ResourceCollection):
        starting_resources.remove_all(res_in)
        return res_out
    
    # Runs the converter (using the resources for inputs)
    # Returns a tuple, the first value of which is whether the running was successfully done, and the second value 
    # is the output given by running (or not), which is the output to be used in the next round
    # Same as delayed_execution, but returns a tuple with True/False as well as the output, and does not throw an error
    def delayed_execution_if_possible(self, starting_resources: ResourceCollection):
        try:
            ex_output = self.instant_execution(starting_resources)
            return (True, ex_output)
        except:
            return (False, ResourceCollection())
    
    # Doubles the inputs and outputs of the converter
    def double(self):
        input_copy = ResourceCollection(other_collection=resource_input)
        output_copy = ResourceCollection(other_collection=resource_output)
        resource_input.add_all(input_copy)
        resource_output.add_all(output_copy)