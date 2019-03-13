import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=BruteForceSubstringSearch ARGS="abra aaabbbabracadabra"
#+END_SRC

#+RESULTS:
: text:    aaabbbabracadabra
: pattern:       abra

#+BEGIN_SRC sh :results output
make run CLASS=BruteForceSubstringSearch ARGS="abrc aaabbbabracadabra"
#+END_SRC

#+RESULTS:
: text:    aaabbbabracadabra
: pattern:                  abrc

*/

public class BruteForceSubstringSearch2 {
    public static int search(String pat, String txt) {
        int j, M = pat.length();
        int i, N = txt.length();
        for (i = 0, j = 0; i < N && j < M; i++) {
            if (txt.charAt(i) == pat.charAt(j)) j++;
            else { i -= j; j = 0; }
        }
        if (j == M) return i-M;
        else        return N;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Usage: java BruteForceSubstringSearch2 PATTERN TEXT");
            return;
        }
        String pat = args[0];
        String txt = args[1];
        StdOut.println("text:    " + txt);
        int offset = search(pat, txt);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
