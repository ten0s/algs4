import edu.princeton.cs.algs4.*;
import java.lang.Math.*;

// $ make test CLASS=Ex1115

public class Ex1115 {
    // histogram takes an array a[] of int values and an int m as arguments
    // and returns an array of length m whose ith entry is the number of
    // times the integer i appeared in the argument array. If the values
    // in a[] are all between 0 and m-1, the sum of the values in the returned
    // array should be equal to a.length
    public static int[] histogram(int[] a, int m) {
        int[] hist = new int[m];

        for (int i = 0; i < a.length; i++) {
            if (a[i] >= 0 && a[i] <= m-1) {
                hist[a[i]]++;
            }
        }

        return hist;
    }

    public static void main(String[] args) {
        StdOut.println("Hello");
    }
}
