import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh
wc -l /usr/share/dict/words
#+END_SRC

#+RESULTS:
: 99171 /usr/share/dict/words

#+BEGIN_SRC sh
make run CLASS=TrieSet ARGS=/usr/share/dict/words <<EOF
size
EOF
#+END_SRC

#+RESULTS:
: 99171

#+BEGIN_SRC sh
make run CLASS=TrieSet ARGS=/usr/share/dict/words <<EOF
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
make run CLASS=TrieSet ARGS=/usr/share/dict/words <<EOF
ktm sh.ll
EOF
#+END_SRC

#+RESULTS:
| shall |
| shell |
| shill |

#+NAME: trie
#+BEGIN_SRC sh :results output drawer
make run CLASS=TrieSet ARGS=../data/shells.txt <<EOF
dot
EOF
#+END_SRC

#+RESULTS: trie
:RESULTS:
graph {
  0 [label=""];
  1 [label="a"];
  0 -- 1;
  2 [label="r"];
  1 -- 2;
  3 [label="e" xlabel="t"];
  2 -- 3;
  4 [label="b"];
  0 -- 4;
  5 [label="y" xlabel="t"];
  4 -- 5;
  6 [label="s"];
  0 -- 6;
  7 [label="e"];
  6 -- 7;
  8 [label="a" xlabel="t"];
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
  14 [label="s" xlabel="t"];
  13 -- 14;
  15 [label="l"];
  7 -- 15;
  16 [label="l"];
  15 -- 16;
  17 [label="s" xlabel="t"];
  16 -- 17;
  18 [label="h"];
  6 -- 18;
  19 [label="e" xlabel="t"];
  18 -- 19;
  20 [label="l"];
  19 -- 20;
  21 [label="l"];
  20 -- 21;
  22 [label="s" xlabel="t"];
  21 -- 22;
  23 [label="o"];
  18 -- 23;
  24 [label="r"];
  23 -- 24;
  25 [label="e" xlabel="t"];
  24 -- 25;
  26 [label="u"];
  6 -- 26;
  27 [label="r"];
  26 -- 27;
  28 [label="e"];
  27 -- 28;
  29 [label="l"];
  28 -- 29;
  30 [label="y" xlabel="t"];
  29 -- 30;
  31 [label="t"];
  0 -- 31;
  32 [label="h"];
  31 -- 32;
  33 [label="e" xlabel="t"];
  32 -- 33;
}

:END:

#+BEGIN_SRC dot :file trieset.png :var src=trie
$src
#+END_SRC

#+RESULTS:
[[file:trieset.png]]


*/

public class TrieSet {
    private final static int R = 256; // extended ASCII
    private Node root = new Node();
    private int size;

    private static class Node {
        boolean end;
        Node[] next = new Node[R];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean contains(String key) {
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.end;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.end) size++;
            x.end = true;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = add(x.next[c], key, d+1);
        return x;
    }

    public void remove(String key) {
        root = remove(root, key, 0);
    }

    private Node remove(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.end) size--;
            x.end = false;
        } else {
            char c = key.charAt(d);
            x.next[c] = remove(x.next[c], key, d+1);
        }
        if (x.end) return x;

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
        if (x.end) queue.add(prefix);
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
        if (d == pattern.length() && x.end) queue.add(prefix);
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
        if (x.end) length = d;
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
                sb.append("  " + id + " " + attrs(label(c), xlabel(x.next[c].end)) + ";\n");
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

    private String xlabel(boolean v) {
        if (v) {
            return "xlabel=\"t\"";
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java TrieSet <file> <<EOF");
            StdOut.println("dot | size | lpo <prefix> | kwp <prefix> | ktm <pattern>");
            StdOut.println("EOF");
            return;

        }
        In in = new In(args[0]);
        TrieSet trie = new TrieSet();
        while (!in.isEmpty()) {
            String key = in.readString();
            trie.add(key);
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
