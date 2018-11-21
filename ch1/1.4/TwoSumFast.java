import edu.princeton.cs.algs4.*;
import java.util.Arrays;

// $ make run CLASS=TwoSumFast ARGS=../../data/32Kints.txt
// elapsed time = 0.023
// 273

// $ make run CLASS=TwoSumFast ARGS=../../data/1Mints.txt
// elapsed time = 0.384
// 249838

public class TwoSumFast {

    // Do not instantiate.
    private TwoSumFast() { }

    public static int count(int[] a) {
        int n = a.length;

        // O(n*lg(n))
        Arrays.sort(a);

        int count = 0;
        // O(n)
        for (int i = 0; i < n; i++) {
            // O(lg(n))
            // Possible to improve a bit by
            // int hi = n-1;
            // if (BinarySearch.indexOf(-a[i], a, i+1, hi) != -1) {
            if (BinarySearch.indexOf(-a[i], a) > i) {
                    count++;
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
