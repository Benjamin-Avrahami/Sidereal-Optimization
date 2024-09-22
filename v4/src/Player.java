
public class Player {
	private SeparatedResourceCollection resources;
	private String player_name;
	private int colony_support;
	private int bid_tiebreak;
	
	public Player(String name, int col_support, int tiebreak) {
		player_name = name;
		colony_support = col_support;
		bid_tiebreak = tiebreak;
		resources = new SeparatedResourceCollection(new SameClassSubclassRule());
	}
	
	// Can add cubes, fleets, cards, colonies, etc
	public void add(Resource r) {
		resources.add(r);
	}
	
	public ResourceCollection getCubes() {
		Scorable test = null;
		return resources.getCollectionOfType(test);
	}
	
	public ResourceCollection getConverters() {
		GenericConverterCard test = null;
		return resources.getCollectionOfType(test);
	}
	
}