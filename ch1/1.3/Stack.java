import java.util.NoSuchElementException;
import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    Node first;
    int size;

    public Stack() {}

    public Stack(Stack<Item> s) {
        // one-pass copy, grow the list to the right
        Node last = null;
        for (Item item : s) {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            if (oldlast != null) {
                oldlast.next = last;
            }
            if (first == null) {
                first = last;
            }
            size++;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    public Item pop() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    public Item peek() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        return first.item;
    }

    @SuppressWarnings("unchecked")
    public Item[] toArray(Item[] a) {
        int i = 0;
        for (Item e : this) {
            a[i++] = e;
        }
        return a;
    }

    public Iterator<Item> iterator() {
        return new ReverseIterator();
    }

    private class ReverseIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
