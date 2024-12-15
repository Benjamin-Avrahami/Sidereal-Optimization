

public class ConverterUtils {
	// Given a converter that requires resourcesRequired and produces resourcesProduced, and starting with beginningResources,
	// attempt to run the converter, taking from beginningResources and putting into endingResources
	// Returns true if successful, and false if not
	// If unsuccessful, does not change beginning or endingResources
	public static boolean execute(ResourceCollection resourcesRequired, ResourceCollection resourcesProduced, ResourceCollection beginningResources, ResourceCollection endingResources) {
		boolean result = beginningResources.removeAll(resourcesRequired);
		if (!result) {
			return false;
		}
		endingResources.addAll(resourcesProduced);
		return true;
	}
}