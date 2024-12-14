
public class ConverterUseRequestOptions {
	private String requestType;
	private int chosenConverter;
	private ConverterResourceChoice chosenResources;
	
	public ConverterUseRequestOptions() {
		requestType = "";
		chosenConverter = 0;
		chosenResources = null;
	}
	
	public void setRequestType(String reqType) {
		requestType = reqType;
	}
	
	public void setConverterChoice(int converterNum) {
		chosenConverter = converterNum;
	}
	
	public void setResourceChoice(ConverterResourceChoice resources) {
		chosenResources = resources;
	}
	
	public String getRequestType() {
		return requestType;
	}
	
	public int getConverterChoice() {
		return chosenConverter;
	}
	
	public ConverterResourceChoice getResourceChoice() {
		return chosenResources;
	}
}