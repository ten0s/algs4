import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=BruteForceSubstringSearch ARGS=BruteForceSubstringSearch.java <<EOF
return
charAt
EOF
#+END_SRC

#+RESULTS:
: Found: 135
: Found: 142

*/

public class BruteForceSubstringSearch {
    public static int search(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        for (int i = 0; i <= n-m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            }
            if (j == m) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Usage: java BruteForceSubstringSearch FILE <<EOF");
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
