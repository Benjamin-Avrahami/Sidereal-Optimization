import java.io.*;


public class Player {
	private String playerRace;
	private int colonySupport;
	private int bidTiebreak;

	private ResourceCollection current_resources;
	private ResourceCollection future_resources;
	
	private Game withinGame;

	public Player(Game siderealGame, String race, int supportedColonies, int tiebreak) {
		withinGame = siderealGame;
		playerRace = race;
		int colonySupport = supportedColonies;
		bidTiebreak = tiebreak;
		current_resources = new ResourceCollection();
		future_resources = new ResourceCollection();
	}
	
	public void setCurrentResources(ResourceCollection newResources) {
		current_resources = newResources;
	}
	
	public String getRace() {
		return playerRace;
	}
	
	public int getColonySupport() {
		return colonySupport;
	}
	
	public int getBidTiebreak() {
		return bidTiebreak;
	}
	
	
	public ResourceCollection getCurrentResources() {
		return current_resources;
	}
	
	public ResourceCollection getFutureResources() {
		return future_resources;
	}
	
	public ResourceCollection getConverters() {
		return current_resources.getObjectsOfType("Converter");
	}
	
	public ResourceCollection getCubes() {
		return current_resources.getObjectsOfType("Cube");
	}
	
	// Create a basic converter usage request and returns it
	// Sets only the player/converter/game
	// Does not have any options within the individual converter (which action to do within the converter, etc.)
	private ConverterUseRequest createRequest(ConverterCard cc) {
		ConverterUseRequest convReq = new ConverterUseRequest(cc, this, withinGame);
		return convReq;
	}
	
	// Creates the further options beyond those created by the basic request, as given by the ConverterUseRequestOptions
	// Sets the request type (run/enter/upgrade), as well as choices for indivudal converter and cubes, if necessary
	private void addSubOptions(ConverterUseRequest converterRequest, ConverterUseRequestOptions suboptions) {
		converterRequest.setRequestType(suboptions.getRequestType());
		converterRequest.setConverterChoice(suboptions.getConverterChoice());
		converterRequest.setResourceChoice(suboptions.getResourceChoice());
	}
	
	// Runs the converter on the card, with individual options as specified by requestOptions
	// requestOptions must have the request type
	// If successful, returns true and the requisite resources will be removed from the player's current resources and 
	// added to either the player's current or future resources
	// If unsuccessful, returns false and does not modify the player
	public boolean converterUse(ConverterCard card, ConverterUseRequestOptions requestOptions) {
		ConverterUseRequest converterRequest = createRequest(card);
		addSubOptions(converterRequest, requestOptions);
		return card.useConverter(converterRequest);
	}
	
	// Runs the converter to enter play, with further options specified by requestOptions
	// If successful, returns true, the card enters play, the requisite resources will be removed from the player's current
	// resources and are added to either the player's current or future resources
	// If unsuccessful, returns false and does not change
	public boolean converterEnterPlay(ConverterCard card, ConverterUseRequestOptions requestOptions) {
		requestOptions.setRequestType("Enter Play");
		return converterUse(card, requestOptions);
	}
	
	// Runs the converter to upgrade the card, with further options specified by requestOptions
	// If successful, returns true, the card upgrades, the requisite resources will be removed from the player's current
	// resources and are added to either the player's current or future resources
	// If unsuccessful, returns false and does not change
	public boolean converterUpgrade(ConverterCard card, ConverterUseRequestOptions requestOptions) {
		requestOptions.setRequestType("Upgrade");
		return converterUse(card, requestOptions);
	}
	
	// Runs the converter, with further options specified by requestOptions
	// If successful, returns true and the requisite resources will be removed from the player's current
	// resources and are added to either the player's current or future resources
	// If unsuccessful, returns false and does not modify resources
	public boolean converterRunConverter(ConverterCard card, ConverterUseRequestOptions requestOptions) {
		requestOptions.setRequestType("Convert");
		return converterUse(card, requestOptions);
	}
	
	
	public void display() {
		System.out.println("The player of race " + playerRace + ", with colony support of " + colonySupport + " and a bid tiebreak of " + bidTiebreak + ", has:");
		System.out.println("Currently:");
		current_resources.display();
		System.out.println("Next turn:");
		future_resources.display();
	}


}
