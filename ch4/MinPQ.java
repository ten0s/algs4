class MinPQ<Key extends Comparable<Key>> extends PQ<Key> {
    public void insert(Key v) {
        push(v);
    }

    public Key delMin() {
        return pop();
    }

    public Key min() {
        return peek();
    }

    boolean less(Key v, Key w) {
        return w.compareTo(v) < 0;
    }
}
