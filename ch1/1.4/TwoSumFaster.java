import edu.princeton.cs.algs4.*;
import java.util.Arrays;

// $ make run CLASS=TwoSumFaster ARGS=../../data/32Kints.txt
// elapsed time = 0.015
// 273

// $ make run CLASS=TwoSumFaster ARGS=../../data/1Mints.txt
// elapsed time = 0.245
// 249838

public class TwoSumFaster {

    // Do not instantiate.
    private TwoSumFaster() { }

    public static int count(int[] a) {
        // O(n*lg(n))
        Arrays.sort(a);

        // O(N)
        int count = 0;
        int i = 0, j = a.length-1;
        while (i < j) {
            int sum = a[i] + a[j];
            //StdOut.printf("(%d, %d) %d + %d = %d\n", i, j, a[i], a[j], sum);
            if (sum == 0) {
                count++;
                // inc i and don't dec j
                // there can the same value again like -3
                // Example: -3 -3 -2 -1 0 1 2 3
                i++;
            } else if (sum > 0) {
                // upper bound is too big, dec it
                // Example: -3 -3 -2 -1 0 1 2 3 4
                j--;
            } else {
                // sum < 0, lower bound is too small, inc it
                // Example: -3 -2 -1 0 1 2
                i++;
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
