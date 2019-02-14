import java.lang.reflect.Array;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

/*
$ make run CLASS=HashMap "ARGS=maxword 10 ../data/tale.txt"
monseigneur 101

$ make run CLASS=HashMap "ARGS=maxword 10 ../data/tale.txt stat"
monseigneur 101
size: 2257
buckets: 557
min bucket size: 0
max bucket size: 12
avg bucket size: 4
empty buckets: 10
    x
   xx
   xx
   xxx
  xxxx
  xxxxx
  xxxxx
  xxxxx
 xxxxxx
 xxxxxxx
 xxxxxxx
xxxxxxxxx
xxxxxxxxxxxxx

$ make run CLASS=HashMap ARGS="dot map.txt"
digraph {
  rankdir=LR;
  node [shape=record];
  array [label="<0> 0|<1> 1|<2> 2|<3> 3|<4> 4|<5> 5|<6> 6|<7> 7|<8> 8|<9> 9|<10> 10|<11> 11|<12> 12|<13> 13|<14> 14|<15> 15|<16> 16|<17> 17|<18> 18|<19> 19|<20> 20|<21> 21|<22> 22|<23> 23|<24> 24|<25> 25|<26> 26|<27> 27|<28> 28|<29> 29|<30> 30"];
  array:1 -> node1_0:head;
  node1_0 [label="{<head> 1 | one | <next>}"];
  array:2 -> node2_0:head;
  node2_0 [label="{<head> 2 | two | <next>}"];
  array:3 -> node3_0:head;
  node3_0 [label="{<head> 3 | three | <next>}"];
  array:4 -> node4_0:head;
  node4_0 [label="{<head> 4 | four | <next>}"];
  array:5 -> node5_0:head;
  node5_0 [label="{<head> 5 | five | <next>}"];
  array:6 -> node6_0:head;
  node6_0 [label="{<head> 6 | six | <next>}"];
  array:7 -> node7_0:head;
  node7_0 [label="{<head> 7 | seven | <next>}"];
}
*/

public class HashMap<Key , Value> implements MAP<Key, Value> {
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

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        sb.append("  rankdir=LR;\n");
        sb.append("  node [shape=record];\n");

        sb.append("  array [label=\"");
        for (int i = 0; i < m; i++) {
            sb.append("<" + i + "> " + i);
            if (i != m-1) sb.append("|");
        }
        sb.append("\"];\n");

        for (int i = 0; i < m; i++) {
            Node x = a[i];
            if (x != null) {
                int j = 0;
                sb.append("  array:" + i + " -> node" + i + "_" + j + ":" + "head;\n");
                while (x != null) {
                    sb.append("  node" + i + "_" + j + " [label=\"{<head> " + x.key + " | " + x.val + " | <next>}\"];\n");
                    if (x.next != null) {
                        sb.append("  node" + i + "_" + j + ":next -> node" + i + "_" + (j+1) + ":head;\n");
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
            StdOut.println("usage: javac HashMap maxword <minlen> <file> [dot | stat]");
            StdOut.println("usage: javac HashMap dot <file>");
            StdOut.println("usage: javac HashMap stat <file>");
            StdOut.println("usage: javac HashMap test <max> [dot | stat]");
            return;
        }

        String command = args[0];
        if (command.equals("maxword")) {
            int minLen = Integer.parseInt(args[1]);
            In in = new In(args[2]);
            HashMap<String, Integer> t = new HashMap<>();
            while (!in.isEmpty()) {
                String word = in.readString();
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
            if (args.length > 3) {
                String info = args[3];
                if (info.equals("dot")) {
                    StdOut.println(t.toDot());
                } else if (info.equals("stat")) {
                    stat(t);
                }
            }
        } else if (command.equals("dot")) {
            In in = new In(args[1]);
            HashMap<Integer, String> t = new HashMap<>();
            while (!in.isEmpty()) {
                int key = in.readInt();
                String val = in.readString();
                t.put(key, val);
            }
            StdOut.println(t.toDot());
        } else if (command.equals("stat")) {
            In in = new In(args[1]);
            HashMap<Integer, String> t = new HashMap<>();
            while (!in.isEmpty()) {
                int key = in.readInt();
                String val = in.readString();
                t.put(key, val);
            }
            stat(t);
        } else if (command.equals("test")) {
            HashMap<Integer, Integer> t = new HashMap<>();
            int max = Integer.parseInt(args[1]);
            StdOut.println("Adding " + max + " integers...");
            for (int i = 0; i < max; i++) {
                t.put(i, i);
            }
            if (args.length > 2) {
                String info = args[2];
                if (info.equals("dot")) {
                    StdOut.println(t.toDot());
                } else if (info.equals("stat")) {
                    stat(t);
                }
            }
            StdOut.println("Deleting " + max + " integers...");
            for (int i = 0; i < max; i++) {
                t.delete(i);
            }
            if (args.length > 2) {
                String info = args[2];
                if (info.equals("dot")) {
                    StdOut.println(t.toDot());
                } else if (info.equals("stat")) {
                    stat(t);
                }
            }
        } else {
            StdOut.println("Unknown command: " + command);
        }
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
