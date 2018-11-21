import edu.princeton.cs.algs4.*;
import java.util.Arrays;

// $ make run CLASS=ClosestPair ARGS=doubles.txt
// (1.70, 1.90)

public class ClosestPair {

    // Do not instantiate.
    private ClosestPair() { }

    // O(n*lg(n))
    public static double[] pair(double[] a) {
        // O(n*lg(n))
        Arrays.sort(a);


        double min = Math.abs(a[1] - a[0]);
        double r[] = new double[] {a[0], a[1]};

        // O(n)
        for (int i = 1; i < a.length - 1; i++) {
            double tmp = Math.abs(a[i+1] - a[i]);
            if (tmp < min) {
                r[0] = a[i];
                r[1] = a[i+1];
                min = tmp;
            }
        }

        return r;
    }

    public static void main(String[] args)  {
        In in = new In(args[0]);
        double[] a = in.readAllDoubles();

        double[] r = pair(a);
        StdOut.printf("(%.2f, %.2f)\n", r[0], r[1]);
    }
}
