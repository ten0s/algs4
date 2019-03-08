import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output drawer
make run CLASS=BruteForceSubstringSearch ARGS="abra aaabbbabracadabra"
#+END_SRC

#+RESULTS:
:RESULTS:
text:    aaabbbabracadabra
pattern:       abra
:END:

#+BEGIN_SRC sh :results output drawer
make run CLASS=BruteForceSubstringSearch ARGS="abrc aaabbbabracadabra"
#+END_SRC

#+RESULTS:
:RESULTS:
text:    aaabbbabracadabra
pattern:                  abrc
:END:

*/

public class BruteForceSubstringSearch {
    public static int search(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        for (int i = 0; i <= N-M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            }
            if (j == M) return i;
        }
        return N;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Usage: java BruteForceSubstringSearch PATTERN TEXT");
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
