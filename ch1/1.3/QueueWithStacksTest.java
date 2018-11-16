import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

// $ make test CLASS=QueueWithStacks

public class QueueWithStacksTest {
    @Test
    public void defaultConstructor() {
        QueueWithStacks<String> q = new QueueWithStacks<>();
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
    }

    @Test
    public void checkNotEmpty() {
        QueueWithStacks<String> q = new QueueWithStacks<>();
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        assertFalse(q.isEmpty());
        assertEquals(3, q.size());
    }

    @Test
    public void enqueuesAndDequeues() {
        QueueWithStacks<String> q = new QueueWithStacks<>();
        q.enqueue("1");
        q.enqueue("2");
        q.enqueue("3");
        assertEquals("1", q.dequeue());
        assertEquals("2", q.dequeue());
        assertEquals("3", q.dequeue());
    }

    @Test
    public void enqueuesAndDequeues2() {
        QueueWithStacks<String> q = new QueueWithStacks<>();
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
        QueueWithStacks<String> q = new QueueWithStacks<>();
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
        QueueWithStacks<String> q = new QueueWithStacks<>();
        q.dequeue();

    }

    @Test
    public void iteratorEmpty() {
        QueueWithStacks<String> q = new QueueWithStacks<>();
        for (String j : q) {}
    }

    @Test
    public void iteratorNotEmpty() {
        QueueWithStacks<String> q = new QueueWithStacks<>();
        // | | | |
        // | | | |
        // | | | |
        // --- ---
        q.enqueue("0");
        q.enqueue("1");
        q.enqueue("2");
        // | | |2|
        // | | |1|
        // | | |0|
        // --- ---
        q.dequeue();
        // | | | |
        // |1| | |
        // |2| | |
        // --- ---
        q.enqueue("3");
        q.enqueue("4");
        q.enqueue("5");
        // | | |5|
        // |1| |4|
        // |2| |3|
        // --- ---
        String[] r = new String[q.size()];
        int i = 0;
        for (String j : q) {
            r[i++] = j;
        }
        assertArrayEquals(new String[] {"1", "2", "3", "4", "5"}, r);
    }
}
