

public abstract class ConverterCard implements Consumable {
  // Runs the converter on the card (defaults to first legally available)
  // initialResources are the resources available this turn, futureResources are the resources available next turn (created during economy)
  // Consumes any necessary resources if successfully run, then inserts created resources to the appropriate resource collection
  // Returns true if converter was successfully run, false if not
  public abstract boolean run(ResourceCollection initialResources, ResourceCollection futureResources);

  // Attempts to upgrade the converter (using first option legally available if there are multiple)
  // initialResources are the resources available this turn, futureResources are the resources available next turn
  // Consumes any necessary resources if successfully upgraded, then inserts created resources (if existing) to the appropriate resource collection
  // Returns true if converter was successfully upgraded, false if not
  public abstract boolean upgrade(ResourceCollection initialResources, ResourceCollection futureResources);

  public abstract boolean isInPlay();
  public abstract boolean isUpgraded();
}
