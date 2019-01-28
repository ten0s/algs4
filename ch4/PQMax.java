import java.util.NoSuchElementException;

class PQMax<Key extends Comparable<Key>> extends PQ<Key> {
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

    public static void main(String[] args) throws Throwable {
        PQMax<Integer> pq = new PQMax<>();
        Assert.assertTrue(pq.isEmpty());
        Assert.assertEquals(0, pq.size());

        pq.insert(1);
        pq.insert(3);
        pq.insert(5);
        pq.insert(7);
        pq.insert(9);
        pq.insert(2);
        pq.insert(4);
        pq.insert(6);
        pq.insert(8);
        pq.insert(10);

        Assert.assertFalse(pq.isEmpty());
        Assert.assertEquals(10, pq.size());
        Assert.assertEquals(16, pq.capacity());

        Assert.assertEquals(10, pq.max());
        Assert.assertEquals(10, pq.delMax());
        for (int i = 0; i < 8; i++) pq.delMax();
        Assert.assertEquals(1, pq.max());
        Assert.assertEquals(1, pq.delMax());

        Assert.assertTrue(pq.isEmpty());
        Assert.assertEquals(0, pq.size());

        try {
            pq.max();
            Assert.assertTrue(false);
        } catch (NoSuchElementException e) {}

        try {
            pq.delMax();
            Assert.assertTrue(false);
        } catch (NoSuchElementException e) {}
    }
}
