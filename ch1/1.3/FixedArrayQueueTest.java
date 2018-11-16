import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;
import java.util.Arrays;

// $ make test CLASS=FixedArrayQueue

public class FixedArrayQueueTest {
    @Test
    public void defaultConstructor() {
        FixedArrayQueue<String> q = new FixedArrayQueue<>();
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
    }

    @Test
    public void constructorWithArg() {
        FixedArrayQueue<String> q = new FixedArrayQueue<>(5);
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
    }

    @Test
    public void checkNonEmpty() {
        FixedArrayQueue<String> q = new FixedArrayQueue<>(3);
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        assertFalse(q.isEmpty());
        assertEquals(3, q.size());
    }

    @Test
    public void enqueuesAndDequeues() {
        FixedArrayQueue<String> q = new FixedArrayQueue<>(3);
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        assertEquals("1", q.dequeue());
        assertEquals("2", q.dequeue());
        assertEquals("3", q.dequeue());
    }

    @Test
    public void enqueuesAndDequeues2() {
        FixedArrayQueue<String> q = new FixedArrayQueue<>(3);
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
        FixedArrayQueue<String> q = new FixedArrayQueue<>(3);
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
        FixedArrayQueue<String> q = new FixedArrayQueue<>();
        q.dequeue();
    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void enqueueExcessesCapacity() {
        FixedArrayQueue<String> q = new FixedArrayQueue<>(1);
        q.enqueue("1");
        q.enqueue("2");
    }

    @Test
    public void forwardIterator() {
        FixedArrayQueue<String> q = new FixedArrayQueue<>(5);
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
