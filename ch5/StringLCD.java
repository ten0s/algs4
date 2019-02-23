import edu.princeton.cs.algs4.*;

/*
M-x org-babel-execute-src-block-maybe

#+BEGIN_SRC sh :results output drawer
make run CLASS=StringLCD <<EOF
4PGC938
2IYE230
3CI0720
1ICK750
1OHV845
4JZY524
1ICK750
3CI0720
1OHV845
1OHV845
2RLA629
2RLA629
3ATW723
EOF
#+END_SRC

#+RESULTS:
:RESULTS:
1ICK750
1ICK750
1OHV845
1OHV845
1OHV845
2IYE230
2RLA629
2RLA629
3ATW723
3CI0720
3CI0720
4JZY524
4PGC938
:END:
*/

public class StringLCD {
    public static String[] sort(String[] a, int W) {
        int N = a.length;
        int R = 256; // extend ASCII alphabet size
        String[] aux = new String[N];

        for (int d = W-1; d >= 0; d--) {
            int[] count = new int[R+1];

            for (int i = 0; i < N; i++)
                count[a[i].charAt(d)+1]++;

            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }

        return a;
    }

    /**
     * Reads in a sequence of fixed-length strings from standard input;
     * LSD radix sorts them;
     * Prints them to standard output in ascending order.
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;

        // check that strings have fixed length
        int w = a[0].length();
        for (int i = 0; i < n; i++)
            assert a[i].length() == w : "Strings must have fixed length";

        // sort the strings
        sort(a, w);

        // print results
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);
    }
}
