
import java.util.*;

public class RandomDeck extends Deck {
  private Deque<Consumable> deck_cards;

  // Creates a randomly ordered deck from objects
  public RandomDeck(List<Consumable> objects) {
    ArrayList<Consumable> unshuffled_cards = new ArrayList<Consumable>(objects);
    Collections.shuffle(unshuffled_cards);
    deck_cards = new ArrayDeque<Consumable>(unshuffled_cards);
  }

  // Creates a randomly ordered deck from objects
  public RandomDeck(ResourceCollection objects) {
    ResourceCollection object_copy = objects.getCopy();
    ArrayList<Consumable> unshuffled_cards = new ArrayList<Consumable>();
    while (object_copy.distinctObjects() > 0) {
      Consumable r = object_copy.resourceIterator().next();
      int amt = object_copy.getAmount(r);
      object_copy.remove(r,amt);
      for (int i = 0; i < amt; i++) {
        unshuffled_cards.add(r);
      }
    }
    Collections.shuffle(unshuffled_cards);
    deck_cards = new ArrayDeque<Consumable>(unshuffled_cards);
  }

  // Removes the top value/card from the deck and returns it
  public Consumable draw() {
    return deck_cards.poll();
  }

  // Returns the number of items in the deck
  public int size() {
    return deck_cards.size();
  }
}
