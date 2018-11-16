import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item extends Comparable> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private int size;

    /**
     * API
     */
    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // Ex 1.3.19
    public void removeLast() {
        // empty list
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        // single item list
        if (first.next == null) {
            first = null;
            size = 0;
            return;
        }

        // multiple item list
        // search for second to last item
        Node current = first;
        while (current.next.next != null) {
            current = current.next;
        }
        current.next = null;
        size--;
    }

    // Ex 1.3.20
    // zero-based index
    public void removeKth(int k) {
        if (k < 0) {
            throw new IllegalArgumentException();
        }

        if (k >= size()) {
            throw new NoSuchElementException();
        }

        // the first item?
        if (k == 0) {
            first = first.next;
            size--;
            return;
        }

        // locate k-1th node
        int i = 0;
        Node current = first;
        while (i < k-1) {
            i++;
            current = current.next;
        }
        current.next = current.next.next;
        size--;
    }

    // Ex 1.3.21
    public boolean find(Item key) {
        for (Node c = first; c != null; c = c.next) {
            if (c.item.equals(key)) {
                return true;
            }
        }
        return false;
    }

    // Ex 1.3.26
    public void remove(Item key) {
        if (first == null) {
            return;
        }

        // if key is in the head, remove it and
        // recursively continue with the tail
        if (first.item.equals(key)) {
            first = first.next;
            size--;
            remove(key);
            return;
        }

        // if key is in the next?
        Node current = first;
        while (current.next != null) {
            if (current.next.item.equals(key)) {
                // yes, remove it and continue
                current.next = current.next.next;
                size--;
            } else {
                // no, move forward
                current = current.next;
            }
        }
    }

    // Ex. 1.3.26
    @SuppressWarnings("unchecked")
    public Item max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item max = first.item;
        for (Node c = first.next; c != null; c = c.next) {
            if (max.compareTo(c.item) < 0) {
                max = c.item;
            }
        }
        return max;
    }

    // Ex 1.3.30
    public void reverse() {
        Node prev = null;
        Node curr = first;
        Node next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        first = prev;
    }

    @SuppressWarnings("unchecked")
    public Item[] toArray(Item[] a) {
        int i = 0;
        for (Item e : this) {
            a[i++] = e;
        }
        return a;
    }

    /**
     * Iterable
     */
    public Iterator<Item> iterator() {
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<Item> {
        Node current = first;

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
