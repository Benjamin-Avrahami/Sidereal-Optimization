import java.io.*;


public class Player {
	private String playerRace;
	private int colonySupport;
	private int bidTiebreak;

	private ResourceCollection current_resources;
	private ResourceCollection future_resources;

	public Player(String race, int supported_colonies, int tiebreak) {
		playerRace = race;
		int colonySupport = supported_colonies;
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
	
	public void display() {
		System.out.println("The player of race " + playerRace + ", with colony support of " + colonySupport + " and a bid tiebreak of " + bidTiebreak + ", has:");
		System.out.println("Currently:");
		current_resources.display();
		System.out.println("Next turn:");
		future_resources.display();
	}


}
