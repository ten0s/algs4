import edu.princeton.cs.algs4.*;
import java.util.*;

/*
#+BEGIN_SRC sh :results output drawer
make run CLASS=KMP ARGS="abra aaabbbabracadabra"
#+END_SRC

#+RESULTS:
:RESULTS:
text:   aaabbbabracadabra
pattern:      abra
digraph {
  0 [label="0"]
  1 [label="1"]
  2 [label="2"]
  3 [label="3"]
  4 [label="4"]
  0 -> 0 [label="b,r"]
  0 -> 1 [label="a"]
  1 -> 0 [label="r"]
  1 -> 1 [label="a"]
  1 -> 2 [label="b"]
  2 -> 0 [label="b"]
  2 -> 1 [label="a"]
  2 -> 3 [label="r"]
  3 -> 0 [label="b,r"]
  3 -> 4 [label="a"]
}

:END:

#+BEGIN_SRC sh :results output drawer
make run CLASS=KMP ARGS="abrc aaabbbabracadabra"
#+END_SRC

#+RESULTS:
:RESULTS:
text:   aaabbbabracadabra
pattern:                  abrc
digraph {
  0 [label="0"]
  1 [label="1"]
  2 [label="2"]
  3 [label="3"]
  4 [label="4"]
  0 -> 0 [label="b,c,r"]
  0 -> 1 [label="a"]
  1 -> 0 [label="c,r"]
  1 -> 1 [label="a"]
  1 -> 2 [label="b"]
  2 -> 0 [label="b,c"]
  2 -> 1 [label="a"]
  2 -> 3 [label="r"]
  3 -> 0 [label="b,r"]
  3 -> 1 [label="a"]
  3 -> 4 [label="c"]
}

:END:

*/

public class KMP {
    private final int R = 256;
    private final int M;
    private String pat;
    private int[][] dfa;

    public KMP(String pat) {
        this.pat = pat;
        M = pat.length();
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (char c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];
            dfa[pat.charAt(j)][j] = j+1;
            X = dfa[pat.charAt(j)][X];
        }
    }

    public int search(String txt) {
        final int N = txt.length();
        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
            if (j == M) return i-M;
        }
        return N;
    }

    public String dfaToDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        // states 0..M
        for (int j = 0; j <= M; j++) {
            sb.append("  " + j + " " + attrs(label(j)) + "\n");
        }
        // links
        for (int j = 0; j < M; j++) {
            HashMap<Integer, List<Character>> map = new HashMap<>();
            for (char c = 0; c < R; c++) {
                // only if pattern has this char
                if (pat.indexOf(c) != -1) {
                    int k = dfa[c][j];
                    if (!map.containsKey(k)) {
                        map.put(k, new ArrayList<>());
                    }
                    map.get(k).add(c);
                }
            }
            for (int k : map.keySet()) {
                Object[] cs = map.get(k).toArray();
                sb.append("  " + j + " -> " + k + " " + attrs(label(cs)) + "\n");
            }
        }
        /*
        // {rank=same 0..M}
        sb.append("  {rank=same ");
        for (int j = 0; j <= M; j++) {
            sb.append(j);
            if (j != M) sb.append(" ");
        }
        sb.append("}\n");
        */
        sb.append("}\n");
        return sb.toString();
    }

    private String attrs(String... attrs) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < attrs.length; i++) {
            sb.append(attrs[i]);
            if (i != attrs.length-1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private String label(Object... objs) {
        StringBuilder sb = new StringBuilder();
        sb.append("label=\"");
        for (int i = 0; i < objs.length; i++) {
            sb.append(objs[i]);
            if (i != objs.length-1) sb.append(",");
        }
        sb.append("\"");
        return sb.toString();
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        KMP kmp = new KMP(pat);
        StdOut.println("text:   " + txt);
        int offset = kmp.search(txt);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
        StdOut.println(kmp.dfaToDot());
    }
}
