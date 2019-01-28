import java.util.NoSuchElementException;

class PQMin<Key extends Comparable<Key>> extends PQ<Key> {
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

    public static void main(String[] args) throws Throwable {
        PQMin<Integer> pq = new PQMin<>();
        Assert.assertTrue(pq.isEmpty());
        Assert.assertEquals(0, pq.size());

        pq.insert(10);
        pq.insert(8);
        pq.insert(6);
        pq.insert(4);
        pq.insert(2);
        pq.insert(9);
        pq.insert(7);
        pq.insert(5);
        pq.insert(3);
        pq.insert(1);

        Assert.assertFalse(pq.isEmpty());
        Assert.assertEquals(10, pq.size());
        Assert.assertEquals(16, pq.capacity());

        Assert.assertEquals(1, pq.min());
        Assert.assertEquals(1, pq.delMin());
        for (int i = 0; i < 8; i++) pq.delMin();
        Assert.assertEquals(10, pq.min());
        Assert.assertEquals(10, pq.delMin());

        Assert.assertTrue(pq.isEmpty());
        Assert.assertEquals(0, pq.size());

        try {
            pq.min();
            Assert.assertTrue(false);
        } catch (NoSuchElementException e) {}

        try {
            pq.delMin();
            Assert.assertTrue(false);
        } catch (NoSuchElementException e) {}
    }
}
