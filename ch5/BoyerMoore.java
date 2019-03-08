import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output drawer
make run CLASS=BoyerMoore ARGS="abra aaabbbabracadabra"
#+END_SRC

#+RESULTS:
:RESULTS:
text:    aaabbbabracadabra
pattern:       abra
:END:

#+BEGIN_SRC sh :results output drawer
make run CLASS=BoyerMoore ARGS="abrc aaabbbabracadabra"
#+END_SRC

#+RESULTS:
:RESULTS:
text:    aaabbbabracadabra
pattern:                  abrc
:END:

*/

public class BoyerMoore {
    private String pat;
    private int[] right;

    public BoyerMoore(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        right = new int[R];
        for (char c = 0; c < R; c++)
            right[c] = -1;
        for (char j = 0; j < M; j++)
            right[pat.charAt(j)] = j;
    }

    public int search(String txt) {
        int M = pat.length();
        int N = txt.length();
        int skip;
        for (int i = 0; i <= N-M; i += skip) {
            skip = 0;
            for (int j = M-1; j >=0; j--) {
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j-right[txt.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;
        }
        return N;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Usage: java BoyerMoore PATTERN TEXT");
            return;
        }
        String pat = args[0];
        String txt = args[1];
        BoyerMoore bm = new BoyerMoore(pat);
        StdOut.println("text:    " + txt);
        int offset = bm.search(txt);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
