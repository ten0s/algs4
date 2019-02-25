import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh
make run CLASS=StringMSD <<EOF
she
sells
seashells
by
the
sea
shore
the
shells
she
sells
are
surely
seashells
EOF
#+END_SRC

#+RESULTS:
| are       |
| by        |
| sea       |
| seashells |
| seashells |
| sells     |
| sells     |
| she       |
| she       |
| shells    |
| shore     |
| surely    |
| the       |
| the       |
*/

public class StringMSD {
    public static String[] sort(String[] a) {
        int N = a.length;
        String[] aux = new String[N];
        return sort(a, aux, 0, N-1, 0);
    }

    public static String[] sort(String[] a, String[] aux, int lo, int hi, int d) {
        if (lo >= hi) return a;

        int R = 256; // extended ASCII
        int[] count = new int[R+2];

        for (int i = lo; i <= hi; i++)
            count[charAt(a[i], d)+2]++;

        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];

        for (int i = lo; i <= hi; i++)
            aux[count[charAt(a[i], d)+1]++] = a[i];

        for (int i = lo; i <= hi; i++)
            a[i] = aux[i-lo];

        for (int r = 0; r < R; r++)
            sort(a, aux, lo+count[r], lo+count[r+1]-1, d+1);

        return a;
    }

    private static int charAt(String s, int d) {
        return d < s.length() ? s.charAt(d) : -1;
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;

        // sort the strings
        sort(a);

        // print results
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);
    }
}
