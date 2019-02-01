import edu.princeton.cs.algs4.*;

public class MaxPQ<Key extends Comparable<Key>> extends PQ<Key> {
    public Key delMax() { return delete(); }
    public Key max() { return top(); }

    boolean less(Key v, Key w) {
        return v.compareTo(w) < 0;
    }

    public static void main(String[] args) {
        MaxPQ<Integer> pq = new MaxPQ<>();
        pq.insert(1);
        pq.insert(3);
        pq.insert(5);
        pq.insert(2);
        pq.insert(4);
        pq.insert(6);
        while (!pq.isEmpty()) {
            System.out.println(pq.delMax());
        }
    }
}
