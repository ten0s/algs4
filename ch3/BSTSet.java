import java.util.NoSuchElementException;
import java.util.Collections;
import java.util.Iterator;

import edu.princeton.cs.algs4.*;

public class BSTSet<Key extends Comparable<Key>> implements SET<Key> {
    private class Node {
        Key key;
        Node left;
        Node right;
        int size;

        public Node(Key key) {
            this.key = key;
            this.size = 1;
        }
    }

    private Node root;

    public boolean contains(Key key) {
        return contains(key, root);
    }

    private boolean contains(Key key, Node x) {
        if (x == null) return false;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return contains(key, x.left);
        else if (cmp > 0) return contains(key, x.right);
        else              return true;
    }

    public void add(Key key) {
        root = add(key, root);
    }

    private Node add(Key key, Node x) {
        if (x == null) return new Node(key);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = add(key, x.left);
        else if (cmp > 0) x.right = add(key, x.right);
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
        else if (t < k) return select(k, x.right);
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

    public Iterator<Key> iterator() {
        if (isEmpty()) {
            return Collections.emptyIterator();
        } else {
            Queue<Key> queue = new Queue<>();
            keys(queue, min(), max(), root);
            return queue.iterator();
        }
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
        // https://eli.thegreenplace.net/2009/11/23/visualizing-binary-trees-with-graphviz
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        if (root == null) {
            sb.append("\n");
        } else if (root.left == null && root.right == null) {
            sb.append("    " + root.key + "\n");
        } else {
            toDotAux(root, new Counter(), sb);
        }
        sb.append("}\n");
        return sb.toString();
    }

    private class Counter {
        public int value;
    }

    private void toDotNull(Key key, Counter nullcount, StringBuilder sb) {
        sb.append("    null" + nullcount.value + " [shape=point];\n");
        sb.append("    " + key + " -> null" + nullcount.value + ";\n");
    }

    private void toDotAux(Node node, Counter nullcount, StringBuilder sb) {
        if (node.left != null) {
            sb.append("    " + node.key + " -> " + node.left.key + ";\n");
            toDotAux(node.left, nullcount, sb);
        } else {
            nullcount.value++;
            toDotNull(node.key, nullcount, sb);
        }

        if (node.right != null) {
            sb.append("    " + node.key + " -> " + node.right.key + ";\n");
            toDotAux(node.right, nullcount, sb);
        } else {
            nullcount.value++;
            toDotNull(node.key, nullcount, sb);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: javac BSTSet <file>");
            return;
        }
        In in = new In(args[0]);
        in.readInt();
        BSTSet<Integer> tree = new BSTSet<>();
        while (!in.isEmpty()) {
            int key = in.readInt();
            tree.add(key);
        }
        StdOut.println(tree.toDot());
    }
}
