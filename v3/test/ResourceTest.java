import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ResourceTest {
  static Resource a;
  static Resource a2;
  static Resource b;

  @BeforeAll
  static void constructResources() {
    a = new Resource("abc");
    a2 = new Resource("abc");
    b = new Resource("def");
  }



  @Test
  void checkNameTest() {
    assertEquals(a.getName(),"abc");
    assertNotEquals(a.getName(),"bcd");
    assertNotEquals(b.getName(),"bcd");
    assertEquals(b.getName(),"def");
  }

  @Test
  void equalityHashcodeTest() {
    assertTrue(a.equals(a2));
    assertEquals(a.hashCode(),a2.hashCode());
    assertFalse(a.equals(b));
  }
}
