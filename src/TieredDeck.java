
// Like a random deck, but is organized in tiers - everything in tier 1 comes before everything in tier 2
// comes before ... , but is random within the tiers
public class TieredDeck extends Deck {
  private ArrayList<RandomDeck> tier_cards;
  int index;

  // Creates a tiered deck from the list, each sublist containing that tier
  // 0-th sublist will be taken out first, then 1-th, then so on
  public TieredDeck(ArrayList<List<GameObject>> l_objects) {
    tier_cards = new ArrayList<RandomDeck>();
    for (int i = 0; i < l_objects.size(); i++) {
      tier_cards.add(new RandomDeck(l_objects.get(i)));
    }
    index = 0;
  }

  // Creates a tiered deck from the list, each sublist containing that tier
  // 0-th sublist will be taken out first, then 1-th, then so on
  public TieredDeck(ArrayList<ResourceCollection> l_objects) {
    tier_cards = new ArrayList<RandomDeck>();
    for (int i = 0; i < l_objects.size(); i++) {
      tier_cards.add(new RandomDeck(l_objects.get(i)));
    }
    index = 0;
  }

  // Removes the top value/card from the deck and returns it
  public GameObject draw() {
    while (index < tier_cards.size() && tier_cards.get(index).size() == 0) {
      index++;
    }
    if (index >= tier_cards.size()) return null;
    return tier_cards.get(index).draw();
  }

  // Returns the number of items in the deck
  public int size() {
    int total = 0;
    for (int i = 0; i < tier_cards.size(); i++) {
      total += tier_cards.get(i).size();
    }
    return total;
  }
}
