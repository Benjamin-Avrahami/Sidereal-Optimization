
// The request that a player sends to a converter in order to run it
public class ConverterUseRequest {
	private Player p;
	private ConverterCard c;
	private Game g;
	// Enter Play/Upgrade/Convert
	private String requestType;
	// If more than one option to do the request on the converter, choose which one to do
	private int converterChoice;
	// If the resources needed by the converter are underspecific/generic, choose the specific cubes
	private VirtualConverter resourceChoice;
	
	public ConverterUseRequest(ConverterCard converterUsed, Player playerUsing, Game gameContext) {
		p = playerUsing;
		c = converterUsed;
		g = gameContext;
		requestType = "";
		converterChoice = -1;
		resourceChoice = new VirtualConverter();
	}
	
	public void setRequestType(String request) {
		requestType = request;
	}
	
	public void setConverterChoice(int choice) {
		converterChoice = choice;
	}
	
	public void setResourceChoice(VirtualConverter choice) {
		resourceChoice = choice;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public ConverterCard getConverter() {
		return c;
	}
	
	public Game getGame() {
		return g;
	}
	
	public String getRequestType() {
		return requestType;
	}
	
	public int getConverterChoice() {
		return converterChoice;
	}
	
	public VirtualConverter getResourceChoice() {
		return resourceChoice;
	}
	
	
	// Returns true if all fields have been filled in, false if not
	public boolean isCompleteRequest() {
		return (!(g == null || p == null || c == null || requestType.equals("") || converterChoice == -1 || resourceChoice == null));
	}
}