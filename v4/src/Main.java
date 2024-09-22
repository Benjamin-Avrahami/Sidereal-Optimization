public class Main {
  public static void main(String[] args) {
    ResourceCollection input = new BasicResourceCollection();
    Scorable yel = new Scorable("yellow");
    Scorable bro = new Scorable("brown");
    input.add(yel);
    input.add(yel, 2);
    input.add(bro, 5);

    ResourceCollection output = new BasicResourceCollection();
    Scorable vp = new Scorable("victory point");
    output.add(vp);

    Converter conv = new Converter(input, output);

    ResourceCollection resources = new BasicResourceCollection(input);
    resources.add(yel);
    resources.display();
    conv.instantExecution(resources);
    resources.display();
  }
}
