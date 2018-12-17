import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

public class PriorityQueue {
    static abstract class PQ<Key extends Comparable<Key>> {
        private final int MIN_CAPACITY = 2;
        private Key[] pq;
        private int n;

        @SuppressWarnings("unchecked")
        private PQ() {
            pq = (Key[]) new Comparable[MIN_CAPACITY+1];
        }

        protected void push(Key v) {
            if (++n == pq.length) resize(2*pq.length+1);
            pq[n] = v;
            swim(n);
        }

        protected Key pop() {
            checkEmpty();
            Key v = pq[1];
            pq[1] = pq[n];
            pq[n] = null;
            if (n <= pq.length/4) resize(Math.max(MIN_CAPACITY, pq.length/2)+1);
            n--;
            sink(1);
            return v;
        }

        protected Key peek() {
            checkEmpty();
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
            for (int i = 1; i < n; i++) {
                pq2[i] = pq[i];
            }
            pq = pq2;
        }

        private void checkEmpty() {
            if (isEmpty()) {
                throw new NoSuchElementException();
            }
        }
    }

    private static class PQMax<Key extends Comparable<Key>> extends PQ<Key> {
        public void insert(Key v) { push(v); }
        public Key delMax() { return pop(); }
        public Key max() { return peek(); }

        boolean less(Key v, Key w) {
            return v.compareTo(w) < 0;
        }
    }

    private static class PQMin<Key extends Comparable<Key>> extends PQ<Key> {
        public void insert(Key v) { push(v); }
        public Key delMin() { return pop(); }
        public Key min() { return peek(); }

        public boolean less(Key v, Key w) {
            return v.compareTo(w) > 0;
        }
    }

    public static void main(String[] args) {
        PQMax<Integer> pq = new PQMax<>();
        pq.insert(1);
        pq.insert(3);
        pq.insert(5);
        pq.insert(2);
        pq.insert(4);
        pq.insert(6);
        while (!pq.isEmpty()) {
            System.out.println(pq.delMax());
        }

        System.out.println();

        PQMin<Integer> pq2 = new PQMin<>();
        pq2.insert(1);
        pq2.insert(3);
        pq2.insert(5);
        pq2.insert(2);
        pq2.insert(4);
        pq2.insert(6);
        while (!pq2.isEmpty()) {
            System.out.println(pq2.delMin());
        }
    }
}
