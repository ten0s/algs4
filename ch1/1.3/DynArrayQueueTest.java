import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;
import java.util.Arrays;

// $ make test CLASS=DynArrayQueue

public class DynArrayQueueTest {
    @Test
    public void defaultConstructor() {
        DynArrayQueue<String> q = new DynArrayQueue<>();
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
    }

    @Test
    public void capacity() {
        DynArrayQueue<String> q = new DynArrayQueue<>();
        assertEquals(2, q.capacity());
        q.enqueue("1");
        q.enqueue("2");
        assertEquals(2, q.capacity());
        q.enqueue("3");
        assertEquals(4, q.capacity());
        q.enqueue("4");
        q.enqueue("5");
        assertEquals(8, q.capacity());
        q.enqueue("6");
        q.enqueue("7");
        q.enqueue("8");
        q.enqueue("9");
        assertEquals(16, q.capacity());
    }

    @Test
    public void checkNonEmpty() {
        DynArrayQueue<String> q = new DynArrayQueue<>();
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        assertFalse(q.isEmpty());
        assertEquals(3, q.size());
    }

    @Test
    public void enqueuesAndDequeues() {
        DynArrayQueue<String> q = new DynArrayQueue<>();
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        assertEquals("1", q.dequeue());
        assertEquals("2", q.dequeue());
        assertEquals("3", q.dequeue());
    }

    @Test
    public void enqueuesAndDequeues2() {
        DynArrayQueue<String> q = new DynArrayQueue<>();
        q.enqueue("1");
        q.dequeue();

        q.enqueue("2");
        q.enqueue("3");
        q.enqueue("4");
        assertEquals("2", q.dequeue());
        assertEquals("3", q.dequeue());
        assertEquals("4", q.dequeue());
    }

    @Test
    public void enqueuesAndDequeues3() {
        DynArrayQueue<String> q = new DynArrayQueue<>();
        q.enqueue("1");
        q.enqueue("2");
        q.dequeue();

        q.enqueue("3");
        q.enqueue("4");
        assertEquals("2", q.dequeue());
        assertEquals("3", q.dequeue());
        assertEquals("4", q.dequeue());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void dequeueFromEmpty() {
        DynArrayQueue<String> q = new DynArrayQueue<>();
        q.dequeue();
    }

    @Test
    public void forwardIterator() {
        DynArrayQueue<String> q = new DynArrayQueue<>();
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        String[] r = new String[q.size()];
        int i = 0;
        for (String j : q) {
            r[i++] = j;
        }
        assertArrayEquals(new String[] {"1", "2", "3"}, r);
    }
}
