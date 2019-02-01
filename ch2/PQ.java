import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

public abstract class PQ<Key extends Comparable<Key>> {
    private final int MIN_CAPACITY = 2;
    private Key[] pq;
    private int n;

    @SuppressWarnings("unchecked")
    protected PQ() {
        pq = (Key[]) new Comparable[MIN_CAPACITY+1];
    }

    protected void insert(Key v) {
        if (++n == pq.length) resize(2*pq.length+1);
        pq[n] = v;
        swim(n);
    }

    protected Key delete() {
        ensureNotEmpty();
        Key v = pq[1];
        pq[1] = pq[n];
        pq[n] = null;
        if (n <= pq.length/4) resize(Math.max(MIN_CAPACITY, pq.length/2)+1);
        n--;
        sink(1);
        return v;
    }

    protected Key top() {
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
        while (k > 1 && less(k/2, k)) {
            swap(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int i = 2*k;
            if (i < n && less(i, i+1)) ++i;
            if (!less(k, i)) break;
            swap(k, i);
            k = i;
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

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }
}
