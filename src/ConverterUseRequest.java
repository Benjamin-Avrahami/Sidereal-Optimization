
// The request that a player sends to a converter in order to run it
public class ConverterUseRequest {
	private Player p;
	private ConverterCard c;
	private Game g;
	// enter play/upgrade/run
	private String requestType;
	// If more than one option to do the request on the converter, choose which one to do
	private int converterChoice;
	// If the resources needed by the converter are underspecific/generic, choose the specific cubes
	private ResourceCollection resourceChoice;
	
	public ConverterUseRequest(Game gameIn, Player playerRequesting, ConverterCard converterRun, String request) {
		g = gameIn;
		p = playerRequesting;
		c = converterRun;
		requestType = request;
		// by default the first option
		converterChoice = 0;
		// if not specified, the resources used are the resources specified
		resourceChoice = null;
	}
	
	public void setConverterChoice(int choice) {
		converterChoice = choice;
	}
	
	public void setResourceChoice(ResourceCollection choice) {
		resourceChoice = choice;
	}
	
	public Player getPlayerRequesting() {
		return p;
	}
	
	public ConverterCard getConverter() {
		return c;
	}
	
	public String getRequestType() {
		return requestType;
	}
	
	public Converter converterAccessed;
}