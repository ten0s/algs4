import java.util.Deque;
import java.util.ArrayDeque;

import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh
wc -l /usr/share/dict/words
#+END_SRC

#+RESULTS:
: 99171 /usr/share/dict/words

#+BEGIN_SRC sh
make run CLASS=TrieMap ARGS=/usr/share/dict/words <<EOF
size
EOF
#+END_SRC

#+RESULTS:
: 99171

#+BEGIN_SRC sh
make run CLASS=TrieMap ARGS=/usr/share/dict/words <<EOF
kwp shell
EOF
#+END_SRC

#+RESULTS:
| shell       |
| shell's     |
| shellac     |
| shellac's   |
| shellacked  |
| shellacking |
| shellacs    |
| shelled     |
| sheller     |
| shellfish   |
| shellfish's |
| shellfishes |
| shelling    |
| shells      |

#+BEGIN_SRC sh
make run CLASS=TrieMap ARGS=/usr/share/dict/words <<EOF
ktm sh.ll
EOF
#+END_SRC

#+RESULTS:
| shall |
| shell |
| shill |

#+NAME: trie
#+BEGIN_SRC sh :results output drawer
make run CLASS=TrieMap ARGS=../data/shells.txt <<EOF
dot
EOF
#+END_SRC

#+RESULTS: trie
:RESULTS:
graph {
  0 [label=" "];
  1 [label="a"];
  0 -- 1;
  2 [label="r"];
  1 -- 2;
  3 [label="e" xlabel="8"];
  2 -- 3;
  4 [label="b"];
  0 -- 4;
  5 [label="y" xlabel="3"];
  4 -- 5;
  6 [label="s"];
  0 -- 6;
  7 [label="e"];
  6 -- 7;
  8 [label="a" xlabel="5"];
  7 -- 8;
  9 [label="s"];
  8 -- 9;
  10 [label="h"];
  9 -- 10;
  11 [label="e"];
  10 -- 11;
  12 [label="l"];
  11 -- 12;
  13 [label="l"];
  12 -- 13;
  14 [label="s" xlabel="10"];
  13 -- 14;
  15 [label="l"];
  7 -- 15;
  16 [label="l"];
  15 -- 16;
  17 [label="s" xlabel="8"];
  16 -- 17;
  18 [label="h"];
  6 -- 18;
  19 [label="e" xlabel="8"];
  18 -- 19;
  20 [label="l"];
  19 -- 20;
  21 [label="l"];
  20 -- 21;
  22 [label="s" xlabel="7"];
  21 -- 22;
  23 [label="o"];
  18 -- 23;
  24 [label="r"];
  23 -- 24;
  25 [label="e" xlabel="6"];
  24 -- 25;
  26 [label="u"];
  6 -- 26;
  27 [label="r"];
  26 -- 27;
  28 [label="e"];
  27 -- 28;
  29 [label="l"];
  28 -- 29;
  30 [label="y" xlabel="9"];
  29 -- 30;
  31 [label="t"];
  0 -- 31;
  32 [label="h"];
  31 -- 32;
  33 [label="e" xlabel="7"];
  32 -- 33;
}

:END:

#+BEGIN_SRC dot :file triemap.png :var src=trie
$src
#+END_SRC

#+RESULTS:
[[file:triemap.png]]


*/

public class TrieMap<Value> implements MAP<String, Value> {
    private final static int R = 256; // extended ASCII
    private Node root = new Node();
    private int size;

    private static class Node {
        Object val;
        Node[] next = new Node[R];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    @SuppressWarnings("unchecked")
    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.val == null) size++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }

    public void remove(String key) {
        root = remove(root, key, 0);
    }

    private Node remove(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) size--;
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = remove(x.next[c], key, d+1);
        }
        if (x.val != null) return x;

        for (char c = 0; c < R; c++)
            if (x.next[c] != null) return x;
        return null;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Deque<String> queue = new ArrayDeque<>();
        collect(get(root, prefix, 0), prefix, queue);
        return queue;
    }

    private void collect(Node x, String prefix, Deque<String> queue) {
        if (x == null) return;
        if (x.val != null) queue.add(prefix);
        for (char c = 0; c < R; c++)
            collect(x.next[c], prefix + c, queue);
    }

    public Iterable<String> keysThatMatch(String pattern) {
        Deque<String> queue = new ArrayDeque<>();
        collect(root, "", pattern, queue);
        return queue;
    }

    private void collect(Node x, String prefix, String pattern, Deque<String> queue) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null) queue.add(prefix);
        if (d == pattern.length()) return;

        char next = pattern.charAt(d);
        for (char c = 0; c < R; c++)
            if (next == '.' || next == c)
                collect(x.next[c], prefix + c, pattern, queue);
    }

    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == s.length()) return length;
        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("graph {\n");
        sb.append("  0 [" + label("") + "];\n");
        toDot(root, 0, 1, sb);
        sb.append("}\n");
        return sb.toString();
    }

    private int toDot(Node x, int pid, int id, StringBuilder sb) {
        if (x == null) return id;
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                sb.append("  " + id + " " + attrs(label(c), xlabel(x.next[c].val)) + ";\n");
                sb.append("  " + pid + " -- " + id + ";\n");
                id = toDot(x.next[c], id, id+1, sb);
            }
        }
        return id;
    }

    private String attrs(String label, String xlabel) {
        return "[" + label + (xlabel.equals("") ? "" : " " + xlabel) + "]";
    }

    private String label(Object o) {
        return "label=\"" + o + "\"";
    }

    private String xlabel(Object o) {
        if (o != null) {
            return "xlabel=\"" + o + "\"";
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java TrieMap <file> <<EOF");
            StdOut.println("dot | size | lpo <prefix> | kwp <prefix> | ktm <pattern>");
            StdOut.println("EOF");
            return;

        }
        In in = new In(args[0]);
        TrieMap<Integer> trie = new TrieMap<>();
        while (!in.isEmpty()) {
            String key = in.readString();
            trie.put(key, trie.size());
        }
        while (!StdIn.isEmpty()) {
            String cmd = StdIn.readString();
            if (cmd.equals("dot")) {
                StdOut.println(trie.toDot());
            } else if (cmd.equals("size")) {
                StdOut.println(trie.size());
            } else if (cmd.equals("lpo")) {
                String prefix = StdIn.readString();
                StdOut.println(trie.longestPrefixOf(prefix));
            } else if (cmd.equals("kwp")) {
                String prefix = StdIn.readString();
                for (String s : trie.keysWithPrefix(prefix)) {
                    StdOut.println(s);
                }
            } else if (cmd.equals("ktm")) {
                String pattern = StdIn.readString();
                for (String s : trie.keysThatMatch(pattern)) {
                    StdOut.println(s);
                }
            }
        }
    }
}
