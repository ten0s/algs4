import java.lang.reflect.Array;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

// time make run CLASS=HashMap ARGS=10 < ../data/tale.txt
// monseigneur 101
// size: 2257
// buckets: 557
// min bucket size: 0
// max bucket size: 12
// avg bucket size: 4
// empty buckets: 10
//     x
//    xx
//    xx
//    xxx
//   xxxx
//   xxxxx
//   xxxxx
//   xxxxx
//  xxxxxx
//  xxxxxxx
//  xxxxxxx
// xxxxxxxxx
// xxxxxxxxxxxxx
//
// real	0m1.548s
// user	0m3.324s
// sys	0m0.176s

public class HashMap<Key , Value> implements ST<Key, Value> {
    private final static int MIN_CAPACITY = 31;
    private class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    private Node[] a;
    private int m;
    private int n;

    public HashMap() {
        this(MIN_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    private HashMap(int m) {
        this.m = m;
        this.a = (Node[]) Array.newInstance(Node.class, m);
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private void resize(int capacity) {
        HashMap<Key, Value> t = new HashMap<>(capacity);
        for (int i = 0; i < m; i++) {
            for (Node x = a[i]; x != null; x = x.next) {
                t.put(x.key, x.val);
            }
        }
        this.a = t.a;
        this.m = t.m;
    }

    public Value get(Key key) {
        int i = hash(key);
        for (Node x = a[i]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.val;
            }
        }
        return null;
    }

    public void put(Key key, Value val) {
        int i = hash(key);
        for (Node x = a[i]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        a[i] = new Node(key, val, a[i]);
        n++;
        if (n >= 8*m) resize(Prime.nextPrime(2*m));
    }

    public void delete(Key key) {
        int i = hash(key);
        Node x = a[i];
        if (x == null) return;
        if (key.equals(x.key)) {
            a[i] = x.next;
            n--;
        } else {
            for (; x.next != null; x = x.next) {
                if (key.equals(x.next.key)) {
                    x.next = x.next.next;
                    n--;
                    break;
                }
            }
        }
        if (n <= 2*m) resize(Math.max(MIN_CAPACITY, Prime.nextPrime(m/2)));
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < m; i++) {
            for (Node x = a[i]; x != null; x = x.next) {
                queue.enqueue(x.key);
            }
        }
        return queue;
    }

    public static void main(String[] args) {
        int minLen = Integer.parseInt(args[0]);
        HashMap<String, Integer> t = new HashMap<>();
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (word.length() >= minLen) {
                Integer value = t.get(word);
                if (value == null) t.put(word, 1);
                else               t.put(word, value + 1);
            }
        }

        Integer maxVal = 0;
        String maxWord = "";
        Integer val;
        for (String word : t.keys()) {
            val = t.get(word);
            if (val > maxVal) {
                maxVal = val;
                maxWord = word;
            }
        }
        StdOut.println(maxWord + " " + maxVal);
        stat(t);

        HashMap<Integer, Integer> t2 = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            t2.put(i, i);
        }
        stat(t2);
        for (int i = 0; i < 10000; i++) {
            t2.delete(i);
        }
        stat(t2);
    }

    private static void stat(HashMap<?, ?> t) {
        StdOut.println("size: " + t.n);
        StdOut.println("buckets: " + t.m);
        int minSize = Integer.MAX_VALUE;
        int maxSize = 0;
        int empty = 0;
        for (int i = 0; i < t.m; i++) {
            int size = 0;
            for (HashMap<?, ?>.Node x = t.a[i]; x != null; x = x.next) {
                size++;
            }
            if (size < minSize) minSize = size;
            if (size > maxSize) maxSize = size;
            if (size == 0) empty++;
        }
        StdOut.println("min bucket size: " + minSize);
        StdOut.println("max bucket size: " + maxSize);
        StdOut.println("avg bucket size: " + t.n / t.m);
        StdOut.println("empty buckets: " + empty);

        int[] freq = new int[maxSize+1];
        for (int i = 0; i < t.m; i++) {
            int size = 0;
            for (HashMap<?, ?>.Node x = t.a[i]; x != null; x = x.next) {
                size++;
            }
            freq[size] += 1;
        }
        Histogram.print(freq, maxSize);
    }
}
