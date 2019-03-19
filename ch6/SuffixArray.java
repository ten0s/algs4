import java.util.*;

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
        return -1;
    }

    public String toString() {
        return ArrayUtil.toString(suffixes);
    }
}
