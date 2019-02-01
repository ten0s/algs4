import edu.princeton.cs.algs4.*;

public class MinPQ<Key extends Comparable<Key>> extends PQ<Key> {
    public Key delMin() { return delete(); }
    public Key min() { return top(); }

    boolean less(Key v, Key w) {
        return v.compareTo(w) > 0;
    }

    public static void main(String[] args) {
        MinPQ<Integer> pq2 = new MinPQ<>();
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
