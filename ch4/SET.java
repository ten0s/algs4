interface SET<Key> extends Iterable<Key> {
    void add(Key key);
    void delete(Key key);
    boolean contains(Key key);
    boolean isEmpty();
    int size();
}
