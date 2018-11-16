import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

// $ make test CLASS=Queue

public class QueueTest {
    @Test
    public void defaultConstructor() {
        Queue<String> q = new Queue<>();
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
    }

    @Test
    public void copyContructor() {
        Queue<String> q1 = new Queue<>();
        q1.enqueue("1");
        q1.enqueue("2");
        q1.enqueue("3");
        // call copy constructor
        Queue<String> q2 = new Queue<>(q1);
        // check both queues have the same items
        String[] a1 = q1.toArray(new String[q1.size()]);
        String[] a2 = q2.toArray(new String[q2.size()]);
        assertArrayEquals(a1, a2);
        // check both queues are indeed independent
        q1.enqueue("4");
        assertEquals(4, q1.size());
        q2.dequeue();
        assertEquals(2, q2.size());
    }

    @Test
    public void checkNotEmpty() {
        Queue<String> q = new Queue<>();
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        assertFalse(q.isEmpty());
        assertEquals(3, q.size());
    }

    @Test
    public void enqueuesAndDequeues() {
        Queue<String> q = new Queue<>();
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        assertEquals("1", q.dequeue());
        assertEquals("2", q.dequeue());
        assertEquals("3", q.dequeue());
    }

    @Test
    public void enqueuesAndDequeues2() {
        Queue<String> q = new Queue<>();
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
        Queue<String> q = new Queue<>();
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
        Queue<String> q = new Queue<>();
        q.dequeue();

    }

    @Test
    public void concat() {
        Queue<String> q1 = new Queue<>();
        q1.enqueue("1");
        q1.enqueue("2");
        q1.enqueue("3");
        Queue<String> q2 = new Queue<>();
        q2.enqueue("4");
        q2.enqueue("5");
        q2.enqueue("6");
        q1.concat(q2);
        String[] a1 = q1.toArray(new String[q1.size()]);
        assertArrayEquals(new String[] {"1", "2", "3", "4", "5", "6"}, a1);
    }

    @Test
    public void iteratorEmpty() {
        Queue<String> q = new Queue<>();
        for (String j : q) {}
    }

    @Test
    public void iteratorNotEmpty() {
        Queue<String> q = new Queue<>();
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

    @Test
    public void toArrayEmpty() {
        Queue<String> q = new Queue<>();
        String[] a = q.toArray(new String[q.size()]);
        assertArrayEquals(new String[] {}, a);
    }

    @Test
    public void toArrayNotEmpty() {
        Queue<String> q = new Queue<>();
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        String[] a = q.toArray(new String[q.size()]);
        assertArrayEquals(new String[] {"1", "2", "3"}, a);
    }
}
