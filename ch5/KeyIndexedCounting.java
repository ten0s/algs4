import edu.princeton.cs.algs4.*;

/*
#+BEGIN_SRC sh :results output
make run CLASS=KeyIndexedCounting <<EOF
6
3 1 4 0 5 2 5 4 3 2 1 0 0 1 2 3 4 5
EOF
#+END_SRC

#+RESULTS:
: 0 0 0 1 1 1 2 2 2 3 3 3 4 4 4 5 5 5
*/

public class KeyIndexedCounting {
    public static int[] sort(int[] a, int R) {
        int N = a.length;
        int[] aux = new int[N];

        int[] count = new int[R+1];

        for (int i = 0; i < N; i++)
            count[a[i]+1]++;

        for (int r = 0; r < R; r++)
            count[r+1] += count[r];

        for (int i = 0; i < N; i++)
            aux[count[a[i]]++] = a[i];

        for (int i = 0; i < N; i++)
            a[i] = aux[i];

        return a;
    }

    public static void main(String[] args) {
        int R = StdIn.readInt();
        int[] a = StdIn.readAllInts();
        int N = a.length;

        sort(a, R);

        for (int i = 0; i < N; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }
}
