import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

// $ make test CLASS=Bag

public class BagTest {
    @Test
    public void defaultConstructor() {
        Bag<String> b = new Bag<>();
        assertTrue(b.isEmpty());
        assertEquals(0, b.size());
    }

    @Test
    public void checkNotEmpty() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        assertFalse(b.isEmpty());
        assertEquals(3, b.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastEmpty() {
        Bag<String> b = new Bag<>();
        b.removeLast();
    }

    @Test
    public void removeLastOne() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.removeLast();
        assertArrayEquals(new String[] {},
                          b.toArray(new String[b.size()]));

    }

    @Test
    public void removeLastNotEmpty() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        b.removeLast();
        assertArrayEquals(new String[] {"3", "2"},
                          b.toArray(new String[b.size()]));
    }

    @Test(expected = NoSuchElementException.class)
    public void removeKthNotExisting() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.removeKth(1);
    }

    @Test
    public void removeKthFirst() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        b.removeKth(0);
        assertArrayEquals(new String[] {"2", "1"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void removeKthMiddle() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        b.removeKth(1);
        assertArrayEquals(new String[] {"3", "1"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void removeKthLast() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        b.removeKth(2);
        assertArrayEquals(new String[] {"3", "2"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void removeEmpty() {
        Bag<String> b = new Bag<>();
        b.remove("1");
        assertArrayEquals(new String[] {},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void removeNotExisting() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        b.remove("4");
        assertArrayEquals(new String[] {"3", "2", "1"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void removeFirst() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        b.remove("3");
        assertArrayEquals(new String[] {"2", "1"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void removeMiddle() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        b.remove("2");
        assertArrayEquals(new String[] {"3", "1"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void removeLast() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        b.remove("1");
        assertArrayEquals(new String[] {"3", "2"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void removeSome() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("1");
        b.add("3");
        b.add("1");
        b.remove("1");
        assertArrayEquals(new String[] {"3", "2"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void removeAll() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("1");
        b.add("1");
        b.remove("1");
        assertArrayEquals(new String[] {},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void findEmpty() {
        Bag<String> b = new Bag<>();
        assertFalse(b.find("1"));
    }

    @Test
    public void findNotExisting() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        assertFalse(b.find("4"));
    }

    @Test
    public void findFirst() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        assertTrue(b.find("1"));
    }

    @Test
    public void findMiddle() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        assertTrue(b.find("2"));
    }

    @Test
    public void findLast() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        assertTrue(b.find("3"));
    }

    @Test(expected = NoSuchElementException.class)
    public void maxEmpty() {
        Bag<String> b = new Bag<>();
        b.max();
    }

    @Test
    public void maxFirst() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        assertEquals("3", b.max());
    }

    @Test
    public void maxMiddle() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("3");
        b.add("2");
        assertEquals("3", b.max());
    }

    @Test
    public void maxIntMiddle() {
        Bag<Integer> b = new Bag<>();
        b.add(1);
        b.add(3);
        b.add(2);
        assertEquals(3, (int)b.max());
    }

    @Test
    public void maxLast() {
        Bag<String> b = new Bag<>();
        b.add("3");
        b.add("2");
        b.add("1");
        assertEquals("3", b.max());
    }

    @Test
    public void maxSome() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("3");
        b.add("2");
        b.add("3");
        assertEquals("3", b.max());
    }

    @Test
    public void maxAll() {
        Bag<String> b = new Bag<>();
        b.add("3");
        b.add("3");
        b.add("3");
        assertEquals("3", b.max());
    }

    @Test
    public void reverseEmpty() {
        Bag<String> b = new Bag<>();
        b.reverse();
        assertArrayEquals(new String[] {},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void reverseOne() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.reverse();
        assertArrayEquals(new String[] {"1"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void reverseMany() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        b.reverse();
        assertArrayEquals(new String[] {"1", "2", "3"},
                          b.toArray(new String[b.size()]));
    }

    @Test
    public void iteratorEmpty() {
        Bag<String> b = new Bag<>();
        for (String j : b) {}
    }

    @Test
    public void iteratorNotEmpty() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        String[] r = new String[b.size()];
        int i = 0;
        for (String j : b) {
            r[i++] = j;
        }
        assertArrayEquals(new String[] {"3", "2", "1"}, r);
    }

    @Test
    public void toArrayEmpty() {
        Bag<String> b = new Bag<>();
        String[] a = b.toArray(new String[b.size()]);
        assertArrayEquals(new String[] {}, a);
    }

    @Test
    public void toArrayNotEmpty() {
        Bag<String> b = new Bag<>();
        b.add("1");
        b.add("2");
        b.add("3");
        String[] a = b.toArray(new String[b.size()]);
        assertArrayEquals(new String[] {"3", "2", "1"}, a);
    }
}
