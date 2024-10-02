
public class RandomDeck extends Deck {
  private Deque<GameObject> deck_cards;

  // Creates a randomly ordered deck from objects
  public RandomDeck(List<GameObject> objects) {
    ArrayList<GameObject> unshuffled_cards = new ArrayList<GameObject>(objects);
    Collections.shuffle(unshuffled_cards);
    deck_cards = new ArrayDeque<GameObject>(unshuffled_cards);
  }

  // Creates a randomly ordered deck from objects
  public RandomDeck(ResourceCollection objects) {
    ResourceCollection object_copy = objects.getCopy();
    ArrayList<GameObject> unshuffled_cards = new ArrayList<GameObject>();
    while (object_copy.distinctObjects() > 0) {
      GameObject r = object_copy.resourceIterator().next();
      int amt = obj.getAmount(r);
      object_copy.remove(r,amt);
      for (int i = 0; i < amt; i++) {
        unshuffled_cards.add(r);
      }
    }
    Collections.shuffle(unshuffled_cards);
    deck_cards = new ArrayDeque<GameObject>(unshuffled_cards);
  }

  // Removes the top value/card from the deck and returns it
  public GameObject draw() {
    return deck_cards.poll();
  }

  // Returns the number of items in the deck
  public int size() {
    return deck_cards.size();
  }
}
