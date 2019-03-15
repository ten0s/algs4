import java.util.*;
import edu.princeton.cs.algs4.*;

public class Huffman {
    private static final int R = 256; // extended ASCII
    private static final char DELIMITER = ':';

    private static class Node implements Comparable<Node> {
        private char ch;
        private int freq;
        private final Node left, right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public static Node fromText(String text) {
        int[] freq = new int[R];
        int n = text.length();
        for (int i = 0; i < n; i++) {
            freq[text.charAt(i)]++;
        }
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (char c = 0; c < R; c++) {
            if (freq[c] > 0) {
                pq.add(new Node(c, freq[c], null, null));
            }
        }
        while (pq.size() > 1) {
            Node l = pq.remove();
            Node r = pq.remove();
            pq.add(new Node('\0', l.freq + r.freq, l, r));
        }
        return pq.remove();
    }

    public static Node fromDump(String dump) {
        Deque<Character> q = new ArrayDeque<>();
        int n = dump.length();
        for (int i = 0; i < n; i++) {
            q.add(dump.charAt(i));
        }
        return undump(q);
    }

    private static Node undump(Deque<Character> q) {
        char c = q.remove();
        if (c == '1') {
            return new Node(q.remove(), 0, null, null);
        }
        Node l = undump(q);
        Node r = undump(q);
        return new Node('\0', 0, l, r);
    }

    public static String toDump(Node root) {
        StringBuilder sb = new StringBuilder();
        dump(root, sb);
        return sb.toString();
    }

    private static void dump(Node x, StringBuilder sb) {
        if (x.isLeaf()) {
            sb.append('1');
            sb.append(x.ch);
        } else {
            sb.append('0');
            dump(x.left, sb);
            dump(x.right, sb);
        }
    }

    public static String[] codes(Node root) {
        String[] st = new String[R];
        codes(root, "", st);
        return st;
    }

    private static void codes(Node x, String prefix, String[] st) {
        if (x.isLeaf()) {
            st[x.ch] = prefix;
        } else {
            codes(x.left, prefix + '0', st);
            codes(x.right, prefix + '1', st);
        }
    }

    public static String toDot(Node root) {
        StringBuilder sb = new StringBuilder();
        sb.append("graph {\n");
        sb.append("  node " + attrs(shape("circle")) + "\n");
        toDot(root, 0, sb);
        sb.append("}\n");
        return sb.toString();
    }

    private static int toDot(Node x, int id, StringBuilder sb) {
        if (x == null) return id;
        int idLeft  = toDot(x.left , id+1  , sb);
        int idRight = toDot(x.right, idLeft , sb);
        if (x.isLeaf()) {
            sb.append("  " + id + " " + attrs(label(x.ch)) + "\n");
        } else {
            sb.append("  " + id + " " + attrs(label(' ')) + "\n");
            // link to left subtree
            sb.append("  " + id + " -- " + (id+1) + " " + attrs(label('0')) + "\n");
            // link to right subtree
            sb.append("  " + id + " -- " + idLeft + " " + attrs(label('1')) + "\n");
        }
        return idRight+1;
    }

    private static String attrs(String... attrs) {
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

    private static String label(Object o) {
        return "label=\"" + o + "\"";
    }

    private static String shape(String s) {
        return "shape=\"" + s + "\"";
    }

    public static void main(String[] args) {
        if (args.length != 0) {
            StdOut.println("Usage: java Huffman << EOF");
            StdOut.println("- TEXT # print dump");
            StdOut.println("-c TEXT # print codes");
            StdOut.println("-d TEXT # print dot");
            StdOut.println("+ DUMP # print text");
            StdOut.println("+d DUMP # print dot");
            StdOut.println("EOF");
            return;
        }
        while (!StdIn.isEmpty()) {
            String cmd = StdIn.readString();
            if (cmd.equals("-") || cmd.equals("-c") || cmd.equals("-d")) {
                String text = StdIn.readString();
                boolean codez = cmd.equals("-c");
                boolean dot = cmd.equals("-d");

                Node trie = fromText(text);

                StringBuilder sb = new StringBuilder();
                String[] codes = codes(trie);

                int n = text.length();
                for (int i = 0; i < n; i++) {
                    sb.append(codes[text.charAt(i)]);
                }

                String trieDump = toDump(trie);
                String textDump = sb.toString();

                sb = new StringBuilder();
                sb.append(trieDump);
                sb.append(DELIMITER);
                sb.append(textDump);
                String dump = sb.toString();

                if (dot)

                    StdOut.println(toDot(trie));
                else if (codez)
                    StdOut.print(codesToString(codes));
                else
                    StdOut.println(dump);
            } else if (cmd.equals("+") || cmd.equals("+d")) {
                String dump = StdIn.readString();
                boolean dot = cmd.equals("+d");

                int d = dump.indexOf(DELIMITER);
                String trieDump = dump.substring(0, d);
                String textDump = dump.substring(d+1, dump.length());

                Node trie = fromDump(trieDump);

                StringBuilder sb = new StringBuilder();
                int n = textDump.length();
                int i = 0;
                while (i < n) {
                    Node x = trie;
                    while (!x.isLeaf()) {
                        if (textDump.charAt(i++) == '0')
                            x = x.left;
                        else
                            x = x.right;
                    }
                    sb.append(x.ch);
                }
                String text = sb.toString();
                if (dot)
                    StdOut.println(toDot(trie));
                else
                    StdOut.println(text);
            }
        }
    }

    private static String codesToString(String[] codes) {
        StringBuilder sb = new StringBuilder();
        for (char c = 0; c < R; c++) {
            if (codes[c] != null) {
                sb.append(c + ": " + codes[c] + "\n");
            }
        }
        return sb.toString();
    }
}
