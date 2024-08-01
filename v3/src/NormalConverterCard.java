

// Assumes 1 converter on front and back side run during economy, no cost to enter play, (up to) 2 upgrades run during trade
public class NormalConverterCard extends GenericConverterCard {

  public NormalConverterCard(String unupgradedName, Converter unupgradedConverter, String upgradedName, Converter upgradedConverter, ConverterCard upgrade1Cost, ConverterCard upgrade2Cost) {
    ResourceCollection card1Cost = new BasicResourceCollection();
    ResourceCollection card2Cost = new BasicResourceCollection();
    card1Cost.add(upgrade1Cost);
    card2Cost.add(upgrade2Cost);
    Converter upgrade1Transaction = new Converter(card1Cost, new ResourceCollection());
    Converter upgrade2Transaction = new Converter(card2Cost, new ResourceCollection());
    ArrayList<Converter> upgradeCosts = new ArrayList<Converter>();
    upgradeCosts.add(upgrade1Transaction);
    upgradeCosts.add(upgrade2Transaction);
    ArrayList<Converter> frontRun = new ArrayList<Converter>();
    ArrayList<Converter> backRun = new ArrayList<Converter>();
    frontRun.add(unupgradedConverter);
    backRun.add(upgradedConverter);
    super(unupgradedName, upgradedName, frontRun, backRun, new ArrayList<Converter>(), upgradeCosts, false);
  }

}
