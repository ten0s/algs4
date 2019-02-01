import edu.princeton.cs.algs4.*;

public class MinPQ<Key extends Comparable<Key>> extends PQ<Key> {
    public Key delMin() { return delete(); }
    public Key min() { return top(); }

    boolean less(Key v, Key w) {
        return v.compareTo(w) > 0;
    }

    public static void main(String[] args) {
        MinPQ<Integer> pq = new MinPQ<>();
        pq.insert(1);
        pq.insert(3);
        pq.insert(5);
        pq.insert(2);
        pq.insert(4);
        pq.insert(6);
        while (!pq.isEmpty()) {
            System.out.println(pq.delMin());
        }
    }
}
