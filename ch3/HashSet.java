import java.lang.reflect.Array;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

/*
$ make run CLASS=HashSet ARGS="maxword 10 ../data/tale.txt"
youthfulness

$ make run CLASS=HashSet ARGS="maxword 10 ../data/tale.txt stat"
youthfulness
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

$ make run CLASS=HashSet ARGS="dot ../data/tinyTale.txt"
digraph {
  rankdir=LR;
  node [shape=record];
  array [label="<0> 0|<1> 1|<2> 2|<3> 3|<4> 4|<5> 5|<6> 6|<7> 7|<8> 8|<9> 9|<10> 10|<11> 11|<12> 12|<13> 13|<14> 14|<15> 15|<16> 16|<17> 17|<18> 18|<19> 19|<20> 20|<21> 21|<22> 22|<23> 23|<24> 24|<25> 25|<26> 26|<27> 27|<28> 28|<29> 29|<30> 30"];
  array:7 -> node7_0:head;
  node7_0 [label="{<head> belief | <next>}"];
  array:8 -> node8_0:head;
  node8_0 [label="{<head> hope | <next>}"];
  node8_0:next -> node8_1:head;
  node8_1 [label="{<head> spring | <next>}"];
  node8_1:next -> node8_2:head;
  node8_2 [label="{<head> age | <next>}"];
  node8_2:next -> node8_3:head;
  node8_3 [label="{<head> the | <next>}"];
  array:9 -> node9_0:head;
  node9_0 [label="{<head> of | <next>}"];
  array:11 -> node11_0:head;
  node11_0 [label="{<head> epoch | <next>}"];
  array:13 -> node13_0:head;
  node13_0 [label="{<head> darkness | <next>}"];
  array:14 -> node14_0:head;
  node14_0 [label="{<head> wisdom | <next>}"];
  array:15 -> node15_0:head;
  node15_0 [label="{<head> season | <next>}"];
  array:19 -> node19_0:head;
  node19_0 [label="{<head> winter | <next>}"];
  node19_0:next -> node19_1:head;
  node19_1 [label="{<head> incredulity | <next>}"];
  array:22 -> node22_0:head;
  node22_0 [label="{<head> times | <next>}"];
  node22_0:next -> node22_1:head;
  node22_1 [label="{<head> was | <next>}"];
  array:23 -> node23_0:head;
  node23_0 [label="{<head> light | <next>}"];
  node23_0:next -> node23_1:head;
  node23_1 [label="{<head> worst | <next>}"];
  node23_1:next -> node23_2:head;
  node23_2 [label="{<head> best | <next>}"];
  node23_2:next -> node23_3:head;
  node23_3 [label="{<head> it | <next>}"];
  array:29 -> node29_0:head;
  node29_0 [label="{<head> foolishness | <next>}"];
  array:30 -> node30_0:head;
  node30_0 [label="{<head> despair | <next>}"];
}

$ make run CLASS=HashSet ARGS="stat ../data/tale.txt"
size: 10674
buckets: 2237
min bucket size: 0
max bucket size: 14
avg bucket size: 4
empty buckets: 24
    x
    x
    xx
   xxxx
   xxxx
   xxxx
   xxxx
   xxxx
  xxxxxx
  xxxxxx
  xxxxxxx
 xxxxxxxx
 xxxxxxxxx
 xxxxxxxxxx
xxxxxxxxxxxxxxx

$ make run CLASS=HashSet ARGS="test 1000 stat"
Adding 1000 integers...
size: 1000
buckets: 137
min bucket size: 7
max bucket size: 8
avg bucket size: 7
empty buckets: 0
       x
       x
       x
       x
       x
       xx
       xx
       xx
xxxxxxxxx
Deleting 1000 integers...
size: 0
buckets: 31
min bucket size: 0
max bucket size: 0
avg bucket size: 0
empty buckets: 31
*/

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
                    sb.append("  node" + i + "_" + j + " [label=\"{<head> " + x.key + " | <next>}\"];\n");
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
