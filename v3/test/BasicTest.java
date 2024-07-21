import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BasicTest {
  @Test
  void createResource() {
    Scorable blu = new Scorable("blue");
    assertEquals(blu.getName(),"blue");
  }
}
