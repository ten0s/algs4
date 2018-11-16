import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;
import java.util.Arrays;

// $ make test CLASS=Stack

public class StackTest {
    @Test
    public void defaultConstructor() {
        Stack<String> s = new Stack<>();
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void copyContructor() {
        Stack<String> s1 = new Stack<>();
        s1.push("1");
        s1.push("2");
        s1.push("3");
        Stack<String> s2 = new Stack<>(s1);
        // check both stacks have the same items
        String[] a1 = s1.toArray(new String[s1.size()]);
        String[] a2 = s2.toArray(new String[s2.size()]);
        assertArrayEquals(a1, a2);
        // check both stacks are indeed independent
        s1.push("4");
        assertEquals(4, s1.size());
        s2.pop();
        assertEquals(2, s2.size());
    }

    @Test
    public void checkNonEmpty() {
        Stack<String> s = new Stack<>();
        s.push("1");
        s.push("2");
        s.push("3");
        assertFalse(s.isEmpty());
        assertEquals(3, s.size());
    }

    @Test
    public void pushesAndPops() {
        Stack<String> s = new Stack<>();
        s.push("1");
        s.push("2");
        s.push("3");
        assertEquals("3", s.pop());
        assertEquals("2", s.pop());
        assertEquals("1", s.pop());
    }

    @Test
    public void pushesAndPopsAndPeek() {
        Stack<String> s = new Stack<>();
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
        Stack<String> s = new Stack<>();
        s.pop();
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void peekFromEmpty() {
        Stack<String> s = new Stack<>();
        s.peek();
    }

    @Test
    public void concatToEmpty() {
        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();
        s2.push("4");
        s2.push("5");
        s2.push("6");
        s1.concat(s2);
        String[] a1 = s1.toArray(new String[s1.size()]);
        assertArrayEquals(new String[] {"6", "5", "4"}, a1);
    }

    @Test
    public void concatToNotEmpty() {
        Stack<String> s1 = new Stack<>();
        s1.push("1");
        s1.push("2");
        s1.push("3");
        Stack<String> s2 = new Stack<>();
        s2.push("4");
        s2.push("5");
        s2.push("6");
        s1.concat(s2);
        String[] a1 = s1.toArray(new String[s1.size()]);
        assertArrayEquals(new String[] {"3", "2", "1", "6", "5", "4"}, a1);
    }

    @Test
    public void iteratorEmpty() {
        Stack<String> s = new Stack<>();
        for (String j : s) {}
    }

    @Test
    public void iteratorNotEmpty() {
        Stack<String> s = new Stack<>();
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

    @Test
    public void toArrayEmpty() {
        Stack<String> s = new Stack<>();
        String[] a = s.toArray(new String[s.size()]);
        assertArrayEquals(new String[] {}, a);
    }

    @Test
    public void toArrayNotEmpty() {
        Stack<String> s = new Stack<>();
        s.push("1");
        s.push("2");
        s.push("3");
        String[] a = s.toArray(new String[s.size()]);
        assertArrayEquals(new String[] {"3", "2", "1"}, a);
    }
}
