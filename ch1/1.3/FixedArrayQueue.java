import java.util.Iterator;
import java.util.NoSuchElementException;

public class FixedArrayQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int head;
    private int tail;

    @SuppressWarnings("unchecked")
    public FixedArrayQueue(int capacity) {
        a = (Item[]) new Object[capacity];
    }

    public FixedArrayQueue() {
        this(2);
    }

    public void enqueue(Item item) {
        // if tail is at the end, but there's some place in the front
        if (tail == a.length && head > 0) defragmentate();
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

    private void defragmentate() {
        for (int i = head; i < tail; i++) {
            a[i-head] = a[i];
            a[i] = null;
        }
        tail -= head;
        head = 0;
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
        FixedArrayQueue<String> q = new FixedArrayQueue<>(3);
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
