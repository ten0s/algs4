import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=BruteForceSubstringSearch2 ARGS=BruteForceSubstringSearch2.java <<EOF
return
charAt
EOF
#+END_SRC

#+RESULTS:
: Found: 137
: Found: 144

*/

public class BruteForceSubstringSearch2 {
    public static int search(String pat, String txt) {
        int j, m = pat.length();
        int i, n = txt.length();
        for (i = 0, j = 0; i < n && j < m; i++) {
            if (txt.charAt(i) == pat.charAt(j)) j++;
            else { i -= j; j = 0; }
        }
        if (j == m) return i-m;
        else        return -1;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Usage: java BruteForceSubstringSearch2 FILE <<EOF");
            StdOut.println("PATTERN");
            StdOut.println("EOF");
            return;
        }
        In in = new In(args[0]);
        String txt = in.readAll();
        while (!StdIn.isEmpty()) {
            String pat = StdIn.readString();
            int i = search(pat, txt);
            if (i == -1) {
                StdOut.println("Not found");
            } else {
                StdOut.println("Found: " + i);
            }
        }
    }
}
