import edu.princeton.cs.algs4.*;
import java.util.Arrays;

// $ make run CLASS=ThreeSumFaster ARGS=../../data/8Kints.txt
// elapsed time = 0.248
// 32074

// $ make run CLASS=ThreeSumFaster ARGS=../../data/16Kints.txt
// elapsed time = 0.893
// 255181

// $ make run CLASS=ThreeSumFaster ARGS=../../data/32Kints.txt
// elapsed time = 2.986
// 2052358

public class ThreeSumFaster {

    // Do not instantiate.
    private ThreeSumFaster() { }

    public static int count(int[] a) {
        // O(n*lg(n))
        Arrays.sort(a);

        int count = 0;

        // O(n^2)
        for (int i = 0; i < a.length - 2; i++) {
            int j = i + 1;
            int k = a.length-1;
            while (j < k) {
                int sum = a[i] + a[j] + a[k];
                //StdOut.printf("(%d, %d, %d) %d + %d + %d = %d\n", i, j, k, a[i], a[j], a[k], sum);
                if (sum == 0) {
                    count++;
                    // inc i and don't dec k
                    j++;
                } else if (sum > 0) {
                    // upper bound is too big, dec it
                    k--;
                } else {
                    // sum < 0, lower bound is too small, inc it
                    j++;
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
