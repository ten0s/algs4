import edu.princeton.cs.algs4.*;
import java.util.Arrays;

// $ make run CLASS=ThreeSumFast ARGS=../../data/4Kints.txt
// elapsed time = 0.607
// 4039

// $ make run CLASS=ThreeSumFast ARGS=../../data/8Kints.txt
// elapsed time = 2.394
// 32074

// $ make run CLASS=ThreeSumFast ARGS=../../data/16Kints.txt
// elapsed time = 9.452
// 255181

// $ make run CLASS=ThreeSumFast ARGS=../../data/32Kints.txt
// elapsed time = 39.739
// 2052358

public class ThreeSumFast {

    // Do not instantiate.
    private ThreeSumFast() { }

    // O(n^2*lg(n))
    public static int count(int[] a) {
        int n = a.length;

        // O(n*lg(n))
        Arrays.sort(a);

        // O(n^2*lg(n))
        int count = 0;
        // O(n)
        for (int i = 0; i < n; i++) {
            // O(n)
            for (int j = i+1; j < n; j++) {
                // O(lg(n))
                if (BinarySearch.indexOf(-a[i]-a[j], a) > j) {
                    count++;
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
