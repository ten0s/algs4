import java.lang.reflect.Array;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

public class HashTable<Key , Value> implements ST<Key, Value> {
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

    public HashTable() {
        this(31);
    }

    @SuppressWarnings("unchecked")
    private HashTable(int m) {
        this.m = m;
        this.a = (Node[]) Array.newInstance(Node.class, m);
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private void resize(int capacity) {
        HashTable<Key, Value> t = new HashTable<>(capacity);
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
        if (n > 0 && n <= 2*m) resize(Prime.nextPrime(m/2));
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
        HashTable<String, Integer> t = new HashTable<>();
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
        StdOut.println("size: " + t.n);
        StdOut.println("buckets: " + t.m);
        int minSize = Integer.MAX_VALUE, maxSize = 0;
        int empty = 0;
        for (int i = 0; i < t.m; i++) {
            int size = 0;
            for (HashTable<String, Integer>.Node x = t.a[i]; x != null; x = x.next) {
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
    }
}
