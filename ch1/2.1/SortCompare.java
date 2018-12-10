import edu.princeton.cs.algs4.*;

// $ make run CLASS=SortCompare ARGS="Selection Insertion 1000 100"

public class SortCompare {
    public static <T extends Comparable<T>> double time(String alg, T[] a) {
        Stopwatch timer = new Stopwatch();
        if (alg.equals("Selection")) Selection.sort(a);
        if (alg.equals("Insertion")) Insertion.sort(a);
        return timer.elapsedTime();
    }

    public static double timeRandomInput(String alg, int n, int trials) {
        double total = 0.0;
        Double[] a = new Double[n];
        for (int t = 0; t < trials; t++) {
            for (int i = 0; i < n; i++) {
                a[i] = StdRandom.uniform(0.0, 1.0);
            }
            total += time(alg, a);
        }
        return total;
    }

    public static void main(String[] args) {
        String alg1 = args[0];
        String alg2 = args[1];
        int n = Integer.parseInt(args[2]);
        int trials = Integer.parseInt(args[3]);
        double time1 = timeRandomInput(alg1, n, trials);
        double time2 = timeRandomInput(alg2, n, trials);
        double ratio = time1 / time2;
        if (ratio < 1) {
            String tmp = alg1;
            alg1 = alg2;
            alg2 = tmp;
            ratio = 1 / ratio;
        }
        StdOut.printf("For %d random Doubles\n  %s is", n, alg1);
        StdOut.printf(" %.1f times faster than %s\n", ratio, alg2);
    }
}
