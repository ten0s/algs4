import java.util.NoSuchElementException;

abstract class PQ<Key extends Comparable<Key>> {
    protected final int MIN_CAPACITY = 2;
    private Key[] pq;
    private int n;

    @SuppressWarnings("unchecked")
    protected PQ() {
        pq = (Key[]) new Comparable[MIN_CAPACITY+1];
    }

    protected void push(Key v) {
        if (++n > capacity()) resize(2*capacity()+1);
        pq[n] = v;
        swim(n);
    }

    protected Key pop() {
        ensureNotEmpty();
        Key v = pq[1];
        pq[1] = pq[n];
        pq[n] = null;
        if (--n <= capacity()/4) resize(Math.max(MIN_CAPACITY, capacity()/2)+1);
        sink(1);
        return v;
    }

    protected Key peek() {
        ensureNotEmpty();
        return pq[1];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    private void swim(int k) {
        // while i'm not at root and parent is less than me
        // swap me with parent and move up
        while (k > 1 && less(k/2, k)) {
            swap(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k) {
        // while left child is in the tree
        while (2*k <= n) {
            int j = 2*k;
            // if right child is in the tree, choose max child
            if (j < n && less(j, j+1)) ++j;
            // stop, if i'm not less than child
            if (!less(k, j)) break;
            // swap me with child and move down
            swap(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return less(pq[i], pq[j]);
    }

    abstract boolean less(Key v, Key w);

    private void swap(int i, int j) {
        Key tmp = pq[i]; pq[i] = pq[j]; pq[j] = tmp;
    }

    @SuppressWarnings("unchecked")
        private void resize(int capacity) {
        Key[] pq2 = (Key[]) new Comparable[capacity];
        int m = Math.min(n, pq.length-1);
        for (int i = 1; i <= m; i++) {
            pq2[i] = pq[i];
        }
        pq = pq2;
    }

    protected int capacity() {
        return pq.length - 1;
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }
}
