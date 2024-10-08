public class Main {
  public static void main(String[] args) {
    ResourceCollection input = new ResourceCollection();
    Resource yel = new Resource("yellow");
    Resource bro = new Resource("brown");
    input.add(yel);
    input.add(yel, 2);
    input.add(bro, 5);

    ResourceCollection output = new ResourceCollection();
    Resource vp = new Resource("victory point");
    output.add(vp);

    Converter conv = new Converter(input, output);

    ResourceCollection resources = input.getCopy();
    resources.add(yel);
    resources.display();
    conv.instantExecution(resources);
    resources.display();
  }
}
