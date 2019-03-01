import java.util.NoSuchElementException;
import java.util.Collections;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

/*
$ cat map.txt
4 four
2 two
6 six
1 one
3 three
5 five
7 seven

$ make run CLASS=BSTMap ARGS=map.txt
digraph {
  3 [shape=point];
  4 [shape=point];
  2 [label="1/one"];
  2 -> 3;
  2 -> 4;
  7 [shape=point];
  8 [shape=point];
  6 [label="3/three"];
  6 -> 7;
  6 -> 8;
  1 [label="2/two"];
  1 -> 2;
  1 -> 6;
  13 [shape=point];
  14 [shape=point];
  12 [label="5/five"];
  12 -> 13;
  12 -> 14;
  17 [shape=point];
  18 [shape=point];
  16 [label="7/seven"];
  16 -> 17;
  16 -> 18;
  11 [label="6/six"];
  11 -> 12;
  11 -> 16;
  0 [label="4/four"];
  0 -> 1;
  0 -> 11;
}
*/

public class BSTMap<Key extends Comparable<Key>, Value> implements MAP<Key, Value> {
    private class Node {
        Key key;
        Value val;
        Node left;
        Node right;
        int size;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.size = 1;
        }
    }

    private Node root;

    public Value get(Key key) {
        return get(key, root);
    }

    private Value get(Key key, Node x) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(key, x.left);
        else if (cmp > 0) return get(key, x.right);
        else              return x.val;
    }

    public void put(Key key, Value val) {
        root = put(key, val, root);
    }

    private Node put(Key key, Value val, Node x) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(key, val, x.left);
        else if (cmp > 0) x.right = put(key, val, x.right);
        else              x.val = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void delete(Key key) {
        root = delete(key, root);
    }

    private Node delete(Key key, Node x) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left = delete(key, x.left);
        else if (cmp > 0) x.right = delete(key, x.right);
        else {
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            Node t = x;
            if (Math.random() < 0.5) {
                x = max(t.left);
                x.left = deleteMax(t.left);
                x.right = t.right;
            } else {
                x = min(t.right);
                x.right = deleteMin(t.right);
                x.left = t.left;
            }
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    public Key min() {
        checkEmpty();
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        checkEmpty();
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public void deleteMin() {
        checkEmpty();
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMax() {
        checkEmpty();
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Key select(int k) {
        if (k < 0 || k >= size())
            throw new IllegalArgumentException();
        return select(k, root).key;
    }

    private Node select(int k, Node x) {
        if (x == null) return null;
        int t = size(x.left);
        if      (t > k) return select(k, x.left);
        else if (t < k) return select(k-t-1, x.right);
        else            return x;
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else              return size(x.left);
    }

    public Key floor(Key key) {
        Node x = floor(key, root);
        if (x == null) throw new NoSuchElementException();
        return x.key;
    }

    private Node floor(Key key, Node x) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(key, x.left);
        Node t = floor(key, x.right);
        if (t != null) return t;
        else           return x;
    }

    public Key ceiling(Key key) {
        Node x = ceiling(key, root);
        if (x == null) throw new NoSuchElementException();
        return x.key;
    }

    private Node ceiling(Key key, Node x) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(key, x.right);
        Node t = ceiling(key, x.left);
        if (t != null) return t;
        else           return x;
    }

    public Iterable<Key> keys() {
        if (isEmpty())
            return new Iterable<Key>() {
                public Iterator<Key> iterator() {
                    return Collections.emptyIterator();
                }
            };
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<>();
        keys(queue, lo, hi, root);
        return queue;
    }

    private void keys(Queue<Key> queue, Key lo, Key hi, Node x) {
        if (x == null) return;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if (cmpLo < 0) keys(queue, lo, hi, x.left);
        if (cmpLo <= 0 && cmpHi >= 0) queue.enqueue(x.key);
        if (cmpHi > 0) keys(queue, lo, hi, x.right);
    }

    private void checkEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    public String toDot() {
        // credits
        // https://gist.github.com/kstwrt/8591183
        // https://eli.thegreenplace.net/2009/11/23/visualizing-binary-trees-with-graphviz
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        toDot(root, 0, sb);
        sb.append("}\n");
        return sb.toString();
    }

    private int toDot(Node node, int id, StringBuilder sb) {
        if (node == null) {
            sb.append("  " + id + " [shape=point];\n");
            return id+1;
        }
        int id2 = toDot(node.left, id+1, sb);
        int id3 = toDot(node.right, id2, sb);
        sb.append("  " + id + " [label=\"" + node.key + "/" + node.val + "\"];\n");
        // link to left subtree
        sb.append("  " + id + " -> " + (id+1) + ";\n");
        // link to right subtree
        sb.append("  " + id + " -> " + id2  + ";\n");
        return id3+1;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: javac BST <file>");
            return;
        }

        In in = new In(args[0]);
        BSTMap<Integer, String> tree = new BSTMap<>();
        while (!in.isEmpty()) {
            int key = in.readInt();
            String val = in.readString();
            tree.put(key, val);
        }
        StdOut.println(tree.toDot());
    }
}
