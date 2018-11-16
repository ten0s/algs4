import java.util.Iterator;
import java.util.NoSuchElementException;

// Queue's capacity is NOT shrinked back!

public class DynArrayQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int head;
    private int tail;

    @SuppressWarnings("unchecked")
    public DynArrayQueue() {
        a = (Item[]) new Object[2];
    }

    public void enqueue(Item item) {
        // if tail is at the end
        if (tail == a.length) {
            // if there's some place in the front
            if (head > 0)
                relocate();
            else
                resize(2 * a.length);
        }
        a[tail++] = item;
    }

    public Item dequeue() {
        if (head == tail) {
            throw new NoSuchElementException();
        }

        Item item = a[head];
        a[head] = null;

        if (++head == tail)
            head = tail = 0;

        return item;
    }

    public boolean isEmpty() { return tail == head; }

    public int size() { return tail - head; }

    private void relocate() {
        for (int i = head; i < tail; i++) {
            a[i-head] = a[i];
            a[i] = null;
        }
        tail -= head;
        head = 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] b = (Item[]) new Object[capacity];
        for (int i = head; i < tail; i++) {
            b[i-head] = a[i];
        }
        tail -= head;
        head = 0;
        a = b;
    }

    // for testing only
    public int capacity() {
        return a.length;
    }

    public Iterator<Item> iterator() {
        return new ForwardArrayIterator();
    }

    private class ForwardArrayIterator implements Iterator<Item> {
        int c = head;

        public boolean hasNext() {
            return c >= head && c < tail;
        }

        public Item next() {
            if (c >= head && c < tail) {
                return a[c++];
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        DynArrayQueue<String> q = new DynArrayQueue<>();
        q.enqueue("1");
        q.enqueue("2");
        q.dequeue();

        q.enqueue("3");
        q.enqueue("4");

        q.dequeue();
        q.dequeue();
        q.dequeue();
    }
}
