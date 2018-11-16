import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynArrayStack<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    @SuppressWarnings("unchecked")
    public DynArrayStack() {
        a = (Item[]) new Object[2];
    }

    public void push(Item item) {
        if (n == a.length) resize(2 * a.length);
        a[n++] = item;
    }

    public Item pop() {
        if (n == 0) {
            throw new NoSuchElementException();
        }

        if (n <= a.length / 4) resize(a.length / 2);
        Item item = a[--n];
        a[n] = null;
        return item;
    }

    public Item peek() {
        if (n == 0) {
            throw new NoSuchElementException();
        }

        return a[n-1];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] b = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            b[i] = a[i];
        a = b;
    }

    // test method
    public int capacity() {
        return a.length;
    }

    // from Iterable
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
        DynArrayStack<String> s = new DynArrayStack<>();
        s.push("1");
        s.push("2");
        s.push("3");
        s.pop();
        s.pop();
        s.pop();
    }
}
