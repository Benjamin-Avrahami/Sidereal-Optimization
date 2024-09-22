import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// Testing for compilation, mostly
class BasicTest {
  @Test
  void createResource() {
    Scorable blu = new Scorable("blue");
    assertEquals(blu.getName(),"blue");
  }
}
