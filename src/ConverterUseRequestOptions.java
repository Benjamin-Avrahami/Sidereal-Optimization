
public class ConverterUseRequestOptions {
	private String requestType;
	private int chosenConverter;
	private VirtualConverter chosenResources;
	
	public ConverterUseRequestOptions() {
		requestType = "";
		chosenConverter = 0;
		chosenResources = new VirtualConverter();
	}
	
	public void setRequestType(String reqType) {
		requestType = reqType;
	}
	
	public void setConverterChoice(int converterNum) {
		chosenConverter = converterNum;
	}
	
	public void setResourceChoice(VirtualConverter resources) {
		chosenResources = resources;
	}
	
	public String getRequestType() {
		return requestType;
	}
	
	public int getConverterChoice() {
		return chosenConverter;
	}
	
	public VirtualConverter getResourceChoice() {
		return chosenResources;
	}
}