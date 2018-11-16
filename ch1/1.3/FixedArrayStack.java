import java.util.Iterator;
import java.util.NoSuchElementException;

public class FixedArrayStack<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    @SuppressWarnings("unchecked")
    public FixedArrayStack(int capacity) {
        a = (Item[]) new Object[capacity];
    }

    public FixedArrayStack() {
        this(2);
    }

    public void push(Item item) {
        a[n++] = item;
    }

    public Item pop() {
        if (n == 0) {
            throw new NoSuchElementException();
        }

        Item item = a[--n];
        a[n] = null;
        return item;
    }

    public boolean isEmpty() { return n == 0; }

    public int size() { return n; }

    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        int c = n;

        public boolean hasNext() {
            return c > 0;
        }

        public Item next() {
            if (--c >= 0) {
                return a[c];
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
    }
}
