import edu.princeton.cs.algs4.*;
import java.util.Arrays;

// $ make run CLASS=FarthestPair ARGS=doubles.txt
// (0.30, 7.20)

public class FarthestPair {

    // Do not instantiate.
    private FarthestPair() { }

    // O(n)
    public static double[] pair(double[] a) {
        double max = a[0];
        double min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
            if (a[i] < min) {
                min = a[i];
            }
        }
        return new double[] {min, max};
    }

    public static void main(String[] args)  {
        In in = new In(args[0]);
        double[] a = in.readAllDoubles();

        double[] r = pair(a);
        StdOut.printf("(%.2f, %.2f)\n", r[0], r[1]);
    }
}
