import java.util.Deque;
import java.util.ArrayDeque;

import edu.princeton.cs.algs4.*;

public class TSTMap<Value> {
    private Node root;
    private int size;

    private class Node {
        char c;
        Value val;
        Node left, mid, right;
        public Node(char c) {
            this.c = c;
        }
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

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length()-1)
                          return get(x.mid, key, d+1);
        else return x;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) x = new Node(c);
        if      (c < x.c) x.left  = put(x.left, key, val, d);
        else if (c > x.c) x.right = put(x.right, key, val, d);
        else if (d < key.length()-1)
                          x.mid   = put(x.mid, key, val, d+1);
        else {
            if (x.val == null) size++;
            x.val = val;
        }
        return x;
    }

    public void remove(String key) {
        // TBI
    }

    public Iterable<String> keys() {
        Deque<String> queue = new ArrayDeque<>();
        collect(root, "", root.val != null, queue);
        return queue;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Deque<String> queue = new ArrayDeque<>();
        Node x = get(root, prefix, 0);
        collect(x, prefix.substring(0, prefix.length()-1), x.val != null, queue);
        return queue;
    }

    private void collect(Node x, String prefix, boolean add, Deque<String> queue) {
        if (add) queue.add(prefix);
        if (x == null) return;
        collect(x.left , prefix, false, queue);
        collect(x.mid  , prefix + x.c, x.val != null, queue);
        collect(x.right, prefix, false, queue);
    }

    public Iterable<String> keysThatMatch(String pattern) {
        Deque<String> queue = new ArrayDeque<>();
        //collect(root, "", pattern, queue);
        return queue;
    }

    private void collect(Node x, String prefix, String pattern, Deque<String> queue) {
        if (x == null) return;
        return;
    }

    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) return length;
        /*
        if (x.val != null) length = d;
        if (d == s.length()) return length;
        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);
        */
        return length;
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("graph {\n");
        sb.append("  node " + attrs(shape("circle")) + "\n");
        toDot(root, 0, sb);
        sb.append("}\n");
        return sb.toString();
    }

    private int toDot(Node x, int id, StringBuilder sb) {
        if (x == null) {
            sb.append("  " + id + " " + attrs(shape("point")) + "\n");
            return id+1;
        }
        int idLeft  = toDot(x.left , id+1  , sb);
        int idMid   = toDot(x.mid  , idLeft, sb);
        int idRight = toDot(x.right, idMid , sb);
        sb.append("  " + id + " " + attrs(label(x.c), xlabel(x.val)) + "\n");
        // link to left subtree
        sb.append("  " + id + " -- " + (id+1) + "\n");
        // link to mid subtree
        sb.append("  " + id + " -- " + idLeft + "\n");
        // link to right subtree
        sb.append("  " + id + " -- " + idMid + "\n");
        return idRight+1;
    }

    private String attrs(String attr) {
        return attrs(attr, "");
    }

    private String attrs(String attr1, String attr2) {
        return "[" + attr1 + (attr2.equals("") ? "" : " " + attr2) + "]";
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

    private String shape(String s) {
        return "shape=\"" + s + "\"";
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: java TSTMap <file> <<EOF");
            StdOut.println("dot | size |");
            StdOut.println("get <key> | put <key> <val> | remove <key> |");
            StdOut.println("lpo <prefix> | keys | kwp <prefix> | ktm <pattern>");
            StdOut.println("EOF");
            return;

        }
        In in = new In(args[0]);
        TSTMap<String> trie = new TSTMap<>();
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
