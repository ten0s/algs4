import java.util.NoSuchElementException;
import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    Node first;
    Node last;
    int size;

    public Queue() {}

    public Queue(Queue<Item> q) {
        /*
        Book's hint:
        for (int i = 0; i < q.size(); i++) {
            Item item = q.dequeue();
            q.enqueue(item);
            this.enqueue(item);
        }
        */
        // more effective
        for (Item item : q) {
            this.enqueue(item);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        if (oldlast == null) {
            first = last;
        } else {
            oldlast.next = last;
        }
        size++;
    }

    public Item dequeue() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        }
        size--;
        return item;
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
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<Item> {
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
