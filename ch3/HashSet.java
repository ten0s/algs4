import java.lang.reflect.Array;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

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

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        sb.append("  rankdir=LR\n");
        sb.append("  node [shape=record]\n");

        sb.append("  array [label=\"");
        for (int i = 0; i < m; i++) {
            sb.append("<" + i + "> " + i);
            if (i != m-1) sb.append("|");
        }
        sb.append("\"]\n");

        for (int i = 0; i < m; i++) {
            Node x = a[i];
            if (x != null) {
                int j = 0;
                sb.append("  array:" + i + " -> node" + i + "_" + j + ":" + "head\n");
                while (x != null) {
                    sb.append("  node" + i + "_" + j + " [label=\"{<head> " + x.key + " | <next>}\"]\n");
                    if (x.next != null) {
                        sb.append("  node" + i + "_" + j + ":next -> node" + i + "_" + (j+1) + ":head\n");
                    }
                    x = x.next;
                    j++;
                }
            }
        }

        sb.append("}\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: javac HashSet maxword <minlen> <file> [dot | stat]");
            StdOut.println("usage: javac HashSet dot <file>");
            StdOut.println("usage: javac HashSet stat <file>");
            StdOut.println("usage: javac HashSet test <max> [dot | stat]");
            return;
        }

        String command = args[0];
        if (command.equals("maxword")) {
            int minLen = Integer.parseInt(args[1]);
            In in = new In(args[2]);
            HashSet<String> s = new HashSet<>();
            while (!in.isEmpty()) {
                String word = in.readString();
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
            if (args.length > 3) {
                String info = args[3];
                if (info.equals("dot")) {
                    StdOut.println(s.toDot());
                } else if (info.equals("stat")) {
                    stat(s);
                }
            }
        } else if (command.equals("dot")) {
            In in = new In(args[1]);
            HashSet<String> s = new HashSet<>();
            while (!in.isEmpty()) {
                String word = in.readString();
                s.add(word);
            }
            StdOut.println(s.toDot());
        } else if (command.equals("stat")) {
            In in = new In(args[1]);
            HashSet<String> s = new HashSet<>();
            while (!in.isEmpty()) {
                String word = in.readString();
                s.add(word);
            }
            stat(s);
        } else if (command.equals("test")) {
            HashSet<Integer> s = new HashSet<>();
            int max = Integer.parseInt(args[1]);
            StdOut.println("Adding " + max + " integers...");
            for (int i = 0; i < max; i++) {
                s.add(i);
            }
            if (args.length > 2) {
                String info = args[2];
                if (info.equals("dot")) {
                    StdOut.println(s.toDot());
                } else if (info.equals("stat")) {
                    stat(s);
                }
            }
            StdOut.println("Deleting " + max + " integers...");
            for (int i = 0; i < max; i++) {
                s.delete(i);
            }
            if (args.length > 2) {
                String info = args[2];
                if (info.equals("dot")) {
                    StdOut.println(s.toDot());
                } else if (info.equals("stat")) {
                    stat(s);
                }
            }
        } else {
            StdOut.println("Unknown command: " + command);
        }
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
