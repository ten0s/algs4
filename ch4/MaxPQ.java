class MaxPQ<Key extends Comparable<Key>> extends PQ<Key> {
    public void insert(Key v) {
        push(v);
    }

    public Key delMax() {
        return pop();
    }

    public Key max() {
        return peek();
    }

    boolean less(Key v, Key w) {
        return v.compareTo(w) < 0;
    }
}
