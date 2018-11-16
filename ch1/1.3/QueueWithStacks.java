import java.util.NoSuchElementException;
import java.util.Iterator;

public class QueueWithStacks<Item> implements Iterable<Item> {
    Stack<Item> dequeue = new Stack<>();
    Stack<Item> enqueue = new Stack<>();

    public boolean isEmpty() {
        return dequeue.isEmpty() && enqueue.isEmpty();
    }

    public int size() {
        return dequeue.size() + enqueue.size();
    }

    public void enqueue(Item item) {
        enqueue.push(item);
    }

    public Item dequeue() {
        if (!dequeue.isEmpty()) {
            return dequeue.pop();
        } else {
            while (!enqueue.isEmpty()) {
                dequeue.push(enqueue.pop());
            }
            return dequeue.pop();
        }
    }

    public Iterator<Item> iterator() {
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<Item> {
        private int idx;
        private Item[] arr;

        @SuppressWarnings("unchecked")
        private ForwardIterator() {
            arr = (Item[]) new Object[size()];
            // copy dequeue items forward
            int i = 0;
            for (Item item : dequeue) {
                arr[i++] = item;
            }
            // copy enqueue items backward
            i = arr.length-1;
            for (Item item : enqueue) {
                arr[i--] = item;
            }
        }

        public boolean hasNext() {
            return idx < arr.length;
        }

        public Item next() {
            return arr[idx++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
