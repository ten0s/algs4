import java.lang.reflect.Array;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

// time make run CLASS=HashSet ARGS=10 < ../data/tale.txt
// youthfulness
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

public class HashSet<Key> implements SET<Key> {
    private final static int MIN_CAPACITY = 31;
    private class Node {
        Key key;
        Node next;

        public Node(Key key, Node next) {
            this.key = key;
            this.next = next;
        }
    }

    private Node[] a;
    private int m;
    private int n;

    public HashSet() {
        this(MIN_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    private HashSet(int m) {
        this.m = m;
        this.a = (Node[]) Array.newInstance(Node.class, m);
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private void resize(int capacity) {
        HashSet<Key> t = new HashSet<>(capacity);
        for (int i = 0; i < m; i++) {
            for (Node x = a[i]; x != null; x = x.next) {
                t.add(x.key);
            }
        }
        this.a = t.a;
        this.m = t.m;
    }

    public boolean contains(Key key) {
        int i = hash(key);
        for (Node x = a[i]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return true;
            }
        }
        return false;
    }

    public void add(Key key) {
        int i = hash(key);
        for (Node x = a[i]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return;
            }
        }
        a[i] = new Node(key, a[i]);
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

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    public Iterator<Key> iterator() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < m; i++) {
            for (Node x = a[i]; x != null; x = x.next) {
                queue.enqueue(x.key);
            }
        }
        return queue.iterator();
    }

    public static void main(String[] args) {
        int minLen = Integer.parseInt(args[0]);
        HashSet<String> s = new HashSet<>();
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (word.length() >= minLen) {
                s.add(word);
            }
        }

        String maxWord = "";
        for (String word : s) {
            if (word.compareTo(maxWord) > 0) {
                maxWord = word;
            }
        }
        StdOut.println(maxWord);
        stat(s);

        HashSet<Integer> s2 = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            s2.add(i);
        }
        stat(s2);
        for (int i = 0; i < 10000; i++) {
            s2.delete(i);
        }
        stat(s2);
    }

    private static void stat(HashSet<?> s) {
        StdOut.println("size: " + s.n);
        StdOut.println("buckets: " + s.m);
        int minSize = Integer.MAX_VALUE;
        int maxSize = 0;
        int empty = 0;
        for (int i = 0; i < s.m; i++) {
            int size = 0;
            for (HashSet<?>.Node x = s.a[i]; x != null; x = x.next) {
                size++;
            }
            if (size < minSize) minSize = size;
            if (size > maxSize) maxSize = size;
            if (size == 0) empty++;
        }
        StdOut.println("min bucket size: " + minSize);
        StdOut.println("max bucket size: " + maxSize);
        StdOut.println("avg bucket size: " + s.n / s.m);
        StdOut.println("empty buckets: " + empty);

        int[] freq = new int[maxSize+1];
        for (int i = 0; i < s.m; i++) {
            int size = 0;
            for (HashSet<?>.Node x = s.a[i]; x != null; x = x.next) {
                size++;
            }
            freq[size] += 1;
        }
        Histogram.print(freq, maxSize);
    }
}
