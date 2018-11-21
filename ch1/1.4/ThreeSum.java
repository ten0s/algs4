import edu.princeton.cs.algs4.*;

// $ make run CLASS=ThreeSum ARGS=../../data/1Kints.txt
// elapsed time = 0.55
// 70

// $ make run CLASS=ThreeSum ARGS=../../data/4Kints.txt
// elapsed time = 33.617
// 4039

public class ThreeSum {

    // Do not instantiate.
    private ThreeSum() { }

    // O(n^3)
    public static int count(int[] a) {
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args)  {
        In in = new In(args[0]);
        int[] a = in.readAllInts();

        Stopwatch timer = new Stopwatch();
        int count = count(a);
        StdOut.println("elapsed time = " + timer.elapsedTime());
        StdOut.println(count);
    }
}
