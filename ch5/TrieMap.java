import java.util.Deque;
import java.util.ArrayDeque;

import edu.princeton.cs.algs4.*;

public class TrieMap<Value> {
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
        //return keysWithPrefix("");
        Deque<String> queue = new ArrayDeque<>();
        collect(root, "", queue);
        return queue;
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
        sb.append("  " + 0 + " " + attrs(label(""), shape("circle")) + ";\n");
        toDot(root, 0, 1, sb);
        sb.append("}\n");
        return sb.toString();
    }

    private int toDot(Node x, int pid, int id, StringBuilder sb) {
        if (x == null) return id;
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                sb.append("  " + id + " " + attrs(
                              label(c), xlabel(x.next[c].val), shape("circle")) + ";\n");
                sb.append("  " + pid + " -- " + id + ";\n");
                id = toDot(x.next[c], id, id+1, sb);
            }
        }
        return id;
    }

    private String attrs(String... attrs) {
        Deque<String> attrs2 = new ArrayDeque<>();
        for (String attr : attrs) if (attr != null) attrs2.add(attr);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String attr : attrs2) {
            sb.append(attr);
            attrs2.remove();
            if (attrs2.size() > 0) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private String label(Object o) {
        return "label=\"" + o + "\"";
    }

    private String xlabel(Object o) {
        if (o != null) {
            return "xlabel=\"" + o + "\"";
        } else {
            return null;
        }
    }

    private String shape(String s) {
        return "shape=\"" + s + "\"";
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java TrieMap <file> <<EOF");
            StdOut.println("dot | size |");
            StdOut.println("get <key> | put <key> <val> | remove <key> |");
            StdOut.println("lpo <prefix> | keys | kwp <prefix> | ktm <pattern>");
            StdOut.println("EOF");
            return;

        }
        In in = new In(args[0]);
        TrieMap<String> trie = new TrieMap<>();
        int id = 0;
        while (!in.isEmpty()) {
            String key = in.readString();
            trie.put(key, "" + id++);
        }
        while (!StdIn.isEmpty()) {
            String cmd = StdIn.readString();
            if (cmd.equals("dot")) {
                StdOut.println(trie.toDot());
            } else if (cmd.equals("size")) {
                StdOut.println(trie.size());
            } else if (cmd.equals("get")) {
                String key = StdIn.readString();
                StdOut.println(trie.get(key));
            } else if (cmd.equals("put")) {
                String key = StdIn.readString();
                String val = StdIn.readString();
                trie.put(key, val);
            } else if (cmd.equals("remove")) {
                String key = StdIn.readString();
                trie.remove(key);
            } else if (cmd.equals("lpo")) {
                String prefix = StdIn.readString();
                if (prefix.equals("\"\"") || prefix.equals("''")) prefix = "";
                StdOut.println(trie.longestPrefixOf(prefix));
            } else if (cmd.equals("keys")) {
                for (String s : trie.keys()) {
                    StdOut.println(s);
                }
            } else if (cmd.equals("kwp")) {
                String prefix = StdIn.readString();
                if (prefix.equals("\"\"") || prefix.equals("''")) prefix = "";
                for (String s : trie.keysWithPrefix(prefix)) {
                    StdOut.println(s);
                }
            } else if (cmd.equals("ktm")) {
                String pattern = StdIn.readString();
                if (pattern.equals("\"\"") || pattern.equals("''")) pattern = "";
                for (String s : trie.keysThatMatch(pattern)) {
                    StdOut.println(s);
                }
            }
        }
    }
}
