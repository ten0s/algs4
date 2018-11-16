import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;
import java.util.Arrays;

// $ make test CLASS=FixedArrayStack

public class FixedArrayStackTest {
    @Test
    public void defaultConstructor() {
        FixedArrayStack<String> s = new FixedArrayStack<>();
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void constructorWithArg() {
        FixedArrayStack<String> s = new FixedArrayStack<>(5);
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void checkNonEmpty() {
        FixedArrayStack<String> s = new FixedArrayStack<>(5);
        s.push("1");
        s.push("2");
        s.push("3");
        assertFalse(s.isEmpty());
        assertEquals(3, s.size());
    }

    @Test
    public void pushesAndPops() {
        FixedArrayStack<String> s = new FixedArrayStack<>(5);
        s.push("1");
        s.push("2");
        s.push("3");
        assertEquals("3", s.pop());
        assertEquals("2", s.pop());
        assertEquals("1", s.pop());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void popFromEmpty() {
        FixedArrayStack<String> s = new FixedArrayStack<>();
        s.pop();
    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void pushExcessesCapacity() {
        FixedArrayStack<String> s = new FixedArrayStack<>(1);
        s.push("1");
        s.push("2");
    }

    @Test
    public void iteratorEmpty() {
        FixedArrayStack<String> s = new FixedArrayStack<>();
        for (String j : s) {}
    }

    @Test
    public void iteratorNotEmpty() {
        FixedArrayStack<String> s = new FixedArrayStack<>(5);
        s.push("1");
        s.push("2");
        s.push("3");
        String[] r = new String[s.size()];
        int i = 0;
        for (String j : s) {
            r[i++] = j;
        }
        assertArrayEquals(new String[] {"3", "2", "1"}, r);
    }
}
