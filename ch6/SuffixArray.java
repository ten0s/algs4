import java.util.*;
import edu.princeton.cs.algs4.*;

public class SuffixArray {
    private class Suffix implements Comparable<Suffix> {
        private final String text;
        private final int index;

        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }

        private int length() {
            return text.length() - index;
        }

        private char charAt(int i ) {
            return text.charAt(index + i);
        }

        public String toString() {
            return text.substring(index);
        }

        public int compareTo(Suffix that) {
            if (this == that) return 0;
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return this.length() - that.length();
        }
    }

    private Suffix[] suffixes;

    public SuffixArray(String text) {
        int n = text.length();
        suffixes = new Suffix[n];
        for (int i = 0; i < n; i++)
            suffixes[i] = new Suffix(text, i);
        Arrays.sort(suffixes);
    }

    public int index(int i ) {
        return suffixes[i].index;
    }

    public int length() {
        return suffixes.length;
    }

    public String select(int i) {
        return suffixes[i].toString();
    }

    public int lcp(int i) {
        int n = Math.min(suffixes[i-1].length(), suffixes[i].length());
        for (int j = 0; j < n; j++)
            if (suffixes[i-1].charAt(j) != suffixes[i].charAt(j))
                return j;
        return n;
    }

    public int rank(String key) {
        int lo = 0, hi = suffixes.length-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(key, suffixes[mid]);
            if      (cmp < 0) hi = mid-1;
            else if (cmp > 0) lo = mid+1;
            else              return mid;
        }
        return lo;
    }

    private int compare(String key, Suffix suffix) {
        int n = Math.min(key.length(), suffix.length());
        for (int i = 0; i < n; i++) {
            if (key.charAt(i) < suffix.charAt(i)) return -1;
            if (key.charAt(i) > suffix.charAt(i)) return +1;
        }
        return key.length() - suffix.length();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: javac SuffixArray FILE < EOF");
            StdOut.println("index i");
            StdOut.println("length");
            StdOut.println("select i");
            StdOut.println("lcp i");
            StdOut.println("rank key");
            StdOut.println("EOF");
        }
        In in = new In(args[0]);
        String text = in.readAll().replaceAll("\\s+", " ");
        SuffixArray sa = new SuffixArray(text);
        while (!StdIn.isEmpty()) {
            String cmd = StdIn.readString();
            if (cmd.equals("index")) {
                int i = StdIn.readInt();
                StdOut.println(sa.index(i));
            } else if (cmd.equals("length")) {
                StdOut.println(sa.length());
            } else if (cmd.equals("select")) {
                int i = StdIn.readInt();
                StdOut.println(sa.select(i));
            } else if (cmd.equals("lcp")) {
                int i = StdIn.readInt();
                StdOut.println(sa.lcp(i));
            } else if (cmd.equals("rank")) {
                String key = StdIn.readString();
                StdOut.println(sa.rank(key));
            }
        }
    }
}
