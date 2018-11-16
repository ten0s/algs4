import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;
import java.util.Arrays;

// $ make test CLASS=DynArrayStack

public class DynArrayStackTest {
    @Test
    public void defaultConstructor() {
        DynArrayStack<String> s = new DynArrayStack<>();
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void capacity() {
        DynArrayStack<String> s = new DynArrayStack<>();
        assertEquals(2, s.capacity());
        s.push("1");
        s.push("2");
        assertEquals(2, s.capacity());
        s.push("3");
        assertEquals(4, s.capacity());
        s.push("4");
        s.push("5");
        assertEquals(8, s.capacity());
        s.push("6");
        s.push("7");
        s.push("8");
        s.push("9");
        assertEquals(16, s.capacity());
        s.pop(); // 9
        s.pop(); // 8
        s.pop(); // 7
        s.pop(); // 6
        s.pop(); // 5
        s.pop(); // 4
        assertEquals(8, s.capacity());
        s.pop(); // 3
        s.pop(); // 2
        assertEquals(4, s.capacity());
        s.pop(); // 1
        assertEquals(2, s.capacity());
    }

    @Test
    public void checkNonEmpty() {
        DynArrayStack<String> s = new DynArrayStack<>();
        s.push("1");
        s.push("2");
        s.push("3");
        assertFalse(s.isEmpty());
        assertEquals(3, s.size());
    }

    @Test
    public void pushesAndPops() {
        DynArrayStack<String> s = new DynArrayStack<>();
        s.push("1");
        s.push("2");
        s.push("3");
        assertEquals("3", s.pop());
        assertEquals("2", s.pop());
        assertEquals("1", s.pop());
    }

    @Test
    public void pushesAndPopsAndPeek() {
        DynArrayStack<String> s = new DynArrayStack<>();
        s.push("1");
        assertEquals("1", s.peek());
        assertEquals("1", s.peek());
        s.push("2");
        assertEquals("2", s.peek());
        assertEquals("2", s.pop());
        assertEquals("1", s.peek());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void popFromEmpty() {
        DynArrayStack<String> s = new DynArrayStack<>();
        s.pop();
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void peekFromEmpty() {
        DynArrayStack<String> s = new DynArrayStack<>();
        s.peek();
    }

    @Test
    public void iteratorEmpty() {
        DynArrayStack<String> s = new DynArrayStack<>();
        for (String j : s) {}
    }

    @Test
    public void iteratorNotEmpty() {
        DynArrayStack<String> s = new DynArrayStack<>();
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
