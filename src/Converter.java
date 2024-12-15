import java.util.*;
import java.io.*;

public class Converter {
	private ResourceCollection inputResources;
	private ResourceCollection outputResources;

	public Converter(ResourceCollection resourceInput, ResourceCollection resourceOutput) {
		inputResources = resourceInput.getCopy();
		outputResources = resourceOutput.getCopy();
	}

	public Converter getCopy() {
		Converter conv = new Converter(this.inputResources, this.outputResources);
		return conv;
	}
	
	public ResourceCollection getInputs() {
		return inputResources.getCopy();
	}
	
	public ResourceCollection getOutputs() {
		return outputResources.getCopy();
	}
	

	
	// Attempts to run the converter using inputs from beginningResources, then adds the output to endingResources
	// beginningResources can be the same ResourceCollection as endingResources (in which case, the outputs
	// would be immediately added back to the same collection)
	// Returns True if successfully executed, False if not (not enough starting resources)
	public boolean execute(ResourceCollection beginningResources, ResourceCollection endingResources) {
		return ConverterUtils.execute(inputResources, outputResources, beginningResources, endingResources);
	}
	
	
	// Attempts to run the converter using the inputs and outputs as modified by chosenResources from beginning and endingResources
	// beginningResources can be the same ResourceCollection as endingResources (in which case, the outputs
	// would be immediately added back to the same collection)
	// Returns True if successfully executed, False if not (not enough starting resources or chosenResources is bad)
	public boolean execute(ResourceCollection beginningResources, ResourceCollection endingResources, VirtualConverter chosenResources) {
		// need to make a copy since there are two removes, either of which can fail
		ResourceCollection beginningCopy = beginningResources.getCopy();
		boolean virtualResult = chosenResources.executeOnInputs(beginningResources);
		if (!virtualResult) {
			return false;
		}
		boolean result = ConverterUtils.execute(inputResources, outputResources, beginningResources, endingResources);
		if (!result) {
			beginningResources = beginningCopy;
		}
		else {
			chosenResources.executeOnOutputs(endingResources);
		}
		return result;
	}


	// Doubles the inputs and outputs of the converter
	public void doubleConverter() {
		ResourceCollection inputCopy = inputResources.getCopy();
		ResourceCollection outputCopy = outputResources.getCopy();
		inputResources.addAll(inputCopy);
		outputResources.addAll(outputCopy);
	}
	
	
	// Prints information on converter (input/output resources)
	public void display() {
		System.out.println("Converter Information:");
		System.out.println("Input:");
		inputResources.display();
		System.out.println("Output:");
		outputResources.display();
	}
}
